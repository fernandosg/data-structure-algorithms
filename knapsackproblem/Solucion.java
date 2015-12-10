package knapsackproblem;

import utils.Elemento;
import utils.Rango;


class Solucion implements Comparable<Solucion>,Elemento,Rango{
	private GenotipoKnack[]	 genes;
	private double fitness,ranked,begin,end;
	private int umbral,peso_total,cantidad_individuos;	
	
	public Solucion(int umbral,int cantidad_individuos){
		this.umbral=umbral;
		this.cantidad_individuos=cantidad_individuos;
		genes=new GenotipoKnack[cantidad_individuos];
	}
	
	public void init(){
		genes=new GenotipoKnack[cantidad_individuos];
	}
	
	public GenotipoKnack[] getGenes(){
		return genes;
	}
	
	public void setRanked(double rank){
		this.ranked=rank;
	}
	
	public double getRanked(){
		return this.ranked;
	}
	
	public void generarGenes(String[][] valores){		
		for(int i=0;i<genes.length;i++)
			genes[i]=new GenotipoKnack(Integer.parseInt(valores[i][0]),Integer.parseInt(valores[i][1]));			
	}		
	
	public void generarSolucion(){
		byte estado=(byte)(0 + (int)(Math.random() * ((1 - 0) + 1)));
		peso_total=0;
		for(int i=0;i<genes.length;i++,estado=(byte)(0 + (int)(Math.random() * ((1 - 0) + 1)))){
			if(estado==1)
				if(peso_total+genes[i].getPeso()<=umbral)
					peso_total+=genes[i].getPeso();		
				else{
					genes[i].setEstado((byte)0);
					continue;
				}
			genes[i].setEstado(estado);
		}		
		generarValorFitness();
	}

	public double getValorFitness(){
		return fitness;
	}
	
	public boolean esFactible(){
		return this.peso_total<=umbral;
	}
	
	public void generarPesos(){
		this.peso_total=0;
		for(int i=0;i<genes.length;i++){
			if(genes[i].getEstado()==1)
				this.peso_total+=genes[i].getPeso();
		}
	}
	
	public int getPeso(){
		return this.peso_total;
	}
		
	public void generarValorFitness(){
		for(int i=0;i<genes.length;i++)
			if(genes[i].getEstado()==1)
				fitness+=genes[i].getValor();
	}

	@Override
	public int compareTo(Solucion o) {
		return Integer.compare((int)o.getValorFitness(), (int)this.getValorFitness());
	}
	
	@Override
	public String toString(){
		String cadena="";
		for(int i=0;i<genes.length;i++)
			if(genes[i].getEstado()==1)
				cadena+=genes[i]+" ";
		return cadena+" (valor fitness="+fitness+" peso="+this.peso_total+") ";		
	}

	@Override
	public boolean lessThan(double element) {
		return (this.begin<element);
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean belongTo(double value) {
		return this.begin<=value && this.end>=value;
	}

	@Override
	public double getBegin() {
		return this.begin;
	}

	@Override
	public double getEnd() {
		return end;
	}
	
	public void setBegin(double begin){
		this.begin=begin;
	}
	
	public void setEnd(double end){
		this.end=end;
	}
			
}