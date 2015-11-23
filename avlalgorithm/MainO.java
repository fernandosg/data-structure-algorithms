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
					+ "1) Cargar un archivo de definiciones \n "
					+ "2) Agregar una palabra y su definición \n "
					+ "3) Buscar palabra \n "
					+ "4) Modificar una definicion \n "
					+ "5) Eliminar una palabra \n "
					+ "6) Recorrido Preorden \n "
					+ "7) Recorrido InOrden \n "
					+ "8) Recorrido PostOrden \n "
					+ "9) Salir");			
				op=leer.nextInt();
				switch(op){
					case 1:
						System.out.println(" Cargar diccionario de un archivo de texto");
						elementos=archivo.obtenerContenido("/home/fernando/workspace/DisenoAlgoritmos/diccionario.txt");
						String[] datos;
						//System.out.println("SI SALIO??? "+elementos.size());
						for(int i=0;i<elementos.size();i++){
							datos=elementos.get(i).split(":");
							ob.insertar(datos[0], datos[1]);
						}
					break;
					case 2:
						leer.nextLine();
						System.out.println(" Escribe la palabra ");
						palabra=leer.nextLine();
						System.out.println(" Escribe la definicion ");
						definicion=leer.nextLine();
						System.out.println(palabra+" "+definicion);
						ob.insertar(palabra, definicion);
						break;
					case 3:
						leer.nextLine();
						System.out.println("Escribe la palabra a buscar");
						palabra=leer.nextLine();
						ob.buscar(palabra);
						break;					
					case 4:
						leer.nextLine();
						System.out.println("Escribe la palabra a buscar");
						palabra=leer.nextLine();
						System.out.println("Escribe la definicion a cambiar ");
						definicion=leer.nextLine();
						ob.modificar(palabra, definicion);
						break;
					case 5:
						leer.nextLine();
						System.out.println("Escribe el nodo a eliminar");
						palabra=leer.nextLine();
						ob.eliminar(palabra);
						break;
					case 6:
						System.out.println("Mostrando los elementos en PreOrden");
						ob.mostrar(1);
						break;	
					case 7:
						System.out.println("Mostrando los elementos en InOrden");
						ob.mostrar(2);
						break;	
					case 8:
						System.out.println("Mostrando los elementos en PosOrden");
						op=leer.nextInt();
						ob.mostrar(3);
						break;	
					default:
						if(op!=9)
							System.out.println("Opcion no permitida, verifique de nuevo el menu");
						else
							System.out.println("Programa terminado");
						break;
					}			
		}while(op!=9);
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
