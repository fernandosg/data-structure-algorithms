package knapsackproblem;

import java.util.ArrayList;

import javax.swing.JFileChooser;

import utils.LeerArchivo;

public class Main {

	public static void main(String args[]){
		JFileChooser file=new JFileChooser();
		file.showOpenDialog(null);
		ArrayList<String> contenido=LeerArchivo.obtenerContenido(file.getSelectedFile().toString());		
		String[][] valores=new String[Integer.parseInt(contenido.get(0))][Integer.parseInt(contenido.get(1))];		
		for(int i=0,x=3;i<valores.length;i++,x++)
			valores[i]=contenido.get(x).split(" ");		
		AlgoritmoGenetico ob=new AlgoritmoGenetico();
		//cantidad_individuos,valores,umbral,generaciones,porcentaje_cruza,porcentaje_mutacion
		ob.init(Integer.parseInt(contenido.get(0)),Integer.parseInt(contenido.get(0))*3, valores, Integer.parseInt(contenido.get(2)),15,0.85,0.70);
		ob.setSP(1.3);
		ob.initAlgoritmo();
	}
}
