package edu.pjwstk.mherman.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IGreaterOrEqualThanExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class GreaterOrEqualThanExpression implements
		IGreaterOrEqualThanExpression {

	private IExpression leftExpression;
	private IExpression rightExpression;
	
	public GreaterOrEqualThanExpression(IExpression leftExpression, IExpression rightExpression) {
		this.leftExpression = leftExpression;
		this.rightExpression = rightExpression;
	}
	
	@Override
	public IExpression getLeftExpression() {
		return leftExpression;
	}

	@Override
	public IExpression getRightExpression() {
		return rightExpression;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visitGreaterOrEqualThanExpression(this);
	}

}
