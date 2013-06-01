package edu.pjwstk.mherman.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.ISumExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class SumExpression implements ISumExpression {

	private IExpression innerExpression;
	
	public SumExpression(IExpression expression) {
		this.innerExpression = expression;
	}
	
	@Override
	public IExpression getInnerExpression() {
		return innerExpression;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitSumExpression(this);
	}

}
