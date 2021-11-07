package scl_to_xml;
//
//
// onder nieuwe versie van eclipse en git installed
// en een 
// beetje comment
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import scl_to_xml.Test.PS;

public class Basic {
	
	PrintWriter outfw;


		       
	public Basic(String filename) throws FileNotFoundException,IOException{
		// TODO Auto-generated constructor stub
		BufferedReader in
		   = new BufferedReader(new FileReader(filename+".scl"));
											//  inertiserenEnLektest.scl
		
	    FileWriter fileWriter = new FileWriter(filename+".xml");
	    //outfw = new PrintWriter(fileWriter);
	

	    
		String regel;
		String token;

		String begin_parse_token = "CASE";
		boolean parsing_active = false;
		
		StringTokenizer stok;

		System.out.println("<model>");
		while(in.ready()) {

			regel = in.readLine();
			
			stok  = new StringTokenizer(regel);
		     while (stok.hasMoreTokens()) {

		    	 token = stok.nextToken();
		    	 
		    	 if(token.startsWith("//") ) { // includes equals("//")
		    		 break; // break from while loop ignores rest of the tokens ands reads new line
		    	 }	else if(token.contains("//")) { // contains but not starts with because 
		    		 // this case comes after starts with
		    		 token = token.substring(0,token.indexOf("//")); 
		    		 stok = new StringTokenizer(""); // maar de rest vd regel ignore
		    	 } else if (token.toUpperCase().contains("CASE")) {
		    		 parsing_active = true;
		    	 }
		    	 if (parsing_active) {    	 
		    	 switch (token.toUpperCase()) {
		    	 
		    	 case "IF" :
		    	 case "THEN" :
		    	 case "ELSE" :
		    	 case "END_IF;" :
		    	 case "END_IF" :	 
		    	 case "CASE" :
		    	 case "OF" :
		    	 case "END_CASE;" :
		    	 case "END_CASE" :	
		    	 case ":=" :	 
		    		 
		    		 System.out.println("<Lang>"+token+"</Lang>");
		    	 	break;
		    	 case ";" :
		    		 System.out.println("<End>"+token+"</End>");
		    		 break;
		    	 case "#ONENTRY" :
		    		 System.out.println("<onEntry/>");
		    		 break;
		    	 default:
		    		 if(token.endsWith(":")) {
		    			System.out.println("<Label>"+token+"</Label>");
		    		 }	 else if(token.contains(";") && token.length()>1) {
		    			System.out.println("<Var>"+token.substring(0, token.length()-1) +"</Var>"); 
		    		 } //else {
		    			// System.out.println("<?>"+token+"</?>"); 
		    		// }
					break;
		    	 }	

		     }	// switch
		     }
		} // while
		System.out.println("</model>");
		in.close();
		//outfw.flush();
	    //outfw.close();
	}
	
}
