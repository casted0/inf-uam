/** 
 * @author Alejandro  y Mario
 * 
 * Esta aplicación muestra el mensaje "Hola mundo!" por pantalla 
 */ 
public class forMejorado { 
/**
* Punto de entrada a la aplicación.
*
* Este método imprime el logaritmo Neperiano del número que se le pasa como entrada
*
* @param args Los argumentos de la línea de comandos. Se espera un número como primer parámetro
*/
public static void main (String[] args) {

	if (args.length == 0) {
		System.out.println ("Se espera mas de un argumento");
		return;
	} 

	Integer i=0;
	ArrayList <Integer> arr= new ;
	for (String s : args) {
		i+= Integer.parseInt(s);
	}
 	System.out.println ("Media:"+((i/args.length)*1.0)+"");
}
}
