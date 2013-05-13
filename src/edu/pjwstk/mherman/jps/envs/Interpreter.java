package edu.pjwstk.mherman.jps.envs;

import java.util.Collection;
import java.util.List;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.ast.auxname.IAsExpression;
import edu.pjwstk.jps.ast.auxname.IGroupAsExpression;
import edu.pjwstk.jps.ast.binary.IAndExpression;
import edu.pjwstk.jps.ast.binary.ICloseByExpression;
import edu.pjwstk.jps.ast.binary.ICommaExpression;
import edu.pjwstk.jps.ast.binary.IDivideExpression;
import edu.pjwstk.jps.ast.binary.IDotExpression;
import edu.pjwstk.jps.ast.binary.IEqualsExpression;
import edu.pjwstk.jps.ast.binary.IForAllExpression;
import edu.pjwstk.jps.ast.binary.IForAnyExpression;
import edu.pjwstk.jps.ast.binary.IGreaterOrEqualThanExpression;
import edu.pjwstk.jps.ast.binary.IGreaterThanExpression;
import edu.pjwstk.jps.ast.binary.IInExpression;
import edu.pjwstk.jps.ast.binary.IIntersectExpression;
import edu.pjwstk.jps.ast.binary.IJoinExpression;
import edu.pjwstk.jps.ast.binary.ILessOrEqualThanExpression;
import edu.pjwstk.jps.ast.binary.ILessThanExpression;
import edu.pjwstk.jps.ast.binary.IMinusExpression;
import edu.pjwstk.jps.ast.binary.IMinusSetExpression;
import edu.pjwstk.jps.ast.binary.IModuloExpression;
import edu.pjwstk.jps.ast.binary.IMultiplyExpression;
import edu.pjwstk.jps.ast.binary.INotEqualsExpression;
import edu.pjwstk.jps.ast.binary.IOrExpression;
import edu.pjwstk.jps.ast.binary.IOrderByExpression;
import edu.pjwstk.jps.ast.binary.IPlusExpression;
import edu.pjwstk.jps.ast.binary.IUnionExpression;
import edu.pjwstk.jps.ast.binary.IWhereExpression;
import edu.pjwstk.jps.ast.binary.IXORExpression;
import edu.pjwstk.jps.ast.terminal.IBooleanTerminal;
import edu.pjwstk.jps.ast.terminal.IDoubleTerminal;
import edu.pjwstk.jps.ast.terminal.IIntegerTerminal;
import edu.pjwstk.jps.ast.terminal.INameTerminal;
import edu.pjwstk.jps.ast.terminal.IStringTerminal;
import edu.pjwstk.jps.ast.unary.IAvgExpression;
import edu.pjwstk.jps.ast.unary.IBagExpression;
import edu.pjwstk.jps.ast.unary.ICountExpression;
import edu.pjwstk.jps.ast.unary.IExistsExpression;
import edu.pjwstk.jps.ast.unary.IMaxExpression;
import edu.pjwstk.jps.ast.unary.IMinExpression;
import edu.pjwstk.jps.ast.unary.INotExpression;
import edu.pjwstk.jps.ast.unary.IStructExpression;
import edu.pjwstk.jps.ast.unary.ISumExpression;
import edu.pjwstk.jps.ast.unary.IUniqueExpression;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;
import edu.pjwstk.mherman.jps.result.DoubleResult;
import edu.pjwstk.mherman.jps.result.IntegerResult;

public class Interpreter implements IInterpreter {
    
    ISBAStore store;
    IENVS envs;
    IQResStack qres;
    
    public Interpreter(ISBAStore store, IENVS envs, IQResStack qres) {
        this.store = store;
        this.envs = envs;
        this.qres = qres;
    }

    @Override
    public void visitAsExpression(IAsExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitGroupAsExpression(IGroupAsExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAllExpression(IForAllExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAndExpression(IAndExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAnyExpression(IForAnyExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitCloseByExpression(ICloseByExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitCommaExpression(ICommaExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitDivideExpression(IDivideExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitDotExpression(IDotExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitEqualsExpression(IEqualsExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitGreaterThanExpression(IGreaterThanExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitGreaterOrEqualThanExpression(IGreaterOrEqualThanExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitInExpression(IInExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitIntersectExpression(IIntersectExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitJoinExpression(IJoinExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitLessOrEqualThanExpression(ILessOrEqualThanExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitLessThanExpression(ILessThanExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitMinusExpression(IMinusExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        ISingleResult i1 = null;
        ISingleResult i2 = null;
        try {
            i1 = getSingleResult(qres.pop());
            i2 = getSingleResult(qres.pop());
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitMinusExpression");
            System.exit(1);
        }
        if (!(i1 instanceof IIntegerResult) || !(i1 instanceof IDoubleResult) 
                || !(i2 instanceof IIntegerResult) || !(i2 instanceof IDoubleResult)) {
            System.out.println("ERROR: wrong types for visitMinusExpression");
            System.exit(1);
        }
        Double resVal = 0d;
        boolean isResDouble = false;
        if (i1 instanceof IIntegerResult) {
            resVal += ((IIntegerResult) i1).getValue();
        } else {
            resVal += ((IDoubleResult) i1).getValue();
            isResDouble = true;
        }
        if (i2 instanceof IIntegerResult) {
            resVal -= ((IIntegerResult) i2).getValue();
        } else {
            resVal -= ((IDoubleResult) i2).getValue();
            isResDouble = true;
        }
        ISingleResult res;
        if (isResDouble) {
            res = new DoubleResult(resVal);
        } else {
            res = new IntegerResult(resVal.intValue());
        }
        qres.push(res);
    }

    @Override
    public void visitMinusSetExpression(IMinusSetExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitModuloExpression(IModuloExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitMultiplyExpression(IMultiplyExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitNotEqualsExpression(INotEqualsExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitOrderByExpression(IOrderByExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitOrExpression(IOrExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitPlusExpression(IPlusExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitUnionExpression(IUnionExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitWhereExpression(IWhereExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitXORExpression(IXORExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitBooleanTerminal(IBooleanTerminal expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitDoubleTerminal(IDoubleTerminal expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitIntegerTerminal(IIntegerTerminal expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitNameTerminal(INameTerminal expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitStringTerminal(IStringTerminal expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitBagExpression(IBagExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitCountExpression(ICountExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitExistsExpression(IExistsExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitMaxExpression(IMaxExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitMinExpression(IMinExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitNotExpression(INotExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitStructExpression(IStructExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitSumExpression(ISumExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitUniqueExpression(IUniqueExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAvgExpression(IAvgExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public IAbstractQueryResult eval(IExpression queryTreeRoot) {
        // TODO Auto-generated method stub
        return null;
    }
    
    private ISingleResult getSingleResult(IAbstractQueryResult aResult) throws TypeCoercionException {
        if (aResult instanceof ISingleResult) {
            return (ISingleResult) aResult;
        } else {
          if (aResult instanceof IBagResult) {
              Collection<ISingleResult> collection = ((IBagResult) aResult).getElements();
              if (collection.size() != 1) {
                  throw new TypeCoercionException();
              } else {
                  return (ISingleResult) collection.toArray()[0];
              }
          } else if (aResult instanceof IStructResult) {
              List<ISingleResult> list = ((IStructResult) aResult).elements();
              if (list.size() != 1) {
                  throw new TypeCoercionException();
              } else {
                  return list.get(0);
              }
          }
        }
        throw new TypeCoercionException();
    }
    
    private class TypeCoercionException extends Exception {
        private static final long serialVersionUID = -378579275226248409L;
        
    }

}
