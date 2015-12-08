package utils;

public class Busqueda {

	public static int Binary(Elemento[] elements,int search){
		int begin=0,end=elements.length-1,middle=(elements.length-1)/2;	
		while(begin<=end){
			if(elements[middle].belongTo(search) && elements[middle].getValue()==search)
				return middle;
			else if(elements[middle].lessThan(search)){
				begin++;
			}else{	
				end--;
			}
			middle=((end-begin)/2)+begin;									
		}		
		return -1;
	}
	//Clase de prueba para usar en la busqueda binaria, implementando las interfaces Element y Range
	private static class TestingElement implements Elemento, Rango{
		private int value;
		public double begin_value,end_value;
		
		public TestingElement(int value,int begin_value,int end_value){
			this.value=value;
			this.begin_value=begin_value;
			this.end_value=end_value;
		}
		
		public int getValue(){
			return value;
		}
		
		private double getBeginValue(){
			return this.begin_value;
		}
		
		private double getEndValue(){
			return this.end_value;
		}
				
		@Override
		public boolean lessThan(double element) {
			return getBeginValue()<element;		
			
		}		
		public void setBegin(double begin){
			begin_value=begin;
		}
		
		public void setEnd(double end){
			this.end_value=end;
		}
		
		@Override
		public boolean belongTo(double value) {
			return this.getBeginValue()<=value && this.getEndValue()>=value;
		}

		@Override
		public double getBegin() {
			return this.begin_value;
		}

		@Override
		public double getEnd() {
			return this.end_value;
		}
		
	}

}
