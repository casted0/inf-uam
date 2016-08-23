package es.uam.eps.bmi.recomendacion.lectores;


import java.io.*;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Map.Entry;
import java.util.TreeMap;
/**
 * 
 * Clase que lee y el fichero de tags y calcula las similitudes entre usuarios.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 *         Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class MovieTags {
	
	private HashMap<Integer, HashMap<Integer, Integer>> datos= new HashMap<>();
	private HashMap<Integer, Integer> movieIds= new HashMap<>();
	private double [][] similitudes ; 
	/**
	 * Lee los datos de los tags
	 * @param file el archivo con los ratings
	 */
	public void leerDatos(String file){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader (new File (file)));
			String linea;
			linea=br.readLine();
			String[] data;
			while((linea=br.readLine())!=null){
				 data= linea.split(" |\t|\n|\r");
				 for(int i=0; i<data.length; i++){
					 HashMap<Integer, Integer> pelicula= datos.get(Integer.parseInt(data[0]));
					 if(pelicula==null){
						 pelicula= new HashMap<>();
						 pelicula.put(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
					 }
					 else{
						 pelicula.put(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
					 }
					 datos.put(Integer.parseInt(data[0]), pelicula);
				 }
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		int value=0;
		for(Integer key: datos.keySet()){
			movieIds.put(key, value++);
		}
		
	}
	/**
	 * Genera un fichero donde se guardan las similitudes
	 * @param file el fichero a escribir
	 */
	public void guardarSimilitudes(String file){
		try {
			FileOutputStream fos = new FileOutputStream("sims/"+file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(movieIds);
			oos.writeObject(similitudes);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Lee y carga las similitudes generadas en un ejecucion anterior,
	 * en caso de que este no exista lo volvera a generar.
	 * @param file el archivo a cargar
	 */
	public void cargarSimilitudes(String file){
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			movieIds= (HashMap<Integer, Integer>) ois.readObject();
			similitudes= (double[][]) ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println("El fichero de similitudes no existe por tanto se pasara a calcular"
					+ "\nEsto pude tardar.");
			if(file.contains("Coseno")){
				similitudCoseno();
			}
			else if(file.contains("Jaccard")){
				similitudBinJaccard();
			}
		}
	}
	
	/**
	 * Calcula las smilitudes mediante jaccard y las escribe a fichero
	 */
	public void similitudBinJaccard(){

		
		int numTags=0;
		int tamCol2=0;
		int tamCol1=0;
		similitudes= new double [movieIds.size()][movieIds.size()];
		for(Entry<Integer, HashMap<Integer, Integer>> pelicula1: datos.entrySet()){
			tamCol1= pelicula1.getValue().size();
			for(Entry<Integer, HashMap<Integer, Integer>> pelicula2: datos.entrySet()){
				numTags=0;
				tamCol2= pelicula2.getValue().size();
				for(Integer tag: pelicula1.getValue().keySet()){

					if(pelicula2.getValue().containsKey(tag)){
						numTags++;
					}	
				}
				similitudes[movieIds.get(pelicula1.getKey())][movieIds.get(pelicula2.getKey())]=(double)numTags/(double)(tamCol1+tamCol2 -numTags);
			}
		}

		guardarSimilitudes("similitudMoviesJaccard.dat");
		
	}

	/**
	 * Calcula las smilitudes mediante conseno y las escribe a fichero
	 */
	public void similitudCoseno(){
		similitudes= new double [movieIds.size()][movieIds.size()];
		for(Entry<Integer, HashMap<Integer, Integer>> pelicula1: datos.entrySet()){
			int denom1=0;
			//System.out.println(pelicula1.getKey()+" "+pelicula1.getValue().size());
			for(Integer tagW: pelicula1.getValue().values()){
				denom1+=tagW*tagW;
			}
			for(Entry<Integer, HashMap<Integer, Integer>> pelicula2: datos.entrySet()){
				int numTags=0;
				for(Integer tag: pelicula1.getValue().keySet()){
					if(pelicula2.getValue().containsKey(tag)){
						numTags+=pelicula2.getValue().get(tag)*pelicula1.getValue().get(tag);
					}	
				}
				int denom2=0;
				for(Integer tag: pelicula2.getValue().values()){
					denom2+=tag*tag;
				}


				similitudes[movieIds.get(pelicula1.getKey())][movieIds.get(pelicula2.getKey())]=
						(double)numTags/(Math.sqrt(denom1)+Math.sqrt(denom2));
			}
		}

		guardarSimilitudes("similitudMoviesCoseno.dat");
	}
	
		
	
	/**
	 * @return the datos
	 */
	public HashMap<Integer, HashMap<Integer, Integer>> getDatos() {
		return datos;
	}


	/**
	 * @param datos the datos to set
	 */
	public void setDatos(HashMap<Integer, HashMap<Integer, Integer>> datos) {
		this.datos = datos;
	}


	/**
	 * @return the similitudes
	 */
	public double[][] getSimilitudes() {
		return similitudes;
	}


	/**
	 * @param similitudes the similitudes to set
	 */
	public void setSimilitudes(double[][] similitudes) {
		this.similitudes = similitudes;
	}

	

	/**
	 * @return the movieIds
	 */
	public HashMap<Integer, Integer> getMovieIds() {
		return movieIds;
	}

	/**
	 * @param movieIds the movieIds to set
	 */
	public void setMovieIds(HashMap<Integer, Integer> movieIds) {
		this.movieIds = movieIds;
	}public MovieTags() {
	}
	public void printSimilitudes(int n){
		for(int i=0; i<n; i++){
			for(int j=0; j<n; j++){
				System.out.print("["+ similitudes[i][j]+"] ");
			}
			System.out.println();
		}
			
	}


	public static void main(String [] args){
		MovieTags mt = new MovieTags();
		mt.leerDatos("hetrec2011-movielens-2k-v2/movie_tags.dat");
		mt.similitudBinJaccard();
		mt.guardarSimilitudes("similitudMoviesJaccard.dat");
	}
	
	
	

}
