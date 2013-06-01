package edu.pjwstk.mherman.jps.ast.unary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class BagExpression implements IBagExpression {

	private IExpression innerExpression;
	
	public BagExpression(IExpression expression) {
		this.innerExpression = expression;
	}
	
	@Override
	public IExpression getInnerExpression() {
		return innerExpression;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitBagExpression(this);
	}

}
