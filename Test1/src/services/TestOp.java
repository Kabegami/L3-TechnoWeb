package services;

public class TestOp {
	public static void main(String[] args){
		double a = 1;
		double b = 2;
		String op = "addition";
		
		Operation o = new Operation();
		double res = o.calcul(a, b, op);
		System.out.println("res = " + res);
	}
}
