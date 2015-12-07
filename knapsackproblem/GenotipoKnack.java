package knapsackproblem;

class GenotipoKnack implements Genotipo{
	private int peso,valor;
	private byte estado;
	public GenotipoKnack(int peso,int valor){
		this.peso=peso;
		this.valor=valor;
	}
	@Override
	public byte getEstado() {
		return estado;
	}
	@Override
	public void setEstado(byte estado) {
		this.estado=estado;
	}
	
	public int getValor(){
		return valor;
	}
	
	public int getPeso(){
		return peso;
	}
	
	@Override
	public String toString(){			
		return "(est="+estado+" pes="+peso+" val="+valor+")";
	}
}