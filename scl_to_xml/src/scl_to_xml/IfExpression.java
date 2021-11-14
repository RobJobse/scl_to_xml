package scl_to_xml;

import java.util.List;
import java.util.ArrayList;

public class IfExpression {
	List<ActionList> cas ;

	
	public IfExpression() {
		cas = new ArrayList<ActionList>() ;
	}
	
	public void print() {
		for (ActionList ca : cas) {
			ca.print();
		}
		
		
	}
}
