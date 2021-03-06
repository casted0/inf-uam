package clasificadores;
import datos.*;
import particionado.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

abstract public class Clasificador {
	abstract public void entrenamiento (Datos datosTrain);
	abstract public ArrayList<Double> clasifica (Datos datosTest);
	public Boolean fLaplace;
	abstract public ArrayList<Double> getResultado();
	static int[][] confusion;
	
	public static double error (Datos datos, Clasificador clas) {
		/*Recorremos todos los datos*/
		int error = 0;
		for(int i=0; i<datos.getNumDatos(); i++){
			//System.out.println("	Real: "+datos.getDato(i, datos.getSizeTipoAtributos()-1)+ "		Prediccion: "+ clas.getResultado().get(i)*1.0);
			/*Comprobamos si el dato es correcto*/
			if(datos.getDato(i, datos.getSizeTipoAtributos()-1) != clas.getResultado().get(i)*1.0){
				error++;
			}
			/*Añadimos el dato a su lugar en la matrix de confusion*/
			if (datos.getClasesValue(clas.getResultado().get(i))!=null)
				confusion[datos.getClasesValue(clas.getResultado().get(i))][datos.getClasesValue(datos.getDato(i, datos.getSizeTipoAtributos()-1))]++;
		}
		/*Devolvemos el % de errores sobre el total de comprobaciones*/
		return error/(datos.getNumDatos()*1.0);
	}
	
	public static ArrayList<Double> validacion(EstrategiaParticionado part, Datos datos, Clasificador clas) {
		/*Inicializacion de la matriz de confusion*/
		confusion = new int[datos.getClases().size()][datos.getClases().size()];		
		for(int j=0,k=0; j<datos.getClases().size(); j++){
			for(;k<datos.getClases().size();k++){
				datos.getClases().keys().nextElement();
				confusion[j][k]=0;
			}	
		}
		
		/*Para particion recibida se realiza el entrenamiento, test y se contabilizan los errores*/
		ArrayList<Double> res = new ArrayList<Double>();
		for (Particion p:part.crearPartciones(datos)){
			clas.entrenamiento(datos.getParticion( p.getTrain()));
			clas.clasifica(datos.getParticion( p.getTest()));
			Double e=Clasificador.error(datos.getParticion(p.getTest()), clas);
			res.add(e);
		}
		return res;
	}
	
	public static void matrizConf(){
		System.out.println("Matriz de confusion:");
		for(int i=0;i<confusion.length;i++){
			for(int j=0;j<confusion.length;j++){
				System.out.print("["+confusion[i][j]+"]\t");
			}
			System.out.println();
		}
	}
	
	public static void error(){
		double errores = 0.0;
		double numDat = 0.0;
		for(int i=0;i<confusion.length;i++){
			for(int j=0;j<confusion.length;j++){
				numDat+=confusion[i][j];
				if(i!=j)
					errores+=confusion[i][j];
			}	
		}
		System.out.println("Error matriz = "+errores/numDat*100);
		return;
	}
	
	public static void validar(EstrategiaParticionado part, Datos datos, Clasificador clas, boolean matriz){
		ArrayList<Double> errores = Clasificador.validacion(part, datos, clas);
		Double suma=0.0;
		for(Double e: errores){
			//System.out.println("Error de una prueba = "+e*100);
			suma = suma + e;
		}
		System.out.println("Error medio = "+suma/errores.size()*100+" %");
		if(matriz) matrizConf();
		error();
	}
	
	public static void main(String []args) {
		
		boolean matriz = true;

		double [][]des= new double[2][2];
		des[0][0]=1.0;
		Arrays.sort(des, new Comparator<double[]>() {
			@Override
			public int compare(double[] o1, double[] o2) {
				return Double.compare(o2[0], o1[0]);
			}
		});
		System.out.println("- Leyendo ficheros...");
		Datos d1 = Datos.cargaDeFichero("src/ficheros/wdbc.data");
		Datos d2 = Datos.cargaDeFichero("src/ficheros/winequality.data");
		Datos d3 = Datos.cargaDeFichero("src/ficheros/heart-disease.data");
		System.out.println("- Creando estrategias de particionado y clasificadores...");
		EstrategiaParticionado part1 = new ValidacionCruzada(10);
		Clasificador c1a = new KNN(5);
		Clasificador c1b= new KNN(1);
		Clasificador c1c = new KNN(51);
		Clasificador c2 = new RegLog(50,0.01);
		Clasificador c3 = new DKNN(1, 50, 2);
		Clasificador c4 = new KNNDW(5);
		Clasificador c5 = new KNNNB(1);
		
		System.out.println("\nvalidacion KNN sobre wdbc.data con 5 vecinos");
		validar(part1, d1, c1a, matriz);
		
		System.out.println("\nvalidacion KNN sobre winewquality.data con 2 vecinos");
		validar(part1, d2, c1b, matriz);
		
		System.out.println("\nvalidacion KNN sobre herat-disease.data con 51 vecinos");
		validar(part1, d3, c1c, matriz);
		
		System.out.println("\nvalidacion RegLog sobre wdbc.data tasa=0.01 epocas=50");
		validar(part1, d1, c2, matriz);

		System.out.println("\nvalidacion DKNN sobre wdbc.data de 1 a 50 vecinos");
		validar(part1, d1, c3, matriz);

		System.out.println("\nvalidacion KNNDW sobre wine-quality.data con 5 vecinos");
		validar(part1, d2, c4, matriz);
		
		System.out.println("\nvalidacion KNNNB sobre wine-quality.data con 1 vecino");
		validar(part1, d2, c5, matriz);
		//System.out.println("Mejor k = "+ DKNN.getBestK());
	}
}