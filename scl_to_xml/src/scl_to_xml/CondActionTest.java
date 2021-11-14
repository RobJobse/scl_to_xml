package scl_to_xml;

import scl_to_xml.Action.AT;

public class CondActionTest {

	
	public CondActionTest() {
		
		IfExpression blok1 = new IfExpression();	
		IfExpression innerBlok = new IfExpression();	
		
		ActionList al1 = new ActionList();
		ActionList al2 = new ActionList();
		ActionList al3 = new ActionList();
		ActionList al4 = new ActionList();		
		
		al1.onEntry = true;
		al1.add(new Action("#oToevoer := 0;",AT.ONENTRY) ); 	
		al1.add(new Action("#rodeLed.cmd := #LED_OFF;",AT.ONENTRY) ); 	
		blok1.cas.add(al1);		

		blok1.print();
		blok1.cas.clear();
		al1.clear();
		al1.onEntry = false;
		
		al1.condition = "#osCmd.stelNietBeschikbaar";
		al1.add(new Action("NIETBESCHIKBAAR",AT.TARGET) );
		al2.condition = "#osCmd.stelBeschikbaar";
		al2.add(new Action(innerBlok));
		blok1.cas.add(al1);
		blok1.cas.add(al2);
		al3.condition = "#druk < #param.maxKoppeldruk";
		al3.add(new Action("AANKOPPELEN_MAG",AT.TARGET));
		al4.condition = "else";
		al4.add(new Action("AFLAAT_AANKOPPELEN",AT.TARGET));
		innerBlok.cas.add(al3);
		innerBlok.cas.add(al4);		
		
		blok1.print();
//		ca1.add(new Action(innerBlok));
/*		
        IF #onEntry THEN
        #oToevoer := 0;
        #rodeLed.cmd := #LED_OFF;
    END_IF;
    IF #osCmd.stelNietBeschikbaar (*AND NOT #osCmd.stelBeschikbaar*) THEN
        #newState := #NIETBESCHIKBAAR;
    ELSIF #osCmd.stelBeschikbaar (*AND NOT #osCmd.stelNietBeschikbaar*) THEN
        IF #druk < #param.maxKoppeldruk THEN
            #newState := #AANKOPPELEN_MAG;
        ELSE
            #newState := #AFLAAT_AANKOPPELEN;
        END_IF;
    END_IF;
		*/
	}
}
