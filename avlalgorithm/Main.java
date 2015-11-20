package avlalgorithm;

public class Main {

	public static void main(String args[]){
		AVL ob=new AVL();
		ob.insertar(11);
		ob.insertar(5);
		ob.insertar(22);
		ob.insertar(1);
		ob.insertar(7);
		ob.insertar(6);
		ob.insertar(10);
		ob.insertar(32);
		ob.insertar(4);
		ob.insertar(90);
		ob.insertar(33);
		ob.insertar(40);
		ob.insertar(30);
		ob.eliminar(7);	
		ob.mostrarArbol();
	}
}
