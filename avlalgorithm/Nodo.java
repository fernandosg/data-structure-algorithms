package avlalgorithm;


public class Nodo {
	private Nodo hijo_izq, hijo_der, padre;
	private String valor;
	public int predom_izq=0,predom_der=0,ident_izq=0,ident_der=0;
	private int profundidad=0,profundidad_izq,profundidad_der;
	private boolean estado=false;
	public byte pos=0;
	
	public int identificarPosicion(Nodo padre){
		if(padre.getHijoDer()!=null)
			if(padre.getHijoDer().getValor()==valor)
				return 2;		
		if(padre.getHijoIzq()!=null)
			if(padre.getHijoIzq().getValor()==valor)
				return 1;		
		return 0;
		
	}
	
	public int obtenerProfundidad(){
		return profundidad;
	}
	
	public void definirPredominancia(int izq,int der){
		this.ident_der=der;
		this.ident_izq=izq;
	}
	
	public void setProfunidad(Nodo nodo){		
		if(this.getPadre()==null){
			if(this.hijo_der!=null)
				if(this.hijo_der.getValor()==nodo.getValor()){
					if(this.getHijoIzq()==null)
						predom_der++;
					else
						predom_der=(nodo.predom_der>nodo.predom_izq) ? nodo.predom_der+1 : nodo.predom_izq+1;
				}else{
					if(this.hijo_der==null)
						predom_izq++;	
					else{
						predom_izq=(nodo.predom_izq>nodo.predom_der) ? nodo.predom_izq+1 : nodo.predom_der+1;
					}
				}
			else
				predom_izq=(nodo.predom_izq>nodo.predom_der) ? nodo.predom_izq+1 : nodo.predom_der+1;			
		}else{
			if(hijo_der!=null)
				if(this.hijo_der.getValor()==nodo.getValor()){
					if(hijo_izq==null)
						predom_der++;
					else
						predom_der=(nodo.predom_der>nodo.predom_izq) ? nodo.predom_der+1 : nodo.predom_der+1;
				}
			if(hijo_izq!=null)
				if(this.hijo_izq.getValor()==nodo.getValor()){
					if(hijo_der==null)
						predom_izq++;
					else
						predom_izq=(nodo.predom_izq>nodo.predom_der) ? nodo.predom_izq+1 : nodo.predom_der+1;
				}
		}
	}
	
	public void incrementarProfIzq(){
		predom_izq++;
	}
	
	public void incrementarProfDer(){
		predom_der++;
	}
	
	public boolean defineFactorEquilibrio(){
		return Math.abs(this.predom_der-this.predom_izq)>=2;
	}			
	
	
	public Nodo verificarProfunidad(){
		Nodo padre=getPadre();
		Nodo hijo=this;
		while(padre!=null){
			padre.setProfunidad(hijo);
			if(padre.defineFactorEquilibrio()){
				return padre;				
			}else{
				if(padre.getPadre()!=null)
				hijo=padre;
				padre=padre.getPadre();
			}
		}
		return padre;
	}
	/*
	private void definirEstado(){
		if(!(hijo_izq!=null && hijo_der!=null))
			if(padre!=null)
				padre.aumentarProfundidad();					
	}*/

	public Nodo(String nodo, Nodo padre) {
		hijo_izq = null;
		hijo_der = null;
		valor=nodo;
		this.padre = padre;
	}

	
	
	
	public boolean checarDesbalance(String tipo){
		int test=predom_der;		
		if(tipo=="izquierda"){		
			return (Math.abs(predom_der-(predom_izq+1))>=2);			
		}else{
			return (Math.abs(predom_izq-(predom_der+1))>=2);
		}
	}
	
	public boolean esHoja(){
		return hijo_izq==null && hijo_der==null;
	}
	
	public Nodo insertarNodo(String palabra){
		Nodo me=this;
		if(valor.compareTo(palabra)>0){
			hijo_izq=new Nodo(palabra,me);
			hijo_izq.ident_izq=1;
			return hijo_izq;
		}else{
			hijo_der=new Nodo(palabra,me);
			hijo_der.ident_der=1;
			return hijo_der;
		}
	}		
	
	public Nodo getHijoIzq() {
		return hijo_izq;
	}

	public Nodo getHijoDer() {
		return hijo_der;
	}

	public int compara(String nueva_palabra){
		return (this.valor.compareTo(nueva_palabra));
	}

	public String getValor() {
		return valor;
	}

	public void insertarHijoIzq(String valor, Nodo padre) {
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
	
	public void eliminarNodosIzq(){
		this.predom_izq=0;
	}
	
	
	public void eliminarNodosDer(){
		this.predom_der=0;
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
	
}
