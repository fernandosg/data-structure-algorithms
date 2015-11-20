package avlalgorithm;

import java.util.ArrayList;

public class AVL {
	private ArrayList<int[]> arbol;
	int[] nodo_padre,nodo_hijo_izq,nodo_hijo_der;
	int pos_hijo_der,pos_nuevo_hijo,pos_hijo_izq;
	public AVL() {
		arbol = new ArrayList<int[]>();
	}

	private int getPosHijoIzq(int pos) {
		return 2 * pos + 1;
	}

	private int getPosHijoDer(int pos) {
		return 2 * pos + 2;
	}

	private int getPadre(int pos) {
		return ((pos<=1) ? 0 : ((pos % 2 == 0) ? (pos - 2) / 2 : (pos - 1) / 2));
	}	
		
	
	private void verificarFactorEquilibrio(int pos_nodo,byte izq_o_der){
		int pos_padre=pos_nodo,factor_eq=0,izq=0,der=0,predom_izq=0,predom_der=0;
		while(true){
			pos_padre=getPadre(pos_padre);
			if(izq_o_der==0){
				if(this.arbol.get(getPosHijoIzq(pos_padre))==null){
					arbol.get(pos_padre)[2]=factor_eq+(arbol.get(pos_padre)[2]+1);
					factor_eq=arbol.get(pos_padre)[2];
					der=1;
					predom_der++;
				}else
					break;				
			}else{
				if(this.arbol.get(getPosHijoDer(pos_padre))==null){
					arbol.get(pos_padre)[2]=factor_eq+(arbol.get(pos_padre)[2]+1);
					factor_eq=arbol.get(pos_padre)[2];
					izq=1;
					predom_izq++;
				}else
					break;				
			}
			if(arbol.get(pos_padre)[2]>1){
				if((izq+der)>2){
					rotacionDoble(pos_padre,pos_nodo,predom_izq,predom_der);
				}else{
					if(izq==1)
						rotacionIzqSimple(pos_padre,pos_nodo);
					else if(der==1)
						rotacionDerSimple(pos_padre,pos_nodo);
				}
				break;
			}
			if(getPadre(pos_padre)==pos_padre)
				break;			
		}
	}
	
	private void rotacionDerSimple(int pos,int pos_nodo_nuevo){
		pos_hijo_der=getPosHijoDer(pos);
		nodo_padre=arbol.get(pos);
		nodo_hijo_der=arbol.get(pos_hijo_der);
		nodo_hijo_izq=null;
		pos_nuevo_hijo=0;
		nodo_hijo_der[0]=nodo_padre[0];
		arbol.set(pos, nodo_hijo_der);
		if(arbol.get(getPosHijoIzq(pos_hijo_der))!=null){
			nodo_hijo_izq=arbol.get(getPosHijoIzq(pos_hijo_der));
			nodo_hijo_izq[0]=getPosHijoIzq(pos);
			pos_nuevo_hijo=getPosHijoDer(pos);
			if(pos_nuevo_hijo>arbol.size())
				redimensionarArbol(0,(pos_nuevo_hijo-arbol.size()));						
			arbol.set(getPosHijoIzq(pos_hijo_der), null);
			arbol.set(nodo_hijo_izq[0], nodo_hijo_izq);
		}
		arbol.set(pos_hijo_der, arbol.get(pos_nodo_nuevo));
		arbol.set(getPosHijoIzq(pos), nodo_padre);
		arbol.set(pos_nodo_nuevo, null);
	}
	
	private void rotacionIzqSimple(int pos,int pos_nodo_nuevo){
		pos_hijo_izq=this.getPosHijoIzq(pos);
		nodo_padre=arbol.get(pos);
		nodo_hijo_izq=arbol.get(pos_hijo_izq);
		nodo_hijo_der=null;
		pos_nuevo_hijo=0;
		nodo_hijo_izq[0]=nodo_padre[0];
		arbol.set(pos, nodo_hijo_izq);
		if(arbol.get(getPosHijoDer(pos_hijo_izq))!=null){
			nodo_hijo_der=arbol.get(getPosHijoDer(pos_hijo_izq));
			nodo_hijo_der[0]=getPosHijoDer(pos);
			pos_nuevo_hijo=getPosHijoIzq(getPosHijoDer(pos));
			if(pos_nuevo_hijo>arbol.size())
				redimensionarArbol(0,(pos_nuevo_hijo-arbol.size()));
			arbol.set(pos_nuevo_hijo, nodo_hijo_der);	
			arbol.set(getPosHijoDer(pos_hijo_izq),null);
		}
		arbol.set(pos_hijo_izq, arbol.get(pos_nodo_nuevo));
		arbol.set(pos_nodo_nuevo, null);
		arbol.set(getPosHijoDer(pos), nodo_padre);		
	}
	
	private void rotacionDoble(int pos,int pos_nodo_nuevo,int izq,int der){
		if(izq>der){
			rotacionDerSimple(pos, pos_nodo_nuevo);
			verificarFactorEquilibrio(pos, (byte) 1);
		}else{
			rotacionIzqSimple(pos, (byte)0);
		}
	}
	
	private int encontrar(int valor){
		int itera=0;
		while(itera<arbol.size()){
			if(arbol.get(itera)==null)
				break;
			if(arbol.get(itera)[1]>valor){
				itera=getPosHijoIzq(itera);
			}else if(arbol.get(itera)[1]<valor)
				itera=getPosHijoDer(itera);
			else
				return itera;			
		}
		return -1;
	}	
	
	public void eliminacionYRecuperacionHijos(int pos_nodo){
		if(arbol.get(this.getPosHijoIzq(pos_nodo))!=null){
			arbol.set(pos_nodo, arbol.get(getPosHijoDer(pos_nodo)));
			arbol.set(getPosHijoDer(pos_nodo),null);
		}else if(arbol.get(getPosHijoDer(pos_nodo))!=null){
			arbol.set(pos_nodo, arbol.get(getPosHijoIzq(pos_nodo)));
			arbol.set(getPosHijoIzq(pos_nodo),null);
		}else
			arbol.set(pos_nodo, null);		
	}
	
	public void eliminar(int cantidad){
		int pos_nodo=encontrar(cantidad);
		if(pos_nodo!=-1){
			if(this.getPosHijoDer(pos_nodo)<=arbol.size()-1){
				if(arbol.get(this.getPosHijoIzq(pos_nodo))!=null && arbol.get(this.getPosHijoDer(pos_nodo))!=null){
					this.arbol.set(pos_nodo, this.arbol.get(this.getPosHijoIzq(pos_nodo)));
					this.arbol.set(this.getPosHijoIzq(pos_nodo),null);
				}else
					this.eliminacionYRecuperacionHijos(pos_nodo);				
			}else
				this.eliminacionYRecuperacionHijos(pos_nodo);
			this.verificarFactorEquilibrio(pos_nodo, (byte) ((pos_nodo%2)!=0 ? 1 : 0));
		}else{
			System.out.println("No lo encontre");
		}
		
	}

	public void insertar(int nodo) {
		if (arbol.size() == 0) {
			arbol.add(new int[] { -1, nodo, 0 });
			arbol.add(null);
			arbol.add(null);
		} else {
			for (int i = 0, pos = 0, lim = 0; true;) {
				if (arbol.get(i)[1] > nodo) {
					pos = getPosHijoIzq(i);
					if (pos >= arbol.size()-1) {
						lim = pos - arbol.size();
						redimensionarArbol(0, lim + 2);
					}
				} else {
					pos = getPosHijoDer(i);
					if (pos >= arbol.size()-1) {
						lim = pos - arbol.size();
						redimensionarArbol(0, lim + 2);
					}
				}
				i = pos;
				if (arbol.get(i) == null) {
					arbol.set(pos, new int[] { this.getPadre(pos), nodo, 0 });					
					this.verificarFactorEquilibrio(pos, (byte) ((pos%2)!=0 ? 1 : 0));
					break;
				}

			}
		}
	}

	public void mostrarArbol() {
		for (int i = 0; i < arbol.size(); i++) {
			if (arbol.get(i) == null)
				System.out.print("() ");
			else
				System.out.print(arbol.get(i)[1] + " ");
		}
	}

	private void redimensionarArbol(int z, int lim) {
		while (z < lim) {
			arbol.add(null);
			z++;
		}
	}
}
