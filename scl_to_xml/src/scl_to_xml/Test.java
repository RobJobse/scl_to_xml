package scl_to_xml;     //  design version 1.1a updated 5-10-2021/14-10
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

public class Test {
	
	PrintWriter outfw;

	enum PS  { E_CASE,  	// s00 expect case
		       E_STATEVAR, 	// s01 expect #state variable
		       E_STATELBL,  // s02 expect state: label
		       E_IF,   		// s03 expect if OR A STATELABEL or end_case
		       E_EN_OR_TR, 	// s04 expect an onEntry or an transition
		       E_THEN_EN, 	// s05 expect the then following an onEntry 
		       E_END_EN, 	// s07 expect the END_IF following an onEntry 		       
		       E_THEN_TR, 	// s06 expect the then following an transition 
		       E_END_TR, 	// s08 expect the END_IF following an transition 
		       E_TARGET,	// s10 expect targe state
		       E_COM };  	// expect comment end
		       
    
	private void writeState(Boolean next, String state) {
		if(next) outfw.println("</subvertex>");
		outfw.print("<subvertex name=\""+state.substring(1,state.length()-1)+"\">");
	}
	
	private void writeTarget(String state) {
		outfw.print("<target>"+state.substring(1,state.length()-1)+"</target>");
		outfw.print("<effect>");
	}
		       
	public Test(String filename) throws FileNotFoundException,IOException{
		// TODO Auto-generated constructor stub
		BufferedReader in
		   = new BufferedReader(new FileReader(filename+".scl"));
											//  inertiserenEnLektest.scl
		
	    FileWriter fileWriter = new FileWriter(filename+".xml");
	    outfw = new PrintWriter(fileWriter);
	    		



	    
		String regel;
		String token;

		PS parseState = PS.E_CASE;
		PS prevParseState = PS.E_CASE;
		PS savedState = PS.E_CASE;
		
		StringTokenizer stok;
	//	regel = in.readLine();
		outfw.println("<model>");
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
		    	 }
		    	 
		    	 if (token.equals("(*")) {
		    		 savedState = parseState;
		    		 parseState = PS.E_COM;
		    	 } else if(token.equals("*)")) {
		    		 parseState = savedState ;
		    		 //parseState = PS.B_COM;
		    	 }
		    	 
		   	 
		    	 if(token.contains("(*") && token.contains("*)") ) { // tussendeel er uit en resterende token verwerken 

		    		 token = token.substring(0,token.indexOf("(*"))+token.substring(token.indexOf("*)")+2 );
		    	 }			    	 
		    	 else if(token.startsWith("(*") ) { 
		    		 savedState = parseState;
		    		 parseState = PS.E_COM;
		    	 } else if (token.contains("(*") ) {
		    		 token = token.substring(0,token.indexOf("(*"));
		    		 savedState = parseState;
		    		 parseState = PS.E_COM;
		    	 }  	 
	    	 
		    	 switch (parseState) {
		    	 
		    	 	case E_CASE: // s00 wait for case
				    	 if(token.toUpperCase().equals("CASE") ) { 
				    		 parseState = PS.E_STATEVAR;
				    	 }
				    	 break;	 
				    	 		    	 	
		    	 	case E_STATEVAR: // s01 wait for state variable if not back
				    	 if(token.toUpperCase().equals("#STATE") ) { 
				    		 parseState = PS.E_STATELBL;
				    	 }	else { 
				    		 parseState = PS.E_CASE;
				    	 }
				    	 break;
				    	 
		    	 	case E_STATELBL: // s02 skip OF wait for state label:
				    	 if(token.endsWith(":") && token.length()>2) { 
				    		 parseState = PS.E_IF;
				    		 writeState(false,token);
				    	 }	
				    	 break;
				    	 
    	    	 	case E_IF: // s03 wait for IF
				    	 if(token.toUpperCase().equals("IF") ) { 
				    		 parseState = PS.E_EN_OR_TR;
				    	 }  else if(token.endsWith(":") && token.length()>2) { 
				    		 
				    		 writeState(true,token);
				    	 } else if(token.toUpperCase().contains("END_CASE")) {
				    		 outfw.print("</subvertex>");
				    		 parseState = PS.E_CASE;
				    	 }
				    	 break;
				    	 
		    	 	case E_EN_OR_TR:	 // s04 catch onEntry or trigger or statelabel 
				    	 if(token.toUpperCase().equals("#ONENTRY")) {
				    		 outfw.print("<entry>");
				    		 parseState = PS.E_THEN_EN;  // s05
				    	 } else {
				    		 outfw.println("<transition>");
				    		 outfw.println("<guard>");			    		 
				    		 outfw.print(token+" ");
				    		 parseState = PS.E_THEN_TR;  // s06
				    	 }
				    	 break;

		    	 	case E_THEN_EN: // 05 wait for then
				    	 if(token.toUpperCase().contains("THEN") ) { 
				    		 parseState = PS.E_END_EN;  // s07
				    		// outfw.print("<Action>");
				    	 }	
				    	 break;
				    	 
		    	 	case E_END_EN: // s07
				    	 if(token.toUpperCase().contains("END_IF") ) { 
				    		 parseState = PS.E_IF; // s03
				    		 outfw.println("</entry>");
				    	 } else if (token.toUpperCase().contains("ELSIF")) {
			    	 			parseState = PS.E_EN_OR_TR; // s04
			    	 			outfw.print("</entry>");
			    	 	 } else {
				    		 outfw.print(token+" ");
				    	 }
				    	 break;	

		    	 	case E_THEN_TR:  //s06
		    	 		if (token.toUpperCase().contains("THEN")) {
		    	 			parseState = PS.E_END_TR; // s08
		    	 			outfw.println();
		    	 			outfw.print("</guard>");
		    	 		}  else {
		    	 			if ( token.equals("<")) token = "&lt;";
		    	 			outfw.print(token+" ");
		    	 		}
		    	 		break;
		    	 		
		    	 	case E_END_TR: // s08
				    	 if(token.toUpperCase().contains("END_IF") ) { 
				    		 parseState = PS.E_IF; // s03
				    		 outfw.print("</effect></transition>");
				    	 }	else if(token.toUpperCase().contains("#NEWSTATE")){
				    		 parseState = PS.E_TARGET; // s010
				    	 }  else if (token.toUpperCase().contains("ELSIF")) {
			    	 			parseState = PS.E_IF; // s03
			    	 			outfw.print("</effect></transition>");
			    	 	 }   else if (token.toUpperCase().contains("ELSE")) {
			    	 			parseState = PS.E_IF; // s03
			    	 			outfw.print("</effect></transition>");
			    	 	 } else {
				    		 outfw.print(token+" ");
				    	 }
				    	 break;	
		    	 	case E_TARGET:
				    	 if(!token.toUpperCase().contains(":=") ) { 
				    		 parseState = PS.E_END_TR; // s08
				    		 writeTarget(token);
				    	 }  
				    	 break;	
		    	 	case E_COM:
		    	 		 if (token.startsWith("*)") ) {
				    		 token = token.substring(token.indexOf("*)")+2); // substr is vanaf index
				    		 parseState = savedState;
				    	 } else if (token.contains("*)") ) {
				    		 token = token.substring(token.indexOf("*)")+2); // substr is vanaf index
				    		 parseState = savedState;
				    	 } 	
		    	 		 break;

		    	 	default:		    	 		
					break;
		    	 }	
		    	 if(parseState!=prevParseState) {
		    		// outfw.println(parseState);
		    	 }
		    	 
		    	 prevParseState = parseState;
		     }	// switch
		     outfw.println();
		} // while
		outfw.println("</model>");
		in.close();
		outfw.flush();
	    outfw.close();
	}
}
