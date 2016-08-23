package clasificadores;
import datos.*;
import interfacesAlgoritmoGenetico.*;
import interfazKMeans.*;
import particionado.*;

import java.util.*;

import FinalesKMeans.*;

import clasesGeneticoNeuronal.*;
import clasesGeneticoNeuronal2.*;
import clasesGeneticoNeuronal2.EndAlgGenBasico;
import clasesGeneticoNeuronal2.FitnessRN;
import clasesGeneticoNeuronal2.IniPoblacionRN;
import clasesGeneticoNeuronal2.MutacionRN;
import clasesGeneticoNeuronal2.SeleccionRN;
import clasesGeneticoNeuronal2.SelecetPadresRN;
import metricasKMeans.*;


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
			System.out.println("	Real: "+datos.getDato(i, datos.getSizeTipoAtributos()-1)+ "		Prediccion: "+ clas.getResultado().get(i)*1.0);
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
		for(int j=0;j<confusion.length;j++){
			System.out.print(j+"\t");
		}
		System.out.println("\n----------------------------------------------------------------------------------");
		for(int i=0;i<confusion.length;i++){
			int count=0;
			for(int j=0;j<confusion.length;j++){
				System.out.print("["+confusion[i][j]+"]\t");
				count+=confusion[i][j];
			}
			System.out.println("-->"+count+" | "+i);
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
		Datos d1 = Datos.cargaDeFichero("src/ficheros/crx.data");
		//EstrategiaParticionado part=new ValidacionCruzada(4); 
		EstrategiaParticionado part=new ValidacionSimple(65);
	
		/**
		 * Condiciones de parada
		 * 
		 * -Numero de rondas maximo con cambios
		 * -Numero de cambios minimo de los clusters
		 * -Numero de cambios minimo en los centroides
		 */
		//finKMeans fin = new FinRondas(15);
		//finKMeans fin = new FinClusters(50);
		/*finKMeans fin = new FinCentroides(0);
		
		/**
		 * Metricas
		 * 
		 * -Distancia Euclidea
		 * -Distancaia de Manahattan
		 * -Distancia de Chevychev
		 */
		
		/*metricaKMeans metrica = new MetricaEuclidea();
		//metricaKMeans metrica = new MetricaManhattan();
		//metricaKMeans metrica = new MetricaChevychev();
		
		Clasificador c1 = new ClasificadorKMeans(fin, metrica, 500);
		*/
		
		ArrayList<Integer> capas= new ArrayList<>();
		
		/*crx
		 * capas.add(d1.getSizeCountAtributos());

		capas.add(12);
		capas.add(d1.getClases().size());
		*/
		
		capas.add(d1.getSizeCountAtributos());
		capas.add(12);
		capas.add(d1.getClases().size());

		System.out.println(d1.getClases().size());
		System.out.println(d1.getNumDatos());
		//Clasificador c1 = new ClasificadorRetropropagacion(capas, 0.05, 10000);
		//Clasificador c1 = new KNN(1);

		//System.out.println(d1.getDato(1, 1));
		/*System.out.println(d1.getSizeCountAtributos());
		EndAlgGen parada= new EndAlgGenBasico(100);
		Seleccion selecc= new SeleccionRN(0.1);
		Mutacion mut = new MutacionRN(0.1, 0.0, 0.0);
		Fitness fit = new FitnessRN();
		IniPoblacion inip= null;
		Recombinar reco = new RecombinarUnPuntoRN(0.6);
		SelectPadres sepad = new SelecetPadresRN();
		ClasificadorGenetico c1 = new ClasificadorGenetico(parada, selecc, fit, inip, mut, reco, sepad, 100, 1000, "");
		*/
		int ngen= 1000;
		int tpob= 200;
		EndAlgGen parada= new EndAlgGenBasico(ngen);
		Seleccion selecc = new SeleccionRN(4.0/tpob);
		Fitness fit = new  FitnessRN();
		IniPoblacion inip = new IniPoblacionRN(tpob, capas);
		Mutacion mut = new MutacionRN(0.3);
		Recombinar reco = new RecombinarSimple(0.6);
		SelectPadres sepad = new SelecetPadresRN();
		ClasificadorGenetico2 c1 = new ClasificadorGenetico2(parada, selecc, fit, inip, mut, reco, sepad, tpob, ngen);
		d1.Norm();
		validar(part, d1, c1, matriz);
	
		//System.out.println(c1.getBestK());
		
		
	}
}