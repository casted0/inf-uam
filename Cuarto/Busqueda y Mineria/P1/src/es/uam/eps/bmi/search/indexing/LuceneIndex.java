package es.uam.eps.bmi.search.indexing;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.text.Document;
import java.io.*;
import java.net.URL;
import java.util.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.PorterStemFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Version;
import org.jsoup.Jsoup;
import org.tartarus.snowball.ext.PorterStemmer;
import es.uam.eps.bmi.search.TextDocument;
import es.uam.eps.bmi.search.parsing.Parser;
import es.uam.eps.bmi.search.parsing.TextParser;


/**
 * Clase que implementa la interfaz index, creando un indice de un buscador.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class LuceneIndex implements Index{
	private TextParser textParser;
	private IndexWriter iwriter;
	private String outputPath;
	private IndexReader ireader;
	
	/** Creamos un índice con un cojunto de datos
	 * 
	 * @param inputCollectionPath ruta donde se encuentran los documentos para crear el índice
	 * @param outputIndexPath ruta donde queremos guardar el índice
	 * @param textParser parseador de texto que usamos para analizar los documentos
	 */
	@Override
	public void build(String inputCollectionPath, String outputIndexPath, TextParser textParser) {

		  // Inicio creación del índice
      Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
      Directory directory;
      this.textParser=textParser;
      this.outputPath= outputIndexPath;
		try {
			directory = new SimpleFSDirectory(new File(outputIndexPath));
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_36, analyzer);
	        conf.setOpenMode(OpenMode.CREATE);
	        this.iwriter = new IndexWriter(directory, conf);
	        
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		ZipInputStream zips=null;
		try {
			zips= new ZipInputStream(new FileInputStream(inputCollectionPath));
			ZipEntry entrada = null;
			while((entrada = zips.getNextEntry())!=null ){
				org.apache.lucene.document.Document doc = new org.apache.lucene.document.Document();
				doc.add(new Field("docID", entrada.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
	            doc.add(new Field("content", textParser.parse(readZips(zips)), Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
	            iwriter.addDocument(doc);
	            zips.closeEntry();
			}
			this.iwriter.close();
			zips.close();
			load(outputIndexPath);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
      
	}

	/** Leemos los zips sin necesidad de extraerlos
	 * 
	 * @param zipIn ZipInputStream que hace referencia a un elemento dentro del zip para leerlo
	 * @return string tokenizada y sin stopwords para meter en el indice
	 */
	private String readZips(ZipInputStream zipIn){
		String linea = null;
		String texto = new String();
		try {
			File f = new File ("src/doc.txt");
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("src/doc.txt"));
			BufferedReader br = new BufferedReader(new FileReader(f));
			byte[] bytesIn = new byte[4096];
			int read = 0;
	        while ((read = zipIn.read(bytesIn)) != -1) 
	            bos.write(bytesIn, 0, read);
	        bos.close();
	        while((linea=br.readLine())!=null){
	        	texto = texto.concat(linea+"\n");
	        }
	        br.close();
	        f.delete();
	        return tokenizeStopStem(texto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** Quitamos los stopwords y hacemos stemming de un documento que hemos leido de un zip
	 * 
	 * @param input documento que hemos leido de un zip
	 * @return string documento tokenizado y sin stopwords
	 */
	private String tokenizeStopStem(String input) throws Exception {
		 
	    BufferedReader br = new BufferedReader(new FileReader("datos.txt"));
	    HashSet<String> stopwords = new HashSet<String>();
		String linea = null;
	    while ((linea = br.readLine())!=null) 
	        stopwords.add(linea);
	     
		
        TokenStream tokenStream = new StandardTokenizer(
                Version.LUCENE_36, new StringReader(input));
        tokenStream = new StopFilter(Version.LUCENE_36, tokenStream,CharArraySet.copy(Version.LUCENE_36, stopwords));
        tokenStream = new PorterStemFilter(tokenStream);
 
        StringBuilder sb = new StringBuilder();
        OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttr = tokenStream.getAttribute(CharTermAttribute.class);
        try{
            while (tokenStream.incrementToken()) {
                sb.append(charTermAttr.toString());
                sb.append(" ");
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        return sb.toString();
}

	
	/** Cargamos un indice creado con anterioridad con una ruta que nos pasan
	 * 
	 * @param indexPath ruta donde se encuentra el documento
	 */
	@Override
	public void load(String indexPath) {
		try {
			this.outputPath=indexPath;
			this.ireader= IndexReader.open(new SimpleFSDirectory(new File(indexPath)));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/** Obtenemos la ruta de un indice creado previamente
	 * 
	 * @return ruta del indice creado con anterioridad
	 */
	@Override
	public String getPath() {
		return this.outputPath;
	}

	/** Obtenemos los identificadores de todos los documentos que hemos cargado en el indice
	 * 
	 * @return List<String> lista con todos los identificadores de los documentos cargados en el indice 
	 */
	@Override
	public List<String> getDocIds() {
		List<String> docIds= null;
		try {
			docIds= new ArrayList<String>();
			for (int i=0; i< ireader.numDocs(); i++){
				docIds.add(this.ireader.document(i).getFieldable("docID").stringValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return docIds;
	}

	/** Nos devuelve un TextDocument asociado a un documento del índice
	 * 
	 * @param docId identificador del documento del cual queremos el TextDocument
	 * @return TextDocument asociado con el documento del índice
	 */
	@Override
	public TextDocument getDocument(String docId) {
		TextDocument doc= new TextDocument();
		for (int i=0; i< ireader.numDocs(); i++){
			try {
				String aux=ireader.document(i).getFieldable("docID").stringValue(); 
				if(aux.equals(docId)){
					doc.setId(aux);
					doc.setDoc(ireader.document(i).getFieldable("content").stringValue());
					return doc;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** Nos devuelve todos los términos de todos los documentos del indice
	 * 
	 * @return List<String> lista con todos los términos que hay en el índice 
	 */
	@Override
	public List<String> getTerms() {
		List<String> terminos= null;
		try {   
			terminos= new ArrayList<String>();
			for (int docID = 0; docID < ireader.numDocs(); docID++) {
	            
				TermFreqVector docVector;
	            docVector = ireader.getTermFreqVector(docID, "content");
	            for(String word: docVector.getTerms())
	            	terminos.add(word);
	            
		    }	
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return terminos;
	}

	/** Obtenemos los posting de una termino que nos pasan por argumento
	 * 
	 * @param term término del cual queremos obtener sus postings
	 * @return List<Posting> lista con todos los postings de un término 
	 */
    public List<Posting> getTermPostings(String term) {
        ArrayList<Posting> postingList = new ArrayList<>();
        try {
            TermPositions termPositions = ireader.termPositions(new Term("content", term));
            while(termPositions.next()){
            	ArrayList<Long> positions = new ArrayList<>();
            	for(int i = 0; i < termPositions.freq(); i++)
            		positions.add((long)termPositions.nextPosition());
            	Posting p = new Posting(termPositions.doc() + "", termPositions.freq(), positions);
                postingList.add(p);
            }
            return postingList;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return postingList;
    }

    /** Creamos un indice nuevo, a partir del path donde están los datos que obtenemos por los argumentos
	 * 
	 * @param args argumentos que le pasamos al main. El estilo de la entrada es:
	 * 1ºEl lugar donde se encuentran los documentos.
	 * 2ºEl lugar donde crear el indice.
	 */
	public static void main(String[] args) throws Exception{
		if(args.length != 2){
			System.out.println("Numero incorrecto de argumentos de entrada");
			System.exit(0);
		}
		Parser p = new Parser();
		Index i = new LuceneIndex();
		
		i.build(args[0],args[1], p);
		System.out.println("Indice creado correctamente");
		//i.load("res/index");
		/*List<String> terms = i.getTerms();
		System.out.println("Lista de los términos");
		int numTerms = 0;
		for(String s: terms){
			System.out.println(s);
			numTerms++;
		}
		System.out.println(numTerms);*/
		/*List<Posting> pos = i.getTermPostings("boston");
		for(Posting posting: pos){
			System.out.println("-------------------");
			System.out.println("DocId "+posting.getDocId());
			System.out.println("DocFreq "+posting.getTermFrequency());
			System.out.println("Lista de posiciones:"+posting.getTermPositions().toString());
			System.out.println("-------------------");
		}*/
		/*List<String> idterms = i.getDocIds();
		for(String s: idterms)
			System.out.println(s);*/
		/*TextDocument doc =  i.getDocument("clueweb09-en0000-99-778.html");
		System.out.println(doc.getId());
		System.out.println(doc.getName());
		System.out.println(doc.getDoc());*/
	}
	
}
