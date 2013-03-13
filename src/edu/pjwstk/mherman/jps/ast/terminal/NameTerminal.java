package edu.pjwstk.mherman.jps.ast.terminal;

import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NameTerminal implements INameTerminal {

	private String name;
	
	public NameTerminal(String name) {
		this.name = name;
	}
	
	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return name;
	}

}
