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
		for(int i=0;i<orden.size();i++)
			System.out.print(orden.get(i)+" ");		
	}
	
	
	public void eliminar(int nodo){
		Nodo nodo_puntero;
		nodo_puntero=encontrar(nodo);
		if(nodo_puntero!=null){
			if(nodo_puntero.getHijoDer()!=null && nodo_puntero.getHijoIzq()!=null){
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoIzq());
				nodo_puntero.getHijoIzq().setPadre(nodo_puntero.getPadre());
			}else if(nodo_puntero.getHijoDer()!=null){
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoDer());
				nodo_puntero.getHijoDer().setPadre(nodo_puntero.getPadre());
			}else if(nodo_puntero.getHijoIzq()!=null){
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoIzq());
				nodo_puntero.getHijoIzq().setPadre(nodo_puntero.getPadre());
			}else{
				nodo_puntero.getPadre().actualizarHijo(nodo_puntero, null);
			}
		}else
			System.out.println("No se encontro ningun elemento");
	}
	
	private Nodo encontrar(int nodo){	
		if(raiz!=null){
			busqueda=raiz;
			while(busqueda!=null){
				if(busqueda.getValor()>nodo){
					busqueda=busqueda.getHijoIzq();
				}else if(busqueda.getValor()<nodo){
					busqueda=busqueda.getHijoDer();
				}else
					return busqueda;
			}
		}
		return null;
	}
	
	public void insertar(int nodo){
		Nodo vac = null,padre = null;
		if(raiz==null){
			raiz=new Nodo(nodo,null);
			vac=raiz;
		}else{
			vac=raiz;
			while(true){
				if(vac==null){
					vac=new Nodo(nodo,padre);
					if(nodo>padre.getValor())
						padre.setHijoDer(vac);
					else
						padre.setHijoIzq(vac);
					break;
				}else if(vac.getValor()>nodo){
					padre=vac;
					vac=vac.getHijoIzq();					
					continue;
				}else{
					padre=vac;
					vac=vac.getHijoDer();
					continue;
				}				
										
			}
			verificarFactorEquilibrio(vac);
		}
	}
	
	private void rotacionDoble(Nodo nodo_padre,Nodo nodo_hijo,int predom_der,int predom_izq,Nodo ultimo_nodo){
		if(predom_der>predom_izq){
			rotarDerechaSimple(nodo_hijo, nodo_hijo.getHijoDer());			
			this.verificarFactorEquilibrio(nodo_hijo);
		}else if(predom_izq>predom_der){
			rotarIzquierdaSimple(nodo_hijo, nodo_hijo.getHijoIzq());
		}else{
			if(nodo_hijo.getHijoDer()!=null){
				if(nodo_hijo.getHijoDer().getValor()==ultimo_nodo.getValor())
					rotarDerechaSimple(nodo_hijo,nodo_hijo.getHijoDer());
				else
					rotarIzquierdaSimple(nodo_hijo,nodo_hijo.getHijoDer());				
			}else
				rotarIzquierdaSimple(nodo_hijo,nodo_hijo.getHijoIzq());
		}		
		this.verificarFactorEquilibrio(nodo_hijo);
	}
	
	public void verificarFactorEquilibrio(Nodo nodo){
		int predom_izq=0,predom_der=0,factor_eq=0;
		Nodo vac=nodo.getPadre(),vac_hijo=nodo;
		while(true){
			if(vac==null)
				break;
			factor_eq=factor_eq+vac.defineFactorEquilibrio();
			vac.identificarHijo(vac_hijo);
			predom_der=predom_der+vac.predom_der;
			predom_izq=predom_izq+vac.predom_izq;
			if(factor_eq>1){
				if(predom_der>0 && predom_izq>0)
					rotacionDoble(vac,vac_hijo,predom_der,predom_izq,nodo);
				else{
					if(predom_der>0){
						rotarDerechaSimple(vac,vac_hijo);
					}else if(predom_izq>0){
						rotarIzquierdaSimple(vac,vac_hijo);
					}
				}
				break;
			}
			vac_hijo=vac;
			vac=vac.getPadre();			
		}
	}
	
	private void rotarDerechaSimple(Nodo nodo_padre,Nodo nodo_hijo){	
		nodo_hijo.setPadre(nodo_padre.getPadre());
		nodo_padre.getPadre().actualizarHijo(nodo_padre, nodo_hijo);
		nodo_padre.setPadre(nodo_hijo);
		nodo_padre.setHijoDer(null);
		if(nodo_hijo.getHijoIzq()!=null)
			nodo_padre.setHijoDer(nodo_hijo.getHijoIzq());										
		nodo_hijo.setHijoIzq(nodo_padre);
	}
	
	private void rotarIzquierdaSimple(Nodo nodo_padre,Nodo nodo_hijo){
		nodo_hijo.setPadre(nodo_padre.getPadre());
		nodo_padre.getPadre().actualizarHijo(nodo_padre, nodo_hijo);
		nodo_padre.setPadre(nodo_hijo);
		nodo_padre.setHijoIzq(null);
		if(nodo_hijo.getHijoDer()!=null){
			nodo_padre.setHijoIzq(nodo_hijo.getHijoDer()); 		
		}
		nodo_hijo.setHijoDer(nodo_padre);	
	}
}
