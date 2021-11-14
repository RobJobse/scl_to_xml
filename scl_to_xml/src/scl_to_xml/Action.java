package scl_to_xml;

//enum actionType { EFFECT, TARGET };

public class Action {
	enum AT { EFFECT, TARGET }; // { EFFECT, TARGET, IFEXPR };
	public AT type;
	public boolean lineIsIfExpression; // controls which field is valid
	public String line;          // this member
	public IfExpression ifLine;  // or this member

	/**
	 * Constructor of a normal action expression
	 * @param line
	 * @param type
	 */
	
	public Action(String line , AT type) {
		this.line = line;
		this.type = type;
	}
	
	/**
	 * Constructor of an Ifexpr. action expression
	 * @param iline
	 */
	public Action(IfExpression iline ) {
		ifLine = iline;
		//this.type = type;
		lineIsIfExpression = true;	
	}	
	
	
	
	public void print() {
		if(!lineIsIfExpression) {
			System.out.println(line);
		} else {
			if(!ifLine.cas.isEmpty()) {
			System.out.println("<guard>");
			System.out.println(ifLine.cas.get(0).condition  );
			System.out.println("</guard>");			
			}
		}
	}
	
}


