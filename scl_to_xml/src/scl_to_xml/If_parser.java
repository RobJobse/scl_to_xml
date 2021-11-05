package scl_to_xml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class If_parser {
	
	enum PS  { 
		E_IF,  		// s00 expect if
	    E_VW_IF,   	// s01 expect voorwaarde or (new) if
	    E_THEN,     // s02 expect then
	    E_ACT,   	// s03 expect action
	    E_END	 	// s04 expect end_if
	};  		

	
	public PS parseToken(String token, PS state) {
   	 switch( state ) {
	 
   	 case E_IF :  
   		 //System.out.println(token);
   		 if (token.equals("if")) state = PS.E_VW_IF;
   	 	break;
   	 case E_VW_IF :  

   		 if (token.equals("then")) 
   			 state = PS.E_THEN;
   		 else
	    		 System.out.print(token+":");		    			 
   	 	break;		    	 	
   	 case E_THEN :  
   		 //
   		 if (token.equals("end_if")) 
   			 state = PS.E_IF;
   		 else {
   			 System.out.print(token);
   		     System.out.println();
   		 }
   	 	break;	
   	 	
   	 	
   	 }
   	 return state;
		
	}	
	
	public If_parser(String filename) throws FileNotFoundException,IOException{
		// TODO Auto-generated constructor stub
		BufferedReader in
		   = new BufferedReader(new FileReader(filename+".scl"));
											//  inertiserenEnLektest.scl
		
	    FileWriter fileWriter = new FileWriter(filename+".xml");
	    //outfw = new PrintWriter(fileWriter);
   		
	    List<IfAction> alist = new ArrayList();  
	    
	    

	  //  alist.add(new IfAction("Mango","mangoActie") );  

	    //Iterating the List element using for-each loop  
	    for(IfAction item:alist){  
	       System.out.println(item.voorwaarde+":"+item.actie);  
	     
	    }  
	    
	    PS state = PS.E_IF;
	    
		String regel;
		String token;	
		
		StringTokenizer stok;
		

		

		while(in.ready()) {

			regel = in.readLine();
			
			stok  = new StringTokenizer(regel);
		     while (stok.hasMoreTokens()) {
		    	
		    	 token = stok.nextToken();
		    	 
		    	 state = parseToken(token,state);
		    	 
		    	 
/*		    	 switch( state ) {
		    	 
		    	 case E_IF :  
		    		 //System.out.println(token);
		    		 if (token.equals("if")) state = PS.E_VW_IF;
		    	 	break;
		    	 case E_VW_IF :  

		    		 if (token.equals("then")) 
		    			 state = PS.E_THEN;
		    		 else
			    		 System.out.print(token+":");		    			 
		    	 	break;		    	 	
		    	 case E_THEN :  
		    		 //
		    		 if (token.equals("end_if")) 
		    			 state = PS.E_IF;
		    		 else {
		    			 System.out.print(token);
		    		     System.out.println();
		    		 }
		    	 	break;	
		    	 	
		    	 	
		    	 }
		*/    	 
		    	 
		     }
		}
		
	}
	
	
}
