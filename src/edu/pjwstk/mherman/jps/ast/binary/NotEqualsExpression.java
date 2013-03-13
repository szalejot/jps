package edu.pjwstk.mherman.jps.ast.binary;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.binary.INotEqualsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class NotEqualsExpression implements INotEqualsExpression {

	private IExpression leftExpression;
	private IExpression rightExpression;
	
	public NotEqualsExpression(IExpression leftExpression, IExpression rightExpression) {
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
		// TODO Auto-generated method stub

	}

}
