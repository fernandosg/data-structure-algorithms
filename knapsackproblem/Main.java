package knapsackproblem;

import java.util.ArrayList;


import utils.LeerArchivo;

public class Main {

	public static void main(String args[]){
		ArrayList<String> contenido=LeerArchivo.obtenerContenido("/home/fernando/workspace/DisenoAlgoritmos/assets/knackpack_instancias/a01.0.txt");		
		String[][] valores=new String[Integer.parseInt(contenido.get(0))][Integer.parseInt(contenido.get(1))];		
		for(int i=0,x=3;i<valores.length;i++,x++)
			valores[i]=contenido.get(x).split(" ");		
		AlgoritmoGenetico ob=new AlgoritmoGenetico();
		ob.init(Integer.parseInt(contenido.get(0)), valores, Integer.parseInt(contenido.get(2)),15,0.85,0.70);
		ob.setSP(1.3);
		ob.initAlgoritmo();
	}
}
