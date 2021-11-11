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

	PrintWriter outfw;  // for later
	String state = "START" ;
	int ifLevel = 0;

	/**
	 *     Constructor
	 * @param filename  without .scl extention
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Basic(String filename) throws FileNotFoundException,IOException{
		BufferedReader in
		= new BufferedReader(new FileReader(filename+".scl"));
		//  inertiserenEnLektest.scl

		//FileWriter fileWriter = new FileWriter(filename+".xml");
		//outfw = new PrintWriter(fileWriter);

		// info
		int stateCount = 0;

		String regel;
		String tok;

		String begin_parse_token = "CASE";
		boolean parsing_active = false;

		StringTokenizer stok;

		
		while(in.ready()) {

			regel = in.readLine();

			stok  = new StringTokenizer(regel);
			while (stok.hasMoreTokens()) {

				tok = stok.nextToken();

				if(tok.startsWith("//") ) { // includes equals("//")
					break; // break from while loop ignores rest of the line(tokens) ands reads next line
				}	else if(tok.contains("//")) { // contains but not starts with because 
					// this case comes after starts with
					tok = tok.substring(0,tok.indexOf("//")); 
					stok = new StringTokenizer(""); // maar de rest vd regel ignore
				} else if (tok.toUpperCase().contains("CASE")) {
					parsing_active = true;
				}
				if (parsing_active) {   
					
					String utok = tok.toUpperCase();


					if (utok.equals("IF")) {
													
						updateState(utok,"IF");
						ifLevel++;
					} else if (utok.equals("THEN")) {
						updateState(utok,"TH");
					} else if (utok.equals("ELSE")){
						updateState(utok,"EL");
					} else if (utok.contains("END_IF")){
						ifLevel--;
						updateState(utok,"EI");		
					} else if (utok.equals("CASE")){
						updateState(utok,"CA");
						System.out.println("<model>");
					} else if (utok.equals("OF")){
						updateState(utok,"OF");
					} else if (utok.contains("END_CASE")){
						updateState(utok,"EC");
						System.out.println("    </subvertex>");
						System.out.println("</model>");
				//	} else if (tok.equals(":=")){
				//		updateState(utok,"AS");	
					} else if (utok.equals(";")){
						System.out.println("<End>"+tok+"</End>");

					} else if (utok.equals("#ONENTRY")){
						updateState(utok,"OE");
						System.out.println("        <entry>");
						
					} else if (utok.equals("#ONEXIT")){
						System.out.println("<onExit/>");
					} else {
						if(tok.endsWith(":")) {
							
							if(stateCount>0) System.out.println("    </subvertex>");
							System.out.println("    <subvertex name=\""+tok.substring(1, tok.length()-1)+"\">");
							stateCount++;
						}	 else if(tok.contains(";") && tok.length()>1) {
							//System.out.println("<Var>"+tok.substring(0, tok.length()-1) +"</Var>"); 
						} //else {
						   if ((state=="TH") || (state=="TH2")) {
							   System.out.print(" "+tok); 
						 
							   if (tok.endsWith(";")) System.out.println();
						   }
						// }
					}


				}	// if parsing active
				if(tok.toUpperCase().contains("END_CASE")) parsing_active = false;
			}  // more tokens in that line
		} // while more lines to read
		in.close();
		//outfw.flush();
		//outfw.close();
	} // constructor

	void updateState(String token, String newState) {
		switch ( state) {
		case "START" : if (newState=="OE") state = newState;
					   break;
		case "OE" : if (newState=="TH") state = newState;
		   			break;					   
		case "TH" : if (ifLevel==0) {
						if (newState=="EI") state = newState;

						System.out.println("        </entry>");
					} else {
						state = "TH2";
						System.out.println("TH2");
					}					
		   			break;
		   			
		   			
		case "EI" : if (newState=="OE") state = newState;
			break;		   			
		}
		//System.out.println("<Lang>"+token+"</Lang state=\""+state+"\""+ifLevel+">");
	}

} // class
