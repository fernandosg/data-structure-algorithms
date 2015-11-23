package avlalgorithm;


public class AVLO {
	String cadena = "";
	Nodo raiz, busqueda;
	int conta = 0;
	int conteo_niveles;

	public AVLO() {
	}

	public void preOrden(Nodo nodo) {
		if (nodo == null) {
			return;
		}
		System.out.print(nodo.getValor() + ", ");
		conteo_niveles++;
		preOrden(nodo.getHijoIzq());
		preOrden(nodo.getHijoDer());
	}

	public void postOrden(Nodo nodo) {
		if (nodo == null)
			return;
		postOrden(nodo.getHijoIzq());
		postOrden(nodo.getHijoDer());
		System.out.print(nodo.getValor() + ", ");
	}

	public void inOrden(Nodo nodo) {
		if (nodo == null) {
			return;
		}
		inOrden(nodo.getHijoIzq());
		System.out.print(nodo.getValor() + " ");
		inOrden(nodo.getHijoDer());
	}

	public void mostrar(int op) {
		switch (op) {
		case 1:
			preOrden(raiz);
			break;
		case 2:
			inOrden(raiz);
			break;
		case 3:
			postOrden(raiz);
			break;
		}
	}

	public void modificar(String palabra, String definicion) {
		conteo_niveles = 0;
		Nodo nodo = encontrar(palabra);
		if (nodo != null) {
			nodo.setDefinicion(definicion);
			System.out.println("Numeros de niveles descendidos " + (conteo_niveles - 1));
		} else {
			System.out.println("La palabra que busca no existe");
		}
	}

	public void buscar(String palabra) {
		this.conteo_niveles = 0;
		Nodo nodo = encontrar(palabra);
		if (nodo != null) {
			System.out.println(nodo.getValor() + " " + nodo.getDefinicion());
			System.out.println("Definicion encontrada en el nivel " + (conteo_niveles - 1));
		} else {
			conteo_niveles=(conteo_niveles>0) ? (conteo_niveles - 1) : 0;
			System.out.println("No se encontro la definicion, se busco hasta la profunidad " +conteo_niveles);
		}
	}

	private Nodo encontrar(String nodo) {
		Nodo padre = raiz;
		while (padre != null) {
			conteo_niveles++;
			if (padre.compara(nodo) < 0)
				padre = padre.getHijoDer();
			else if (padre.compara(nodo) > 0)
				padre = padre.getHijoIzq();
			else
				return padre;
		}
		return null;
	}

	private void rotacionIzquierda(Nodo padre, Nodo hijo) {
		if (padre.getPadre() == null) {
			raiz = hijo;
			padre.setPadre(null);
		} else {
			padre.getPadre().actualizarHijo(padre, hijo);
		}
		hijo.setPadre(padre.getPadre());
		padre.eliminarNodosIzq();
		if (hijo.getHijoDer() != null) {
			padre.setHijoIzq(hijo.getHijoDer());
			hijo.setHijoDer(null);
		} else
			padre.setHijoIzq(null);
		hijo.setHijoDer(padre);
		padre.setPadre(hijo);
		padre.verificarProfunidad();
	}

	private void rotacionDerecha(Nodo padre, Nodo hijo) {
		if (padre.getPadre() == null) {
			raiz = hijo;
			hijo.setPadre(null);
		} else {
			padre.getPadre().actualizarHijo(padre, hijo);
			hijo.setPadre(padre.getPadre());
		}
		padre.setPadre(hijo);
		padre.eliminarNodosDer();
		if (hijo.getHijoIzq() != null) {
			padre.setHijoDer(hijo.getHijoIzq());
			padre.incrementarProfDer();
		} else
			padre.setHijoDer(null);
		hijo.setHijoIzq(padre);
		padre.verificarProfunidad();
	}

	public void insertar(String nodo, String definicion) {
		Nodo padre = raiz;
		System.out.println("INSERCION: "+nodo);
		if (raiz == null) {
			raiz = new Nodo(nodo, definicion, null);
		} else {
			padre = raiz;
			while (padre != null) {
				if (padre.compara(nodo) < 0) {
					if (padre.getHijoDer() != null) {
						padre = padre.getHijoDer();
						continue;
					} else
						break;
				} else if (padre.compara(nodo) == 0) {
					padre = null;
					System.out.println("Elemento repetido, inserte otra palabra porfavor");
					break;
				} else {
					if (padre.getHijoIzq() != null) {
						padre = padre.getHijoIzq();
						continue;
					} else
						break;
				}
			}
			if (padre != null)
				verificarDesbalance(padre.insertarNodo(nodo, definicion));
		}
	}

	private void verificarDesbalance(Nodo nodo) {
		Nodo padre = nodo;
		boolean bandera = false;
		while (padre.getPadre() != null) {
			padre.getPadre().setProfunidad(padre);
			if (padre.defineFactorEquilibrio()) {
				bandera = true;
				break;
			} else {
				padre.getPadre().definirPredominancia(padre.ident_izq, padre.ident_der);
				padre = padre.getPadre();
			}
		}
		if (!bandera) {
			if (padre.defineFactorEquilibrio())
				identificarRotacion(padre);
		} else
			identificarRotacion(padre);
	}

	public void identificarRotacion(Nodo padre) {
		if (padre.predom_der < padre.predom_izq) {
			if (padre.getHijoIzq().predom_der > padre.getHijoIzq().predom_izq) {
				System.out.println("Hubo rotación doble (Izq-Der) con el nodo "+padre.getHijoIzq().getValor()+" el cual tuvo un desbalanceo, su hijo fue "+padre.getHijoIzq().getHijoDer().getValor());
				this.rotacionDerecha(padre.getHijoIzq(), padre.getHijoIzq().getHijoDer());
				this.rotacionIzquierda(padre, padre.getHijoIzq());
			} else{
				System.out.println("Hubo rotación izquierda con el nodo "+padre.getValor()+" el cual tuvo un desbalanceo, su hijo fue "+padre.getHijoIzq().getValor());
				this.rotacionIzquierda(padre, padre.getHijoIzq());
			}
		} else {
			if (padre.getHijoDer().predom_izq > padre.getHijoDer().predom_der) {
				System.out.println("Hubo rotación doble (Der-Izq) con el nodo "+padre.getHijoDer().getValor()+" el cual tuvo un desbalanceo, su hijo fue "+padre.getHijoDer().getHijoIzq().getValor());
				this.rotacionIzquierda(padre.getHijoDer(), padre.getHijoDer().getHijoIzq());
				this.rotacionDerecha(padre, padre.getHijoDer());
			} else {
				System.out.println("Hubo rotación derecha con el nodo "+padre.getValor()+" el cual tuvo un desbalanceo, su hijo fue "+padre.getHijoDer().getValor());
				this.rotacionDerecha(padre, padre.getHijoDer());
			}
		}
	}

	public void eliminar(String nodo) {
		Nodo nodo_puntero,nodo_padre_temp,nodo_hijo_temp;
		conteo_niveles = 0;
		nodo_puntero = encontrar(nodo);
		if(nodo_puntero!=raiz)
			if (nodo_puntero != null) {
				if (nodo_puntero.getHijoDer() != null && nodo_puntero.getHijoIzq() != null) {
					if(nodo_puntero.getHijoDer().getHijoIzq()!=null){
						nodo_padre_temp=nodo_puntero.getPadre();
						nodo_hijo_temp=nodo_puntero;
						nodo_puntero=nodo_puntero.getHijoDer().getHijoIzq();						
						while(nodo_puntero.getHijoDer()!=null) 
							nodo_puntero=nodo_puntero.getHijoDer();
						nodo_padre_temp.actualizarHijo(nodo_hijo_temp, nodo_puntero);
						if(nodo_puntero.getHijoDer()!=null){
							nodo_puntero.getHijoDer().setPadre(nodo_puntero.getPadre());
						}
						nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoDer());
						nodo_puntero.setHijoDer(nodo_hijo_temp.getHijoDer());
						nodo_puntero.setHijoIzq(nodo_hijo_temp.getHijoIzq());						
						nodo_puntero.setPadre(nodo_padre_temp);							
					}else{
						nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoIzq());
						nodo_puntero.getHijoIzq().setPadre(nodo_puntero.getPadre());
						nodo_puntero.getHijoDer().setPadre(nodo_puntero.getHijoIzq());
						nodo_puntero.getHijoIzq().setHijoDer(nodo_puntero.getHijoDer());
						nodo_puntero.getHijoIzq().verificarProfunidad();
					}
				} else if (nodo_puntero.getHijoDer() != null) {
					nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoDer());
					nodo_puntero.getHijoDer().setPadre(nodo_puntero.getPadre());
					nodo_puntero.getHijoDer().verificarProfunidad();
				} else if (nodo_puntero.getHijoIzq() != null) {
					nodo_puntero.getPadre().actualizarHijo(nodo_puntero, nodo_puntero.getHijoIzq());
					nodo_puntero.getHijoIzq().setPadre(nodo_puntero.getPadre());
					nodo_puntero.getHijoIzq().verificarProfunidad();
				} else {
					nodo_puntero.getPadre().actualizarHijo(nodo_puntero, null);
					nodo_puntero.getPadre().verificarProfunidad();
				}
				System.out.println("Niveles descendidos para eliminar la definicion " + (conteo_niveles - 1));
			} else
				System.out.println("No se encontro ningun elemento");
		else{
			raiz=null;
			System.out.println("Haz eliminado el arbol");
		}
	}
		

}
