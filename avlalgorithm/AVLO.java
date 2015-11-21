package avlalgorithm;

import java.util.ArrayList;

public class AVLO {	
	String cadena="";
	ArrayList<Integer> orden;
	Nodo raiz,busqueda;
	int conta=0;
	public AVLO(){
		orden=new ArrayList<Integer>();
	}
	
	public void preOrden(Nodo nodo){		
		if(nodo==null){
			return;
		}
		System.out.print(nodo.getValor()+", ");
		preOrden(nodo.getHijoIzq());
		preOrden(nodo.getHijoDer());
	}
	
	public void postOrden(Nodo nodo){		
		if(nodo==null)
			return;
		postOrden(nodo.getHijoIzq());
		postOrden(nodo.getHijoDer());
		System.out.print(nodo.getValor()+", ");
	}
	public void inOrden(Nodo nodo){				
		if(nodo==null){
			return;
		}
		inOrden(nodo.getHijoIzq());
		System.out.print(nodo.getValor()+" ");
		inOrden(nodo.getHijoDer());
	}
	
	public void mostrar(int op){
		switch(op){
		case 1:
			preOrden(raiz);
			break;
		case 2:
			this.orden.clear();
			inOrden(raiz);
			break;
		case 3:
			postOrden(raiz);
			break;
		}
	}
	
	public Nodo encontrar(String nodo){
		Nodo padre=raiz;
		while(padre!=null){
			if(padre.compara(nodo)<0)
				padre=padre.getHijoDer();
			else if(padre.compara(nodo)>0)
				padre=padre.getHijoIzq();
			else 
				return padre;							
		}
		return null;
	}	
	
	private void rotacionIzquierda(Nodo padre,Nodo hijo){
		if(padre.getPadre()==null){
			raiz=hijo;
			padre.setPadre(null); 
		}else{
			padre.getPadre().actualizarHijo(padre, hijo);
		}		
		hijo.setPadre(padre.getPadre());
		padre.eliminarNodosIzq();
		if(hijo.getHijoDer()!=null){
			padre.setHijoIzq(hijo.getHijoDer());
			hijo.setHijoDer(null);
		}else
			padre.setHijoIzq(null);
		hijo.setHijoDer(padre);
		padre.setPadre(hijo);
		padre.verificarProfunidad();
	}
	
	private void rotacionDerecha(Nodo padre,Nodo hijo){		
		if(padre.getPadre()==null){			
			raiz=hijo;
			hijo.setPadre(null);
		}else{
			padre.getPadre().actualizarHijo(padre, hijo);
			hijo.setPadre(padre.getPadre());
		}
		padre.setPadre(hijo);
		padre.eliminarNodosDer(); 
		if(hijo.getHijoIzq()!=null){
			padre.setHijoDer(hijo.getHijoIzq());		
			padre.incrementarProfDer();
		}else
			padre.setHijoDer(null); 
		hijo.setHijoIzq(padre);
		padre.verificarProfunidad();
	}	
	
	public void insertar(String nodo){
		Nodo padre = raiz;
		if(raiz==null){
			raiz=new Nodo(nodo,null);
		}else{			
			padre=raiz;
			while(padre!=null){
				if(padre.compara(nodo)<0){										
					if(padre.getHijoDer()!=null){
						padre=padre.getHijoDer();
						continue;
					}else
						break;
				}else{					
					if(padre.getHijoIzq()!=null){
						padre=padre.getHijoIzq();
						continue;
					}else
						break;
				}
			}
			verificarDesbalance(padre.insertarNodo(nodo));			
		}
	}
	
	private void verificarDesbalance(Nodo nodo){
		Nodo padre=nodo;	
		boolean bandera=false;
		while(padre.getPadre()!=null){
			padre.getPadre().setProfunidad(padre);
			if(padre.defineFactorEquilibrio()){
				bandera=true;
				break;
			}else{
				padre.getPadre().definirPredominancia(padre.ident_izq, padre.ident_der);
				padre=padre.getPadre();
			}
		}
		if(!bandera){
			if(padre.defineFactorEquilibrio())
				identificarRotacion(padre);			
		}else
			identificarRotacion(padre);
			
				
	}
	
	
	public void identificarRotacion(Nodo padre){
		if(padre.predom_der<padre.predom_izq){
			if(padre.getHijoIzq().predom_der>padre.getHijoIzq().predom_izq){				
				this.rotacionDerecha(padre.getHijoIzq(), padre.getHijoIzq().getHijoDer());
				this.rotacionIzquierda(padre, padre.getHijoIzq());
			}else
				this.rotacionIzquierda(padre, padre.getHijoIzq());			
		}else{
			if(padre.getHijoDer().predom_izq>padre.getHijoDer().predom_der){
				this.rotacionIzquierda(padre.getHijoDer(), padre.getHijoDer().getHijoIzq());
				this.rotacionDerecha(padre, padre.getHijoDer());
			}else{
				this.rotacionDerecha(padre, padre.getHijoDer());
			}
		}
	}			
	
	public void eliminar(String nodo){
		Nodo nodo_puntero;
		nodo_puntero=encontrar(nodo);
		if(nodo_puntero!=null){
			if(nodo_puntero.getHijoDer()!=null && nodo_puntero.getHijoIzq()!=null){
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoIzq());							
				nodo_puntero.getHijoIzq().setPadre(nodo_puntero.getPadre());											
				nodo_puntero.getHijoDer().setPadre(nodo_puntero.getHijoIzq());
				nodo_puntero.getHijoIzq().setHijoDer(nodo_puntero.getHijoDer());
				nodo_puntero.getHijoIzq().verificarProfunidad();
			}else if(nodo_puntero.getHijoDer()!=null){
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoDer());
				nodo_puntero.getHijoDer().setPadre(nodo_puntero.getPadre());
				nodo_puntero.getHijoDer().verificarProfunidad();
			}else if(nodo_puntero.getHijoIzq()!=null){
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoIzq());
				nodo_puntero.getHijoIzq().setPadre(nodo_puntero.getPadre());
				nodo_puntero.getHijoIzq().verificarProfunidad();
			}else{
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, null);
				nodo_puntero.getPadre().verificarProfunidad();
			}
		}else
			System.out.println("No se encontro ningun elemento");
	}
	
}
