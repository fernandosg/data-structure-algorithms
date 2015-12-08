package knapsackproblem;

import java.util.ArrayList;

import javax.swing.JFileChooser;

import utils.Archivo;

public class Main {

	public static void main(String args[]){
		JFileChooser file=new JFileChooser();
		file.showOpenDialog(null);
		ArrayList<String> contenido=Archivo.obtenerContenido(file.getSelectedFile().toString());	
		String salida="";
		file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		file.showOpenDialog(null);
		salida=file.getSelectedFile().toString();
		String[][] valores=new String[Integer.parseInt(contenido.get(0))][Integer.parseInt(contenido.get(1))];		
		for(int i=0,x=3;i<valores.length;i++,x++)
			valores[i]=contenido.get(x).split("\\s+");		
		AlgoritmoGenetico ob=new AlgoritmoGenetico();
		//cantidad_individuos,valores,umbral,generaciones,porcentaje_cruza,porcentaje_mutacion
		ob.init(Integer.parseInt(contenido.get(0)),Integer.parseInt(contenido.get(0))*1, valores, Integer.parseInt(contenido.get(2)),200,0.25,0.02);
		ob.setSP(1.3);
		ob.setLimiteGeneraciones(5);
		ob.initAlgoritmo();
		Archivo.escribirContenido(ob.getSoluciones(), salida);
	}
}
