package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ServletOp extends HttpServlet {

	public ServletOp(){
		
	}
	
	 /*
	 * This method will handle all GET request.
	 */
	 protected void doGet(HttpServletRequest request,
	 HttpServletResponse response) throws ServletException, IOException {
	 
	 	response.setContentType( " text / plain " );
		PrintWriter out = response.getWriter ();
		
		Map<String, String> pars = request.getParameterMap();
	 		 	
	 	if (pars.containsKey("a") && pars.containsKey("b") && pars.containsKey("op")){
	 		String[] opType = {"addition", "multiplication", "division"};
	 		
	 		String valueA = request.getParameter("a");
	 		String valueB = request.getParameter("b");
	 		String op = request.getParameter("op");
	 		
	 		double res = 0;
	 		String opIcon = "";
	 		
	 		
	 		double a = Double.parseDouble(valueA);
	 		double b = Double.parseDouble(valueB);
	 		Operation operation = new Operation();
	 		res = operation.calcul(a, b, op);
	 		
	 		out.println(res);
	 	}
	 	else {
			out.println("Erreur arguments");
	 	}
	 }
}
