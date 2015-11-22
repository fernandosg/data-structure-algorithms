package avlalgorithm;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import avlalgorithm.utils.LeerArchivo;

public class MainO {

	static AVLO ob=new AVLO();
	public void init(){
		Scanner leer=new Scanner(System.in);
		LeerArchivo archivo=new LeerArchivo();
		ArrayList<String> elementos;
		int op=0;
		String palabra,definicion;
		do{
			System.out.println("\n MENU DE OPCIONES: \n "
					+ "1) Insertar definicion \n "
					+ "2) Cargar diccionario \n "
					+ "3) Buscar definicion \n "
					+ "4) Mostrar elementos \n"
					+ "5) Editar elemento \n"
					+ "6) Eliminar definicion \n"
					+ "7) Estadisticas \n "
					+ "8) Salir");
			try{
				op=leer.nextInt();
				switch(op){
					case 1:
						leer.nextLine();
						System.out.println(" Escribe la palabra ");
						palabra=leer.nextLine();
						System.out.println(" Escribe la definicion ");
						definicion=leer.nextLine();
						System.out.println(palabra+" "+definicion);
						ob.insertar(palabra, definicion);
						break;
					case 2:
						System.out.println(" Cargar diccionario de un archivo de texto");
						elementos=archivo.obtenerContenido("/home/fernando/workspace/DisenoAlgoritmos/diccionario.txt");
						String[] datos;
						//System.out.println("SI SALIO??? "+elementos.size());
						for(int i=0;i<elementos.size();i++){
							datos=elementos.get(i).split(":");
							ob.insertar(datos[0], datos[1]);
						}
						break;
					case 3:
						leer.nextLine();
						System.out.println("Escribe la palabra a buscar");
						palabra=leer.nextLine();
						ob.buscar(palabra);
						break;
					case 4:
						System.out.println("Mostrar los elementos del arbol por \n"
								+ "1) PreOrden \n"
								+ "2) InOrden \n"
								+ "3) PosOrden");
						op=leer.nextInt();
						ob.mostrar(op);
						break;
					case 5:
						leer.nextLine();
						System.out.println("Escribe la palabra a buscar");
						palabra=leer.nextLine();
						System.out.println("Escribe la definicion a cambiar ");
						definicion=leer.nextLine();
						ob.modificar(palabra, definicion);
						break;
					case 6:
						leer.nextLine();
						System.out.println("Escribe el nodo a eliminar");
						palabra=leer.nextLine();
						ob.eliminar(palabra);
						break;
					}
			}catch(InputMismatchException e){
				System.out.println("PARA EL MENU SOLO SE ACEPTAN DIGITOS");
				op=1;
			}
		}while(op<8);
	}

	public static void main(String args[]){
		MainO ob=new MainO();
		ob.init();//*/
		/*
		AVLO ob=new AVLO();		
		ob.insertar("Marco","");
		ob.insertar("Dado","");
		ob.insertar("Arbol","");
		/*
		ob.insertar("Zapato","");
		ob.insertar("Arbol","");
		ob.insertar("Hielo","");
		ob.insertar("Marco","");
		ob.insertar("Faro","");
		ob.insertar("Saco", "");
		ob.mostrar(1);			//*/
		//ob.eliminar("Dado");
		System.out.println("");
//		ob.mostrar(1);
	}
}
