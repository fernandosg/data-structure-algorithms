package knapsackproblem;

import java.util.Arrays;
import java.util.Random;

public class AlgoritmoGenetico {
	private Solucion[] soluciones;	
	private Solucion hijo_generado;
	private int generaciones=0,individuos,padre=-1,madre=-1,umbral,cantidad_individuos;	
	private double sp,porcentaje_cruza;	
	public void init(int cantidad_individuos_orign,int cantidad_soluciones,String[][] valores,int umbral,int generaciones,double porcentaje_cruza,double porcentaje_mutacion){		
		soluciones=new Solucion[cantidad_soluciones];
		this.individuos=cantidad_individuos_orign;
		this.generaciones=generaciones;
		this.porcentaje_cruza=porcentaje_cruza;
		this.umbral=umbral;
		this.cantidad_individuos=cantidad_soluciones;
		for(int i=0;i<cantidad_soluciones;i++){
			soluciones[i]=new Solucion(umbral,cantidad_individuos_orign);
			soluciones[i].generarGenes(valores);
		}
		hijo_generado=new Solucion(umbral,cantidad_individuos_orign);
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
			}
		}
	}
	
	public void initAlgoritmo(){
		generarPoblacion();
		for(int i=0;i<generaciones;i++,padre=-1,madre=-1){
			if(i==0){
				System.out.println("*********************+GENERACION "+i+"*********************************");
				mostrarSoluciones();
			}
			seleccionSoluciones();			
			cruzamientoUniforme();
			throasMutacion();
			verificarFactibilidadMutados();
			hijo_generado=new Solucion(this.umbral,this.individuos);
			
		}
		System.out.println("Ultima generacion***************************************************************");
		mostrarSoluciones();
	}		
		
	private void intercambio(GenotipoKnack ganador,int pos){
		hijo_generado.getGenes()[pos]=ganador;		
	}
	
	private void cruzamientoUniforme(){
		Random rand=new Random();
		float random_cruza=rand.nextFloat();
		for(int i=0;i<soluciones[padre].getGenes().length;i++,random_cruza=rand.nextFloat())
			if(random_cruza>=this.porcentaje_cruza){
				intercambio(soluciones[padre].getGenes()[i],i);
			}else{
				intercambio(soluciones[madre].getGenes()[i],i);
			}
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
		double limit=0;
		for(int i=0,x=1;i<soluciones.length;i++,x++){
			soluciones[i].setRanked(getScaledRank(x));
			limit+=soluciones[i].getRanked();
		}		
		while(padre==madre){
			padre=getSeleccion((0 + (double)(Math.random() * ((limit - 0) + 1))));
			madre=getSeleccion((0 + (double)(Math.random() * ((limit - 0) + 1))));
			//System.out.println("el while no funciona "+padre);
		}
		System.out.println("el limite = "+limit+" PADRE="+padre+" MADRE="+madre);
	}
	
	private int getSeleccion(double limit){
		double sum=0;
		for(int i=0;i<soluciones.length;i++){
			sum+=soluciones[i].getRanked();
			if(sum>limit){				
				return i;
			}
		}
		return 0;
	}
	
	public double getScaledRank(int pos){
		return (2-sp+((2*(sp-1)*((double)(pos-1)/(soluciones.length-1)))));
	}
			
}
