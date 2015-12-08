package knapsackproblem;

import java.util.Arrays;
import java.util.Random;

import utils.Archivo;
import utils.Busqueda;
import utils.Elemento;

public class AlgoritmoGenetico {
	private Solucion[] soluciones;	
	private Solucion hijo_generado;
	private int generaciones=0,individuos,padre=-1,madre=-1,umbral,limite_gen_cont,cont_gen_sin_mejorar;	
	private double sp,porcentaje_cruza,porcentaje_mutacion;
	private float random_cruza;	
	private Random rand;
	private boolean limite_gen=false;
	public void init(int cantidad_individuos_orign,int cantidad_soluciones,String[][] valores,int umbral,int generaciones,double porcentaje_cruza,double porcentaje_mutacion){		
		soluciones=new Solucion[cantidad_soluciones];
		this.individuos=cantidad_individuos_orign;
		this.generaciones=generaciones;
		rand=new Random();
		this.porcentaje_cruza=porcentaje_cruza;
		this.porcentaje_mutacion=porcentaje_mutacion;
		this.umbral=umbral;
		for(int i=0;i<cantidad_soluciones;i++){
			soluciones[i]=new Solucion(umbral,cantidad_individuos_orign);
			soluciones[i].generarGenes(valores);
		}
		hijo_generado=new Solucion(umbral,cantidad_individuos_orign);		
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
	
	private void throasMutacion(){
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
	}
	
	private void verificarFactibilidadMutados(){
		if(this.hijo_generado.esFactible()){
			hijo_generado.generarValorFitness();
			if(soluciones[this.soluciones.length-1].getValorFitness()<hijo_generado.getValorFitness()){
				soluciones[this.soluciones.length-1]=hijo_generado;
			}else{
				this.cont_gen_sin_mejorar++;
			}
		}
	}
	
	private void mutacionBinaria(){
		for(int i=0;i<hijo_generado.getGenes().length;i++)
			if(rand.nextFloat()<=this.porcentaje_mutacion)
				hijo_generado.getGenes()[i].cambiarEstado();
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
				System.out.println("*********************+GENERACION "+i+"*********************************");
				Archivo.escribirContenido(this.soluciones, "primera_generacion_");
			}
			seleccionSoluciones();			
			cruzamientoUniforme();
			mutacionBinaria();
			verificarFactibilidadMutados();
			hijo_generado=new Solucion(this.umbral,this.individuos);
			if(!verificarLimiteGen()) break;					
		}
		System.out.println("se acabo en la generacion "+i+" "+generaciones+" "+this.limite_gen_cont+" "+this.cont_gen_sin_mejorar);
		System.out.println(verificarLimiteGen());
	}		
		
	private void intercambio(GenotipoKnack ganador,int pos){
		hijo_generado.getGenes()[pos]=ganador;		
	}
	
	private void cruzamientoUniforme(){
		random_cruza=rand.nextFloat();
		for(int i=0;i<soluciones[padre].getGenes().length;i++,random_cruza=rand.nextFloat())
			if(random_cruza>=this.porcentaje_cruza)
				intercambio(soluciones[padre].getGenes()[i],i);
			else
				intercambio(soluciones[madre].getGenes()[i],i);			
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
		while(padre==-1 || madre==-1){
			padre=getSeleccion((0 + (double)(Math.random() * ((limit - 0) + 1))));
			madre=getSeleccion((0 + (double)(Math.random() * ((limit - 0) + 1))));			
		}
		//System.out.println("el limite = "+limit+" PADRE="+padre+" MADRE="+madre);
	}
	
	private int getSeleccion(double limit){
		return Busqueda.Binary((Elemento[])soluciones, limit);		
	}
	
	public double getScaledRank(int pos){
		return (2-sp+((2*(sp-1)*((double)(pos-1)/(soluciones.length-1)))));
	}
			
}
