package edu.pjwstk.mherman.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BooleanTerminal implements IBooleanTerminal {

	private Boolean value;
	
	BooleanTerminal(Boolean value) {
		this.value = value;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean getValue() {
		return value;
	}

}
