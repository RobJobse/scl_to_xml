package scl_to_xml;

import scl_to_xml.Action.AT;

public class CondActionTest {

	
	public CondActionTest() {
		
		IfExpression blok1 = new IfExpression();
		
		ActionList ca1 = new ActionList();
		
		ca1.onEntry = true;
		ca1.add(new Action("oAflaat := 1;",AT.EFFECT) ); // = "oAflaat := 1;" ;				
		blok1.cas.add(ca1);		

		blok1.print();
	
		blok1 = new IfExpression();
		
		ca1 = new  ActionList();

		
		ca1.onEntry = true;
		ca1.add( new Action("#intervalEindDruk := #druk;",AT.EFFECT));

		IfExpression innerBlok = new IfExpression();
		
		
		ActionList ca2 = new ActionList();	
		ca2.condition = "#intervalBeginDruk - #intervalEindDruk > #param.maxIntervalDrukval";
		ca2.add(new Action("#newState := #ERROR_AFLATEN;",AT.EFFECT));
		ca2.add(new Action("#maxStapTijd := #param.maxStapTijd;",AT.EFFECT));
		ca2.add(new Action("#errNo := 4;",AT.EFFECT));
		innerBlok.cas.add(ca2);
		
		ca1.add(new Action(innerBlok));
		blok1.cas.add(ca1);	
		blok1.print();
		

		
	}
}
