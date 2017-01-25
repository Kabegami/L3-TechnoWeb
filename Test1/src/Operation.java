import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import java.util.Map;

public class Operation extends HttpServlet {

	public Operation(){
	}
	
	 protected void doGet(HttpServletRequest request,
			 HttpServletResponse response) throws ServletException, IOException {
			 
			 	response.setContentType( " text / plain " );
			 	
			 	
			 	Map<String, String> pars = request.getParameterMap();
			 	
		 		PrintWriter out = response.getWriter ();
			 	
			 	if (pars.containsKey("a") && pars.containsKey("b") && pars.containsKey("op")){
			 		String valueA = request.getParameter("a");
			 		String valueB = request.getParameter("b");
			 		String op = request.getParameter("op");
			 		double res = 0;
			 		String opIcon = "";
			 	
			 	
				 	if (op.compareTo("addition") == 0){
				 		res = Integer.parseInt(valueA) + Integer.parseInt(valueB);
				 		opIcon = " + ";
				 	}
				 	else if (op.compareTo("multiplication") == 0){
				 		res = Integer.parseInt(valueA) * Integer.parseInt(valueB);
				 		opIcon = " x ";
				 	}
				 	else if (op.compareTo("division") == 0){
				 		res = Double.parseDouble(valueA) / Double.parseDouble(valueB);
				 		opIcon = " / ";
				 	}
				 	else {
				 		
				 	}
				 	
					out.println(valueA + opIcon + valueB + " = " + res);
			 	}
			 	else {
					out.println("Erreur arguments");
			 	}
			 }
}
