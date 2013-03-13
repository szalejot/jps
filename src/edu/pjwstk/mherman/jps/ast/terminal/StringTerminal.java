package edu.pjwstk.mherman.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IStringTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StringTerminal implements IStringTerminal {

	private String value;
	
	public StringTerminal(String value) {
		this.value = value;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getValue() {
		return value;
	}

}
