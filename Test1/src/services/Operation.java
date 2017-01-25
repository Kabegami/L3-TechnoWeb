package services;

public class Operation {
	
	public Operation(){
		
	}
	
	// methode qui appelle une des trois methodes dessous
	public double calcul(double a, double b, String operation){
		double res = 0;
		if (operation.compareTo("addition") == 0)
			return addition(a, b);
		else if (operation.compareTo("multiplication") == 0)
			return multiplication(a ,b);
		else if (operation.compareTo("division") == 0)
			return division(a, b);
		else
			return 0;
	}
	
	private double addition(double a, double b){
		return a + b;
	}
	
	private double multiplication(double a, double b){
		return a * b;
	}
	
	private double division(double a, double b){
		return a / b;
	}
	
	
}
