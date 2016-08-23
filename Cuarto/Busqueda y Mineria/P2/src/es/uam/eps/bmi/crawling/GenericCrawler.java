package es.uam.eps.bmi.crawling;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.regex.*;
import java.util.*;

import javax.net.ssl.HttpsURLConnection;


public class GenericCrawler {
	
	int maxPages;
	Set<String> urls;
	
	
	
	public GenericCrawler(int maxPages) {
		super();
		this.maxPages = maxPages;
		this.urls = new HashSet<>();
	}

	public Set<String> hrefs(String text){
		Set<String> refs= new HashSet<String>();
		Pattern p = Pattern.compile("href[ \r\n]*=[ \r\n]*(\")?(https?://)([0-9a-z.-]+)[.][a-z.]{2,6}");
		Matcher m = p.matcher(text);
		while(m.find()){
			refs.add(m.group().split("href[ \r\n]*=[ \r\n]*(\")?")[1]);
		}
		return refs;
		

	}
	public boolean isHttps(String url){
		Pattern p = Pattern.compile("https");
		Matcher m = p.matcher(url);
		return m.find();
	}
	
	/*public static void main (String args[]){
		String serverName = "www.google.es";
		int portNumber = 80;
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		String inputLine=null;
		String response = new String();
		GenericCrawler gc = new GenericCrawler();
		try {
			echoSocket = new Socket(serverName, portNumber);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader( echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + serverName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for " + "the connection to: " + serverName);
			System.exit(1);
		}
		
		BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
		String userInput;
		
		try {
			
			out.println("GET / HTTP/1.1\r\n"
					+ 	"User-Agent: Mozilla/5.0\r\n"
					+ 	"Host: "+serverName+"\r\n"
					+ 	"Accept: text/html\r\n"
					+ 	"Connection: close\r\n\r\n");
			System.out.println("echo: " + in.readLine());
			int i=0;
			while ((inputLine = in.readLine()) != null) {
				response=response.concat(inputLine);
			}
			for(String s:gc.hrefs(response)){
				System.out.println(s);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.close();
		try {
			in.close();
			stdIn.close();
			echoSocket.close();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/
	
	public void httpUrlCrawler(String url){
		
	}
	public List<String> httpsUrlCrawler(String https_url){
		URL url;
		String response = new String();
		ArrayList<String> pages= new ArrayList<>();
		try {	
			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			if(responseCode!=200)
				return pages;
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response=response.concat(inputLine);
			}
			in.close();
			for(String s: hrefs(response)){
				if(urls.add(s))
					pages.add(s);
			}
			
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		}
		System.out.println("Pginas descubiertas:"+pages.size());
		return pages;
	}
	
	public void webCrawler(String page){
		int npage=0;
		urls.add(page);
		ArrayList<String> pages= new ArrayList<>();
		pages.add(page);
		while(npage<maxPages && pages.size()>0){
			npage++;
			System.out.println("pagina numero:"+ npage);
			String actualPage=pages.remove(0);
			if(isHttps(actualPage)){
				pages.addAll(httpsUrlCrawler(actualPage));
			}
			else{
				
			}
		}
		System.out.println(urls);
		
	}
	public static void main(String [] args){
		GenericCrawler gc= new GenericCrawler(1000);
		gc.webCrawler("https://www.reddit.com/r/linux");
	}
	
	/*public static void main(String [] args){
		GenericCrawler gc= new GenericCrawler();
		String https_url = "https://moodle.uam.es/";
		URL url;
		String response = new String();
		try {	
			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response=response.concat(inputLine);
			}
			in.close();
			for(String s:gc.hrefs(response)){
				System.out.println(s);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/
	
	
	/*public static void main(String[] args) throws Exception {
		String USER_AGENT = "Mozilla/5.0";
		String url = "http://www.google.com/";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());

	}*/
}