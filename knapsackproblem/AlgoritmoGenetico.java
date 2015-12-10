package knapsackproblem;

import java.util.Arrays;
import java.util.Random;

import utils.Archivo;
import utils.Busqueda;
import utils.Elemento;

public class AlgoritmoGenetico {
	private Solucion[] soluciones;	
	private Solucion primer_hijo_generado,segundo_hijo_generado;
	private int generaciones=0,individuos,padre=-1,madre=-1,umbral,limite_gen_cont,cont_gen_sin_mejorar,sin_soluciones;	
	private double sp,porcentaje_mutacion;
	private float random_cruza;	
	private Random rand;
	private boolean limite_gen=false;
	public void init(int cantidad_individuos_orign,int cantidad_soluciones,String[][] valores,int umbral,int generaciones,double porcentaje_cruza,double porcentaje_mutacion){		
		soluciones=new Solucion[cantidad_soluciones];
		this.individuos=cantidad_individuos_orign;
		this.generaciones=generaciones;
		rand=new Random();
		this.porcentaje_mutacion=porcentaje_mutacion;
		this.umbral=umbral;
		for(int i=0;i<cantidad_soluciones;i++){
			soluciones[i]=new Solucion(umbral,cantidad_individuos_orign);
			soluciones[i].generarGenes(valores);
		}
		primer_hijo_generado=new Solucion(umbral,cantidad_individuos_orign);
		segundo_hijo_generado=new Solucion(umbral,cantidad_individuos_orign);		
	}		
	
	public Solucion[] getSoluciones(){
		return this.soluciones;
	}
	
	public void setLimiteGeneraciones(int limite_gen){		
		this.limite_gen_cont=limite_gen;
		this.limite_gen=true;
	}
	
	public void setSP(double sp){
		this.sp=sp;
	}
	
	private double getRandom(int limit){
		return (0 + (double)(Math.random() * ((limit - 0) + 1)));
	}
	
	/*private void throasMutacion(){
		int random=(int)getRandom(this.soluciones[padre].getGenes().length);
		GenotipoKnack ob;
		while(true){
			if((random+2)>=soluciones[padre].getGenes().length){
				random=(int)getRandom(this.soluciones[padre].getGenes().length);
				continue;
			}
			ob=this.hijo_generado.getGenes()[random];
			this.hijo_generado.getGenes()[random]=this.hijo_generado.getGenes()[random+2];
			this.hijo_generado.getGenes()[random+2]=this.hijo_generado.getGenes()[random+1];
			this.hijo_generado.getGenes()[random+1]=ob;
			break;
		}
	}*/
	
	private void comprobacionSoluciones(){
		primer_hijo_generado.generarPesos();
		segundo_hijo_generado.generarPesos();
		if(primer_hijo_generado.esFactible() && segundo_hijo_generado.esFactible()){
			primer_hijo_generado.generarValorFitness();
			segundo_hijo_generado.generarValorFitness();
			if(primer_hijo_generado.getValorFitness()<segundo_hijo_generado.getValorFitness()){
				verificarFactibilidadMutados(primer_hijo_generado,soluciones.length-2);
				verificarFactibilidadMutados(segundo_hijo_generado,soluciones.length-1);
			}else{
				verificarFactibilidadMutados(segundo_hijo_generado,soluciones.length-1);
				verificarFactibilidadMutados(primer_hijo_generado,soluciones.length-2);				
			}
			this.cont_gen_sin_mejorar=(sin_soluciones==2) ? cont_gen_sin_mejorar+1 : cont_gen_sin_mejorar;
		}else if(primer_hijo_generado.esFactible()){
			primer_hijo_generado.generarValorFitness();
			verificarFactibilidadMutados(primer_hijo_generado,soluciones.length-1);		
			this.cont_gen_sin_mejorar=(sin_soluciones==1) ? cont_gen_sin_mejorar+1 : cont_gen_sin_mejorar;
		}else if(segundo_hijo_generado.esFactible()){
			segundo_hijo_generado.generarValorFitness();
			verificarFactibilidadMutados(segundo_hijo_generado,soluciones.length-1);
			this.cont_gen_sin_mejorar=(sin_soluciones==1) ? cont_gen_sin_mejorar+1 : cont_gen_sin_mejorar;
		}
		this.sin_soluciones=0;
	}
	
	private void verificarFactibilidadMutados(Solucion hijo,int pos){				
		if(soluciones[pos].getValorFitness()<hijo.getValorFitness()){
			soluciones[pos]=hijo;
			sin_soluciones++;
		}
	}
	
	private void mutacionBinaria(Solucion hijo){
		for(int i=0;i<hijo.getGenes().length;i++)
			if(rand.nextFloat()<=this.porcentaje_mutacion)
				hijo.getGenes()[i].cambiarEstado();			
	}
	
	private boolean verificarLimiteGen(){
		if(this.limite_gen)
			return this.limite_gen_cont>=this.cont_gen_sin_mejorar;		
		return false;
	}
	
	public void initAlgoritmo(){
		generarPoblacion();
		int i=0;
		for(i=0;i<generaciones;i++,padre=-1,madre=-1){
			if(i==0){
				Archivo.escribirContenido(this.soluciones, "primera_generacion_");
			}
			seleccionSoluciones();			
			cruzamientoUniforme();
			mutacionBinaria(primer_hijo_generado);
			mutacionBinaria(segundo_hijo_generado);
			comprobacionSoluciones();
			primer_hijo_generado=new Solucion(this.umbral,this.individuos);
			segundo_hijo_generado=new Solucion(this.umbral,this.individuos);
			if(!verificarLimiteGen()) break;					
		}
		System.out.println(verificarLimiteGen()+" "+i+" "+this.cont_gen_sin_mejorar+" "+this.limite_gen_cont);		
	}	
	
	private void cruzamientoUniforme(){
	    int[] posicion_cruces=new int[soluciones[0].getGenes().length];
	    int conteo_posiciones=0,pos;
	    while(conteo_posiciones<posicion_cruces.length){
			pos=(int) getRandom(soluciones[0].getGenes().length-1);
			if(posicion_cruces[pos]!=0)
				continue;
			if(getRandom(1)==1){
				primer_hijo_generado.getGenes()[pos]=new GenotipoKnack(soluciones[padre].getGenes()[pos]);				
				posicion_cruces[pos]=1;
			}else{
				primer_hijo_generado.getGenes()[pos]=new GenotipoKnack(soluciones[madre].getGenes()[pos]);
				posicion_cruces[pos]=2;
			}
			conteo_posiciones++;
	    }
	    for(int i=0;i<posicion_cruces.length;i++)
	    	if(posicion_cruces[i]==1)
	    		segundo_hijo_generado.getGenes()[i]=new GenotipoKnack(soluciones[madre].getGenes()[i]);
	    	else
	    		segundo_hijo_generado.getGenes()[i]=new GenotipoKnack(soluciones[padre].getGenes()[i]);	
	    posicion_cruces=new int[soluciones[0].getGenes().length];
	}
	
	private void seleccionSoluciones(){
		Arrays.sort(soluciones);
		seleccionRouletaBasadaEnRank();
	}
	
	public void generarPoblacion(){
		for(int i=0;i<soluciones.length;i++)
			soluciones[i].generarSolucion();								
	}
	
	public void mostrarSoluciones(){
		for(int i=0;i<soluciones.length;i++)
			System.out.println(soluciones[i]);
	}
	
	private void seleccionRouletaBasadaEnRank(){
		double limit=0,ranked;
		for(int i=0,x=1;i<soluciones.length;i++,x++){
			soluciones[i].setBegin(limit);
			ranked=getScaledRank(x);
			soluciones[i].setEnd(limit+ranked);			
			limit+=ranked;
		}		
		while(padre==madre || (padre==-1 || madre==-1)){
			padre=getSeleccion((0 + (double)(Math.random() * ((limit - 0) + 1))));
			madre=getSeleccion((0 + (double)(Math.random() * ((limit - 0) + 1))));			
		}
	}
	
	private int getSeleccion(double limit){
		return Busqueda.Binary((Elemento[])soluciones, limit);		
	}
	
	public double getScaledRank(int pos){
		return (2-sp+((2*(sp-1)*((double)(pos-1)/(soluciones.length-1)))));
	}
			
}
