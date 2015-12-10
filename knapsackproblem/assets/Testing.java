package knapsackproblem.assets;

public class Testing {
	private class Persona{
		private int id;
		public Persona(){
			id=1;
		}
		
		public int getId(){
			return id;
		}
		
		public void cambiarId(){
			id=2;
		}
	}
	
	public void init(){
		Persona ob=new Persona();
		System.out.println(ob.getId()+" - ");
		mutar(ob);
		System.out.println(ob.getId()+" - ");
	}
	
	private void mutar(Persona persona){
		persona.cambiarId();
	}
	
	public static void main(String args[]){
		new Testing().init();
	}

}
