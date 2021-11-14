package scl_to_xml;

import java.util.ArrayList;
import java.util.List;

public class ActionList extends ArrayList<Action> {
	public boolean onEntry = false; // the condition is onEntry
	public String condition; // can also be list of strings eg list of tokens
	public String newState = ""; // if the action is a newState assignment,
								 // empty string "" if not a newState assignment
	

	
	
	
	public void print() {
		if(onEntry) {
			System.out.println("<onEntry>");
			for(Action ac : this) ac.print();
			System.out.println("</onEntry>");
			
		} else {
			System.out.println("<guard>");
			System.out.println(condition);
			System.out.println("</guard>");	
			System.out.println("<effect>");
			for (Action ac : this) {
				ac.print();
			}
			System.out.println("</effect>");			
			
		}
	}	
	
	
	
}
