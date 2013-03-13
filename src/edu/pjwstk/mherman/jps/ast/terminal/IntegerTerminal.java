package edu.pjwstk.mherman.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class IntegerTerminal implements IIntegerTerminal {

	private Integer value;
	
	public IntegerTerminal(Integer value) {
		this.value = value;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getValue() {
		return value;
	}

}
