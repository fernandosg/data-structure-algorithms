package avlalgorithm;


public class Nodo {
	private Nodo hijo_izq, hijo_der, padre;
	private int valor;
	public int predom_izq=0,predom_der=0;

	public Nodo(int nodo, Nodo padre) {
		hijo_izq = null;
		hijo_der = null;
		valor=nodo;
		this.padre = padre;
	}
	
	public int defineFactorEquilibrio(){
		return (hijo_izq!=null && hijo_der!=null) ? 0 : 1;
	}

	public Nodo getHijoIzq() {
		return hijo_izq;
	}

	public Nodo getHijoDer() {
		return hijo_der;
	}

	public int getValor() {
		return valor;
	}

	public void insertarHijoIzq(int valor, Nodo padre) {
		this.hijo_izq = new Nodo(valor, padre);
	}

	public Nodo getPadre() {
		return padre;
	}
	
	public void cambiarHijo(Nodo nodo,Nodo nuevo_hijo){
		if(hijo_izq.getValor()==nodo.valor)
			hijo_izq=nuevo_hijo;		
		else
			hijo_der=nuevo_hijo;
	}
	
	public void setPadre(Nodo nodo){
		padre=nodo;
	}

	public void setHijoIzq(Nodo nodo) {
		this.hijo_izq = nodo;
	}

	public void setHijoDer(Nodo nodo) {
		this.hijo_der = nodo;
	}
	
	public void actualizarHijo(Nodo nodo_viejo,Nodo nodo_nuevo){
		if(hijo_izq!=null)
			if(hijo_izq.getValor()==nodo_viejo.getValor()){
				hijo_izq=nodo_nuevo;
			}
		if(hijo_der!=null){
			if(hijo_der.getValor()==nodo_viejo.getValor()){
				hijo_der=nodo_nuevo;
			}
		}			
	}
	
	public void identificarHijo(Nodo nodo){
		predom_izq=0;
		predom_der=0;
		if(hijo_izq!=null){
			if(hijo_izq.getValor()==nodo.getValor())
				this.predom_izq=1;
			else
				this.predom_der=1;			
		}else
			this.predom_der=1;
		
	}
}
