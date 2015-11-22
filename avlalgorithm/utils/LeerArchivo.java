package avlalgorithm.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeerArchivo {

	public ArrayList<String> obtenerContenido(String ruta){
		ArrayList<String> contenido=new ArrayList<String>();
		try {
			FileReader file=new FileReader(ruta);
			BufferedReader br=new BufferedReader(file);
			String linea="";
			while((linea=br.readLine())!=null){
				contenido.add(linea);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return contenido;
	}
}
