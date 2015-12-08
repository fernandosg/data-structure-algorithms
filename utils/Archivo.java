package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Archivo {

	public static ArrayList<String> obtenerContenido(String ruta){
		ArrayList<String> contenido=new ArrayList<String>();
		try {
			FileReader file=new FileReader(ruta);
			BufferedReader br=new BufferedReader(file);
			String linea="";
			while((linea=br.readLine())!=null){
				contenido.add(linea);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contenido;
	}
	
	public static void escribirContenido(Object[] contenido,String ruta){
		try {
			PrintWriter pw=new PrintWriter(new File(ruta+"salida.txt"));
			for(int i=0;i<contenido.length;i++)
				pw.println(contenido[i].toString());
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
