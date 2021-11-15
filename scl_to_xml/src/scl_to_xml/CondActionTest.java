package scl_to_xml;

import scl_to_xml.Action.AT;

public class CondActionTest {

	
	public CondActionTest() {
		
		IfExpression blok1 = new IfExpression();	
		IfExpression innerBlok = new IfExpression();
		IfExpression innerBlok2 = new IfExpression();
		
		ActionList al1 = new ActionList();
		ActionList al2 = new ActionList();
		ActionList al3 = new ActionList();
		ActionList al4 = new ActionList();		
		ActionList al5 = new ActionList();
		ActionList al6 = new ActionList();
		ActionList al7 = new ActionList();
		ActionList al8 = new ActionList();		
		ActionList al9 = new ActionList();
		ActionList al10 = new ActionList();	
		ActionList al11 = new ActionList();	
		
		al1.onEntry = true;
		al1.add(new Action("#oToevoer := 0;",AT.ONENTRY) ); 	
		al1.add(new Action("#rodeLed.cmd := #LED_OFF;",AT.ONENTRY) ); 	
		blok1.cas.add(al1);		

		//blok1.print();
		
		blok1.cas.clear();
		al1.clear();

		
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
		
		//blok1.print();

		blok1.cas.clear();	
		innerBlok.cas.clear();
		al1.clear();
		al2.clear();
		al3.clear();
		al4.clear();
	//       #NIETBESCHIKBAAR:	
		al1.onEntry = true;
		al1.add(new Action("#oAflaat := 0;",AT.ONENTRY) ); 	
		al1.add(new Action("#oToevoer := 0;",AT.ONENTRY) ); 			
		al1.add(new Action("#rodeLed.cmd := #LED_OFF;",AT.ONENTRY) ); 	
		al1.add(new Action("#groeneLed.cmd := #LED_OFF;",AT.ONENTRY) ); 		
		blok1.cas.add(al1);		

		blok1.print();
		
		blok1.cas.clear();
		al1.clear();	
		
		al1.condition = "#osCmd.stelBeschikbaar";
		al1.add(new Action(innerBlok));
		al2.condition = "#druk < #param.maxKoppeldruk - #param.drukHysterese";
		al2.add(new Action("AANKOPPELEN_MAG", AT.TARGET));
		al3.condition = "else";
		al3.add(new Action("AFLAAT_AANKOPPELEN", AT.TARGET));
		innerBlok.cas.add(al2);
		innerBlok.cas.add(al3);
		blok1.cas.add(al1);	
		al4.condition = "#osCmd.startInertiseren";
		al4.add(new Action("INERTISEREN_ERROR", AT.TARGET));	
		blok1.cas.add(al4);		
//     
		al5.condition = "#drukCentaalPaneel > #param.ip.maxDrukPT701";
		al5.add(new Action("#errNo := 1;", AT.EFFECT));		
		al6.condition = "#drukCentaalPaneel < #param.ip.minDrukPT701";
		al6.add(new Action("#errNo := 2;", AT.EFFECT));		
		al7.condition = "NOT #gb704B";
		al7.add(new Action("#errNo := 3;", AT.EFFECT));		
		al8.condition = "NOT #gbToevoerDicht";
		al8.add(new Action("#errNo := 4;", AT.EFFECT));		
		al9.condition = "NOT #gbBurenDicht";
		al9.add(new Action("#errNo := 5;", AT.EFFECT));		
		al10.condition = "else";
		al10.add(new Action("INERTISEREN", AT.TARGET));			
		innerBlok2.cas.add(al5);
		innerBlok2.cas.add(al6);
		innerBlok2.cas.add(al7);
		innerBlok2.cas.add(al8);
		innerBlok2.cas.add(al9);
		innerBlok2.cas.add(al10);
		al4.add(new Action(innerBlok2));
		al11.condition = "#druk < #param.drukSuppletieOpen";
		al11.add(new Action("DRUKSUPPLETIE", AT.TARGET));	
		blok1.cas.add(al11);
		blok1.print();		
/*
	
	        #NIETBESCHIKBAAR:
	            IF #onEntry THEN
	                #oAflaat := 0;
	                #oToevoer := 0;
	                #rodeLed.cmd := #LED_OFF;
	                #groeneLed.cmd := #LED_OFF;
	            END_IF;
	            IF #osCmd.stelBeschikbaar THEN
	                IF #druk < #param.maxKoppeldruk - #param.drukHysterese THEN
	                    #newState := #AANKOPPELEN_MAG;
	                ELSE
	                    #newState := #AFLAAT_AANKOPPELEN;
	                END_IF;
	            ELSIF #osCmd.startInertiseren THEN
	                #newState := #INERTISEREN_ERROR; // default
	                IF #drukCentaalPaneel > #param.ip.maxDrukPT701 THEN
	                    #errNo := 1; // druk te hoog H2 ipv N2 ??
	                ELSIF #drukCentaalPaneel < #param.ip.minDrukPT701 THEN
	                    #errNo := 2; // N2 druk te laag om goed te kunnen inertiseren
	                ELSIF NOT #gb704B THEN
	                    #errNo := 3; // H2 afsluiter XV704 niet dicht
	                ELSIF NOT #gbToevoerDicht THEN
	                    #errNo := 4; // Toevoer afsluiter niet dicht
	                ELSIF NOT #gbBurenDicht THEN
	                    #errNo := 5; // Toevoer afsluiters van buur vulpunten niet beide dicht
	                ELSE
	                    #newState := #INERTISEREN;
	                END_IF;
	            ELSIF #druk < #param.drukSuppletieOpen THEN
	                #newState := #DRUKSUPPLETIE;
	            END_IF;		
		
		
*/
		
		
		
//		ca1.add(new Action(innerBlok));
/*		
 * 		#IDLE:
 * 
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
