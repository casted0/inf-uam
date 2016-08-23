package es.uam.eps.bmi.search.parsing;

import org.jsoup.Jsoup;

/**
 * Clase que implementa la interfaz TextParser, que limpia un texto de
 * caracteres propios de HTML.
 * 
 * @author Mario Valdemaro Garcia Roque <mariov.garcia@estudiante.uam.es> y
 * Angel Fuente Ortega <angel.fuente@estudiante.uam.es>
 *
 */
public class HTMLSimpleParser implements TextParser {

	@Override
	public String parse(String text) {
		return Jsoup.parse(text).text();
	}

	public static void main(String[] args){
		TextParser parser = new HTMLSimpleParser();
		System.out.println(parser.parse("<html>hola mundo </html>"));
	}
}
