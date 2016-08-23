package es.uam.eps.bmi.search;


/**
 * Clase que nos dara informacion sobre un documento.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class TextDocument{
	
	private String id;
	private String name;
	private String doc;
	
	public TextDocument() {
		
	}
	/**
	 * @return the doc
	 */
	public String getDoc() {
		return doc;
	}
	/**
	 * @param doc the doc to set
	 */
	public void setDoc(String doc) {
		this.doc = doc;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Compara el id de la clase con un objeto dado.
	 * @param object el objeto
	 * @return si el objeto dado es de tipo TextDocument devuelve 
	 * true o false dependiendo de si tienen el mismo id. Sino
	 * devuelve false.
	 */
	@Override
	public boolean equals(Object object){
		if(object instanceof TextDocument){
			TextDocument doc = (TextDocument)object;
			if(this.id.equals(doc.getId())) return true;
			else return false;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.id.hashCode();
	}

	
}
