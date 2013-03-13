package edu.pjwstk.mherman.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.ICountExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class CountExpression implements ICountExpression {

	private IExpression innerExpression;
	
	public CountExpression(IExpression expression) {
		this.innerExpression = expression;
	}
	
	@Override
	public IExpression getInnerExpression() {
		return innerExpression;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		// TODO Auto-generated method stub

	}

}
