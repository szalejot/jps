package edu.pjwstk.mherman.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.IIntersectExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class IntersectExpression implements IIntersectExpression {

	private IExpression leftExpression;
	private IExpression rightExpression;
	
	public IntersectExpression(IExpression leftExpression, IExpression rightExpression) {
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
		visitor.visitIntersectExpression(this);
	}

}
