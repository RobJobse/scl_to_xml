package scl_to_xml;

// toegevoegd


import java.io.FileNotFoundException;
import java.io.IOException;

public class Scl_to_xml_app {

	public static void main(String[] args) throws FileNotFoundException,IOException {

		System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	
		ParsingStateMachine psm = new ParsingStateMachine("test01");
	    
		CondActionTest castest = new CondActionTest();
	}

}
