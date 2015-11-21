package avlalgorithm;

public class MainO {

	public static void main(String args[]){
		AVLO ob=new AVLO();
		ob.insertar("Marco");
		ob.insertar("Dado");
		ob.insertar("Zapato");
		ob.insertar("Arbol");
		ob.insertar("Hielo");
		ob.insertar("Saco");
		ob.insertar("Faro");
		ob.eliminar("Zapato");
		ob.eliminar("Faro");
		ob.eliminar("Arbol");
		ob.mostrar(1);				
	}
}
