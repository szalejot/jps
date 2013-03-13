package edu.pjwstk.mherman.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class StructExpression implements IStructExpression {

	private IExpression innerExpression;
	
	public StructExpression(IExpression expression) {
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
