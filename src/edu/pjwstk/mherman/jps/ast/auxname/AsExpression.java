package edu.pjwstk.mherman.jps.ast.auxname;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.visitor.ASTVisitor;

public class AsExpression implements IAsExpression {

	private String auxiliaryName;
	private IExpression innerExpression;
	
	public AsExpression(IExpression expression, String auxiliaryName) {
		this.innerExpression = expression;
		this.auxiliaryName = auxiliaryName;
	}
	
	@Override
	public String getAuxiliaryName() {
		return auxiliaryName;
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
