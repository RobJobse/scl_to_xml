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

public class ParsingStateMachine {

	PrintWriter outfw;  // for later
	String state = "INIT" ;
	int ifLevel = 0;
	boolean onEntryFlag;
	boolean ifWithinOnEntryFlag;
	/**
	 *     Constructor
	 * @param filename  without .scl extention
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ParsingStateMachine(String filename) throws FileNotFoundException,IOException{
		BufferedReader in
		= new BufferedReader(new FileReader("scl_to_xml\\"+filename+".scl"));
		//  inertiserenEnLektest.scl

		//FileWriter fileWriter = new FileWriter(filename+".xml");
		//outfw = new PrintWriter(fileWriter);


		String regel;
		String tok;
		


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
	
					switch ( state) {
					case "INIT"  : 	
						if (utok.contains("CASE")){
							System.out.println("<model>");
							supdateState("CASE");							
						}
						break;
					case "CASE"  : 	
						if (utok.equals("OF")){
							updateState("OF");
						}
						break;
					case "OF"  : 	
						if (utok.endsWith(":")){
							noteSubvertex(utok);
						}
						break;
					case "SL"  : 	
						if (utok.equals("IF")){
							updateState("IF");
						}
						break;
					case "IF"  : 	
						if (utok.equals("THEN")){
							if(onEntryFlag) {
								noteEntryToken(tok);
							} else
							    updateState("THEN");
						} else if (utok.contains("ONENTRY")){
							noteOnEntryStart();
						}
						break;
					case "ONENTRY"  : 	
						if (utok.equals("THEN")){
							updateState("THEN");							
						}
						break;						
					case "THEN"  : 	
						if (utok.equals("ELSIF")){
							updateState("ELSIF");
						} else if (utok.equals("ELSE")){
							updateState("ELSE");
						} else if (utok.contains("END_IF")){
							if(ifWithinOnEntryFlag)
								noteEntryToken(tok);
							else
								updateState("END_IF");
							if(onEntryFlag) noteOnEntryEnd();
						} else if (utok.equals("IF")){
							if(onEntryFlag) {
								noteEntryToken(tok);
								ifWithinOnEntryFlag = true;
							}else
							   updateState("IF");
						} else if (utok.equals("#NEWSTATE")){
							updateState("NEWSTATE");
						} else if(onEntryFlag) {
						    noteEntryToken(tok);	
						}
						break;	
					case "NEWSTATE"	:
						if (utok.equals("ELSIF")){
							updateState("ELSIF");
						} else if (utok.equals("ELSE")){
							updateState("ELSE");
						} else if (utok.contains("END_IF")){
							updateState("END_IF");
						} else if (utok.equals("IF")){
							updateState("IF");
						}
						break;
					case "END_IF"  :
						if (utok.equals("IF")){
							updateState("IF");
						} else if (utok.contains("END_IF")){
							updateState("END_IF");
						} else if (utok.endsWith(":")){
							noteSubvertex(utok);
						} else if (utok.equals("ELSIF")){
							updateState("ELSIF");
						} else if (utok.contains("END_CASE")){
							supdateState("END_CASE");
							System.out.println("</model>");
						}
							

						break;						
					case "ELSIF"  :
						if (utok.equals("THEN")){
							updateState("THEN");
						} else if (utok.equals("#NEWSTATE")){
							updateState("NEWSTATE");
						}
						break;	
					case "ELSE"  :
						if (utok.contains("END_IF")){
							updateState("END_IF");
						} else if (utok.equals("#NEWSTATE")){
							updateState("NEWSTATE");
						}
						break;							
					default :

						break;		   			
					}
				



				}	// if parsing active
				if(tok.toUpperCase().contains("END_CASE")) parsing_active = false;
			}  // more tokens in that line
		} // while more lines to read
		in.close();
		//outfw.flush();
		//outfw.close();
	} // constructor

	void updateState(String newState) {
		state = newState;
		System.out.println("-"+state+"-");
	}
	
	/**
	 * Silent update
	 * @param newState
	 */
	void supdateState(String newState) {
		state = newState;
	}
	void noteSubvertex(String subvert) {
		supdateState("SL");
		System.out.println("    <subvertex>");
		System.out.println("        <name>"+subvert+"</name>");
	}
	
	void noteOnEntryStart() {
		supdateState("ONENTRY");
		onEntryFlag = true;
		System.out.println("        <onEntry>");		
	}
	void noteOnEntryEnd() {
		supdateState("THEN");
		onEntryFlag = false;
		System.out.println("        </onEntry>");		
	}	
	void noteEntryToken(String tok) {
		if (tok.contains(";")) 
			System.out.println(tok); 
		else 
			System.out.print(tok+" ");
	}
} // class
