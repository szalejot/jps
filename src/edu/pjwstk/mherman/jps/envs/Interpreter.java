package edu.pjwstk.mherman.jps.envs;

import java.util.ArrayList;
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
import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBooleanResult;
import edu.pjwstk.jps.result.IDoubleResult;
import edu.pjwstk.jps.result.IIntegerResult;
import edu.pjwstk.jps.result.IReferenceResult;
import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISimpleResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStringResult;
import edu.pjwstk.jps.result.IStructResult;
import edu.pjwstk.mherman.jps.result.BagResult;
import edu.pjwstk.mherman.jps.result.BinderResult;
import edu.pjwstk.mherman.jps.result.BooleanResult;
import edu.pjwstk.mherman.jps.result.DoubleResult;
import edu.pjwstk.mherman.jps.result.IntegerResult;
import edu.pjwstk.mherman.jps.result.SequenceResult;
import edu.pjwstk.mherman.jps.result.StringResult;
import edu.pjwstk.mherman.jps.result.StructResult;

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
        expr.getInnerExpression().accept(this);
        IAbstractQueryResult qResult = qres.pop();
        if (qResult instanceof ISingleResult) {
            qres.push(new BinderResult(expr.getAuxiliaryName(), qResult));
        } else { //qResult instanceof ICollectionResult
            if (qResult instanceof IBagResult) {
                List<ISingleResult> srcList = new ArrayList<ISingleResult>(((IBagResult) qResult).getElements());
                List<ISingleResult> resList = new ArrayList<ISingleResult>();
                for (ISingleResult sRes : srcList) {
                    resList.add(new BinderResult(expr.getAuxiliaryName(), sRes));
                }
                qres.push(new BagResult(resList));
            } else { // qResult instanceof ISequenceResult
                List<ISingleResult> srcList = ((ISequenceResult) qResult).getElements();
                List<ISingleResult> resList = new ArrayList<ISingleResult>();
                for (ISingleResult sRes : srcList) {
                    resList.add(new BinderResult(expr.getAuxiliaryName(), sRes));
                }
                qres.push(new SequenceResult(resList));
            }
        }
    }

    @Override
    public void visitGroupAsExpression(IGroupAsExpression expr) {
        expr.getInnerExpression().accept(this);
        IAbstractQueryResult qResult = qres.pop();
        qres.push(new BinderResult(expr.getAuxiliaryName(), qResult));
    }

    @Override
    public void visitAllExpression(IForAllExpression expr) {
        expr.getLeftExpression().accept(this);
        List<ISingleResult> elList = getSingleResultList(qres.pop());
        for (ISingleResult el : elList) {
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            IAbstractQueryResult res = qres.pop();
            if (!(res instanceof IBooleanResult)) {
                System.out.println("ERROR: Not IBooleanResult in visitAllExpression: " + res);
                System.exit(1);
            }
            if (!((IBooleanResult) res).getValue()) {
                qres.push(new BooleanResult(false));
                return;
            }
        }
        qres.push(new BooleanResult(true));
    }

    @Override
    public void visitAndExpression(IAndExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        if (!(leftRes instanceof IBooleanResult) || !(rightRes instanceof IBooleanResult)) {
            System.out.println("ERROR: Wrong expression results in visitAndExpression : " + leftRes + ", " + rightRes);
            System.exit(1);
        }
        qres.push(new BooleanResult(((IBooleanResult) leftRes).getValue() && ((IBooleanResult) rightRes).getValue()));
    }

    @Override
    public void visitAnyExpression(IForAnyExpression expr) {
        expr.getLeftExpression().accept(this);
        List<ISingleResult> elList = getSingleResultList(qres.pop());
        for (ISingleResult el : elList) {
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            IAbstractQueryResult res = qres.pop();
            if (!(res instanceof IBooleanResult)) {
                System.out.println("ERROR: Not IBooleanResult in visitAllExpression: " + res);
                System.exit(1);
            }
            if (((IBooleanResult) res).getValue()) {
                qres.push(new BooleanResult(true));
                return;
            }
        }
        qres.push(new BooleanResult(false));
    }

    @Override
    public void visitCloseByExpression(ICloseByExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitCommaExpression(ICommaExpression expr) {
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult e2Res = qres.pop();
        IAbstractQueryResult e1Res = qres.pop();
        for (ISingleResult e1 : getSingleResultList(e1Res)) {
            for (ISingleResult e2 : getSingleResultList(e2Res)) {
                List<ISingleResult> structList = new ArrayList<ISingleResult>();
                if (e1 instanceof IStructResult) {
                    structList.addAll(((IStructResult) e1).elements());
                } else {
                    structList.add(e1);
                }
                if (e2 instanceof IStructResult) {
                    structList.addAll(((IStructResult) e2).elements());
                } else {
                    structList.add(e2);
                }
                eresList.add(new StructResult(structList));
            }
        }
        qres.push(new BagResult(eresList));
    }

    @Override
    public void visitDivideExpression(IDivideExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        ISingleResult i1 = null;
        ISingleResult i2 = null;
        try {
            i1 = getSingleResult(qres.pop());
            i2 = getSingleResult(qres.pop());
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitDivideExpression");
            System.exit(1);
        }
        if (!(i1 instanceof IIntegerResult) || !(i1 instanceof IDoubleResult) 
                || !(i2 instanceof IIntegerResult) || !(i2 instanceof IDoubleResult)) {
            System.out.println("ERROR: wrong types for visitDivideExpression");
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
            resVal /= ((IIntegerResult) i2).getValue();
        } else {
            resVal /= ((IDoubleResult) i2).getValue();
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
    public void visitDotExpression(IDotExpression expr) {
        expr.getLeftExpression().accept(this);
        IAbstractQueryResult leftResult = qres.pop();
        List<ISingleResult> leftElemsList = getSingleResultList(leftResult);
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        for (ISingleResult sRes : leftElemsList) {
            envs.push(envs.nested(sRes, store));
            expr.getRightExpression().accept(this);
            eresList.addAll(getSingleResultList(qres.pop()));
        }
        qres.push(new BagResult(eresList));
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
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        expr.getLeftExpression().accept(this);
        IAbstractQueryResult leftRes = qres.pop();
        for (ISingleResult el : getSingleResultList(leftRes)) {
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            for (ISingleResult el2 : getSingleResultList(qres.pop())) {
                List<ISingleResult> structList = new ArrayList<ISingleResult>();
                structList.add(el);
                if (el2 instanceof IStructResult) {
                    structList.addAll(((IStructResult) el2).elements());
                } else {
                    structList.add(el2);
                }
                eresList.add(new StructResult(structList));
            }
        }
        qres.push(new BagResult(eresList));
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
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        ISingleResult i1 = null;
        ISingleResult i2 = null;
        try {
            i1 = getSingleResult(qres.pop());
            i2 = getSingleResult(qres.pop());
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitMultiplyExpression");
            System.exit(1);
        }
        if (!(i1 instanceof IIntegerResult) || !(i1 instanceof IDoubleResult) 
                || !(i2 instanceof IIntegerResult) || !(i2 instanceof IDoubleResult)) {
            System.out.println("ERROR: wrong types for visitMultiplyExpression");
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
            resVal *= ((IIntegerResult) i2).getValue();
        } else {
            resVal *= ((IDoubleResult) i2).getValue();
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
    public void visitNotEqualsExpression(INotEqualsExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitOrderByExpression(IOrderByExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitOrExpression(IOrExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        if (!(leftRes instanceof IBooleanResult) || !(rightRes instanceof IBooleanResult)) {
            System.out.println("ERROR: Wrong expression results in visitAndExpression : " + leftRes + ", " + rightRes);
            System.exit(1);
        }
        qres.push(new BooleanResult(((IBooleanResult) leftRes).getValue() && ((IBooleanResult) rightRes).getValue()));
    }

    @Override
    public void visitPlusExpression(IPlusExpression expr) {
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
        if ((i1 instanceof IStringResult) || (i2 instanceof IStringResult)) {
            qres.push(new StringResult(getStringValue(i1) + getStringValue(i2)));
        }
        if (!(i1 instanceof IIntegerResult) || !(i1 instanceof IDoubleResult) 
                || !(i2 instanceof IIntegerResult) || !(i2 instanceof IDoubleResult)) {
            System.out.println("ERROR: wrong types for visitMinusExpression: " + i1 + ", " + i2);
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
            resVal += ((IIntegerResult) i2).getValue();
        } else {
            resVal += ((IDoubleResult) i2).getValue();
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
    public void visitUnionExpression(IUnionExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        eresList.addAll(getSingleResultList(qres.pop()));
        eresList.addAll(getSingleResultList(qres.pop()));
        qres.push(new BagResult(eresList));
    }

    @Override
    public void visitWhereExpression(IWhereExpression expr) {
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        expr.getLeftExpression().accept(this);
        IAbstractQueryResult leftRes = qres.pop();
        for (ISingleResult el : getSingleResultList(leftRes)) {
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            IAbstractQueryResult innerRes = qres.pop();
            if (!(innerRes instanceof IBooleanResult)) {
                System.out.println("ERROR: Not IBooleanResult in right side of IWhereExpression: " + innerRes);
                System.exit(1);
            }
            if (((IBooleanResult) innerRes).getValue()) {
                eresList.add(el);
            }
        }
        qres.push(new BagResult(eresList));
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
        expr.getInnerExpression().accept(this);
        qres.push(new BagResult(getSingleResultList(qres.pop()))); // ONE LINE! :-D
    }

    @Override
    public void visitCountExpression(ICountExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop());
        qres.push(new IntegerResult(resList.size()));
    }

    @Override
    public void visitExistsExpression(IExistsExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop());
        if (resList.size() > 0) {
            qres.push(new BooleanResult(true));
        } else {
            qres.push(new BooleanResult(false));
        }
    }

    @Override
    public void visitMaxExpression(IMaxExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop());
        double max = -Double.MAX_VALUE;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            if (sRes instanceof IReferenceResult) {
                ISBAObject obj = store.retrieve(((IReferenceResult) sRes).getOIDValue());
                if (obj instanceof IDoubleObject) {
                    sRes = new DoubleResult(((IDoubleObject) obj).getValue());
                } else if (obj instanceof IIntegerObject) {
                    sRes = new IntegerResult(((IIntegerObject) obj).getValue());
                } else {
                    System.out.println("Illegal type for operation min: " + obj);
                    System.exit(1);
                }
            }
            if (sRes instanceof IDoubleResult) {
                double val = ((IDoubleResult) sRes).getValue();
                isResDouble = true;
                if (val > max) {
                    max = val;
                }
            } else if (sRes instanceof IIntegerResult) {
                int val = ((IIntegerResult) sRes).getValue();
                if (val > max) {
                    max = val;
                }
            } else {
                System.out.println("Illegal type for operation min: " + sRes);
                System.exit(1);
            }
        }
        if (isResDouble) {
            qres.push(new DoubleResult(max));
        } else {
            qres.push(new IntegerResult((int) max));
        }
    }

    @Override
    public void visitMinExpression(IMinExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop());
        double min = Double.MAX_VALUE;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            if (sRes instanceof IReferenceResult) {
                ISBAObject obj = store.retrieve(((IReferenceResult) sRes).getOIDValue());
                if (obj instanceof IDoubleObject) {
                    sRes = new DoubleResult(((IDoubleObject) obj).getValue());
                } else if (obj instanceof IIntegerObject) {
                    sRes = new IntegerResult(((IIntegerObject) obj).getValue());
                } else {
                    System.out.println("Illegal type for operation min: " + obj);
                    System.exit(1);
                }
            }
            if (sRes instanceof IDoubleResult) {
                double val = ((IDoubleResult) sRes).getValue();
                isResDouble = true;
                if (val < min) {
                    min = val;
                }
            } else if (sRes instanceof IIntegerResult) {
                int val = ((IIntegerResult) sRes).getValue();
                if (val < min) {
                    min = val;
                }
            } else {
                System.out.println("Illegal type for operation min: " + sRes);
                System.exit(1);
            }
        }
        if (isResDouble) {
            qres.push(new DoubleResult(min));
        } else {
            qres.push(new IntegerResult((int) min));
        }
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
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop());
        double sum = 0;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            if (sRes instanceof IReferenceResult) {
                ISBAObject obj = store.retrieve(((IReferenceResult) sRes).getOIDValue());
                if (obj instanceof IDoubleObject) {
                    sRes = new DoubleResult(((IDoubleObject) obj).getValue());
                } else if (obj instanceof IIntegerObject) {
                    sRes = new IntegerResult(((IIntegerObject) obj).getValue());
                } else {
                    System.out.println("Illegal type for operation min: " + obj);
                    System.exit(1);
                }
            }
            if (sRes instanceof IDoubleResult) {
                double val = ((IDoubleResult) sRes).getValue();
                isResDouble = true;
                sum += val;
            } else if (sRes instanceof IIntegerResult) {
                int val = ((IIntegerResult) sRes).getValue();
                sum += val;
            } else {
                System.out.println("Illegal type for operation min: " + sRes);
                System.exit(1);
            }
        }
        if (isResDouble) {
            qres.push(new DoubleResult(sum));
        } else {
            qres.push(new IntegerResult((int) sum));
        }
    }

    @Override
    public void visitUniqueExpression(IUniqueExpression expr) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAvgExpression(IAvgExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop());
        double avg = 0;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            if (sRes instanceof IReferenceResult) {
                ISBAObject obj = store.retrieve(((IReferenceResult) sRes).getOIDValue());
                if (obj instanceof IDoubleObject) {
                    sRes = new DoubleResult(((IDoubleObject) obj).getValue());
                } else if (obj instanceof IIntegerObject) {
                    sRes = new IntegerResult(((IIntegerObject) obj).getValue());
                } else {
                    System.out.println("Illegal type for operation min: " + obj);
                    System.exit(1);
                }
            }
            if (sRes instanceof IDoubleResult) {
                double val = ((IDoubleResult) sRes).getValue();
                isResDouble = true;
                avg += val;
            } else if (sRes instanceof IIntegerResult) {
                int val = ((IIntegerResult) sRes).getValue();
                avg += val;
            } else {
                System.out.println("Illegal type for operation min: " + sRes);
                System.exit(1);
            }
        }
        avg /= resList.size();
        if (isResDouble) {
            qres.push(new DoubleResult(avg));
        } else {
            qres.push(new IntegerResult((int) avg));
        }
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
    
    private List<ISingleResult> getSingleResultList(IAbstractQueryResult result) {
        List<ISingleResult> resList = new ArrayList<ISingleResult>();
        if (result instanceof ISingleResult) {
            resList.add((ISingleResult) result);
        } else { // leftResult instanceof ICollectionResult
            if (result instanceof IBagResult) {
                resList.addAll(((IBagResult) result).getElements());
            } else { // leftResult instanceof ISequenceResult
                resList.addAll(((ISequenceResult) result).getElements());
            }
        }
        return resList;
    }
    
    @SuppressWarnings("rawtypes")
    private String getStringValue(ISingleResult result) {
        if (result instanceof IStringResult) {
            return ((IStringResult) result).getValue();
        } else if (result instanceof ISimpleResult) {
            return ((ISimpleResult) result).getValue().toString();
        } else if (result instanceof IReferenceResult) {
            // TODO ???
            return "";
        } else {
            // BINDER? STUCT?
            return "";
        }
    }
    
    public static class TypeCoercionException extends Exception {
        private static final long serialVersionUID = -378579275226248409L;
    }

}
