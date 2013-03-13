package edu.pjwstk.mherman.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class DoubleTerminal implements IDoubleTerminal {

	private Double value;
	
	DoubleTerminal(Double value) {
		this.value = value;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Double getValue() {
		return value;
	}

}
