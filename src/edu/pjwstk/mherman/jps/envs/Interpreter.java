package edu.pjwstk.mherman.jps.envs;

import java.util.ArrayList;
import java.util.Arrays;
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
import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.IOID;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IInterpreter;
import edu.pjwstk.jps.interpreter.qres.IQResStack;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.IBinderResult;
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
import edu.pjwstk.mherman.jps.result.ReferenceResult;
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
        List<ISingleResult> elList = getSingleResultList(qres.pop(), true);
        for (ISingleResult el : elList) {
            el = (ISingleResult) makeDerefenceIfReference(el);
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            IAbstractQueryResult res = qres.pop();
            try {
                res = getSingleResult(res, true);
            } catch (TypeCoercionException e) {
                System.out.println("ERROR: Not IBooleanResult in visitAllExpression: " + res);
                System.exit(1);
            }
            res = makeDerefenceIfReference(res);
            if (!(res instanceof IBooleanResult)) {
                System.out.println("ERROR: Not IBooleanResult in visitAllExpression: " + res);
                System.exit(1);
            }
            try {
                if (!((IBooleanResult) res).getValue()) {
                    qres.push(new BooleanResult(false));
                    return;
                }
            } finally {
                envs.pop();
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
        try {
            rightRes = getSingleResult(rightRes, true);
            leftRes = getSingleResult(leftRes, true);
        } catch (TypeCoercionException e) {
            System.out.println("ERROR: Wrong expression results in visitAndExpression : " + leftRes + ", " + rightRes);
            System.exit(1);
        }
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        if (!(leftRes instanceof IBooleanResult) || !(rightRes instanceof IBooleanResult)) {
            System.out.println("ERROR: Wrong expression results in visitAndExpression : " + leftRes + ", " + rightRes);
            System.exit(1);
        }
        qres.push(new BooleanResult(((IBooleanResult) leftRes).getValue() && ((IBooleanResult) rightRes).getValue()));
    }

    @Override
    public void visitAnyExpression(IForAnyExpression expr) {
        expr.getLeftExpression().accept(this);
        List<ISingleResult> elList = getSingleResultList(qres.pop(), true);
        for (ISingleResult el : elList) {
            el = (ISingleResult) makeDerefenceIfReference(el);
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            IAbstractQueryResult res = qres.pop();
            try {
                res = getSingleResult(res, true);
            } catch (TypeCoercionException e) {
                System.out.println("ERROR: Not IBooleanResult in visitAnyExpression: " + res);
                System.exit(1);
            }
            res = makeDerefenceIfReference(res);
            if (!(res instanceof IBooleanResult)) {
                System.out.println("ERROR: Not IBooleanResult in visitAnyExpression: " + res);
                System.exit(1);
            }
            try {
                if (((IBooleanResult) res).getValue()) {
                    qres.push(new BooleanResult(true));
                    return;
                }
            } finally {
                envs.pop();
            }
        }
        qres.push(new BooleanResult(false));
    }

    @Override
    public void visitCloseByExpression(ICloseByExpression expr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitCommaExpression(ICommaExpression expr) {
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult e2Res = qres.pop();
        IAbstractQueryResult e1Res = qres.pop();
        for (ISingleResult e1 : getSingleResultList(e1Res, false)) {
            for (ISingleResult e2 : getSingleResultList(e2Res, false)) {
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
            i2 = getSingleResult(qres.pop(), true);
            i1 = getSingleResult(qres.pop(), true);
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitDivideExpression");
            System.exit(1);
        }
        i1 = (ISingleResult) makeDerefenceIfReference(i1);
        i2 = (ISingleResult) makeDerefenceIfReference(i2);
        if (!((i1 instanceof IIntegerResult) || (i1 instanceof IDoubleResult)) 
                || !((i2 instanceof IIntegerResult) || (i2 instanceof IDoubleResult))) {
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
        List<ISingleResult> leftElemsList = getSingleResultList(leftResult, true);
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        for (ISingleResult sRes : leftElemsList) {
            envs.push(envs.nested(sRes, store));
            expr.getRightExpression().accept(this);
            eresList.addAll(getSingleResultList(qres.pop(), true));
            envs.pop();
        }
        qres.push(new BagResult(eresList));
    }

    @Override
    public void visitEqualsExpression(IEqualsExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult i1 = qres.pop();
        IAbstractQueryResult i2 = qres.pop();
        qres.push(new BooleanResult(equals(i1, i2)));
    }
    
    @SuppressWarnings("rawtypes")
    private boolean equals(final IAbstractQueryResult i1, final IAbstractQueryResult i2) {
        IAbstractQueryResult loci1 = i1;
        IAbstractQueryResult loci2 = i2;
        try {
            loci1 = getSingleResult(loci1, true);
        } catch (TypeCoercionException e) {
            // NOP
        }
        try {
            loci2 = getSingleResult(loci2, true);
        } catch (TypeCoercionException e) {
            // NOP
        }
        if (loci1.getClass() != loci2.getClass()) {
            return false;
        } else if (loci1 instanceof ISimpleResult) {
            Object obj1 = ((ISimpleResult) loci1).getValue();
            Object obj2 = ((ISimpleResult) loci2).getValue();
            return obj1.equals(obj2);
        } else if (loci1 instanceof IReferenceResult) {
            IOID oid1 = ((IReferenceResult) loci1).getOIDValue();
            IOID oid2 = ((IReferenceResult) loci2).getOIDValue();
            return oid1.equals(oid2);
        } else {
            return loci1.equals(loci2);
        }
    }
    
    /**
     * 
     * @param i1
     * @param i2
     * @return true if (i1 > i2)
     */
    private boolean greater(final IAbstractQueryResult i1, final IAbstractQueryResult i2) {
        IAbstractQueryResult loci1 = null;
        IAbstractQueryResult loci2 = null;
        try {
            loci1 = getSingleResult(i1, true);
            loci2 = getSingleResult(i2, true);
        } catch (TypeCoercionException e) {
            System.out.println("TypeCoercionException in greater:" + i1 + ", " + i2);
            System.exit(1);
        }
        if (!(loci1 instanceof IIntegerResult) || !(loci1 instanceof IDoubleResult) 
                || !(loci2 instanceof IIntegerResult) || !(loci2 instanceof IDoubleResult)) {
            System.out.println("Wrong types (not Integer/Double) in greater:" + loci1 + ", " + loci2);
            System.exit(1);
        }
        double val1, val2;
        if (loci1 instanceof IIntegerResult) {
            val1 = ((IIntegerResult) loci1).getValue();
        } else { // loci1 instanceof IDoubleResult
            val1 = ((IDoubleResult) loci1).getValue();
        }
        if (loci2 instanceof IIntegerResult) {
            val2 = ((IIntegerResult) loci2).getValue();
        } else { // loci2 instanceof IDoubleResult
            val2 = ((IDoubleResult) loci2).getValue();
        }
        return (val1 > val2);
    }

    @Override
    public void visitGreaterThanExpression(IGreaterThanExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        qres.push(new BooleanResult(greater(leftRes, rightRes)));
    }

    @Override
    public void visitGreaterOrEqualThanExpression(IGreaterOrEqualThanExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        qres.push(new BooleanResult(greater(leftRes, rightRes) || equals(leftRes, rightRes)));
    }

    @Override
    public void visitInExpression(IInExpression expr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitIntersectExpression(IIntersectExpression expr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitJoinExpression(IJoinExpression expr) {
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        expr.getLeftExpression().accept(this);
        IAbstractQueryResult leftRes = qres.pop();
        for (ISingleResult el : getSingleResultList(leftRes, true)) {
            envs.push(envs.nested(el, store));
            expr.getRightExpression().accept(this);
            for (ISingleResult el2 : getSingleResultList(qres.pop(), true)) {
                List<ISingleResult> structList = new ArrayList<ISingleResult>();
                structList.add(el);
                if (el2 instanceof IStructResult) {
                    structList.addAll(((IStructResult) el2).elements());
                } else {
                    structList.add(el2);
                }
                eresList.add(new StructResult(structList));
            }
            envs.pop();
        }
        qres.push(new BagResult(eresList));
    }

    @Override
    public void visitLessOrEqualThanExpression(ILessOrEqualThanExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        qres.push(new BooleanResult(greater(rightRes, leftRes) || equals(leftRes, rightRes)));
    }

    @Override
    public void visitLessThanExpression(ILessThanExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        qres.push(new BooleanResult(greater(rightRes, leftRes)));
    }

    @Override
    public void visitMinusExpression(IMinusExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult i2 = qres.pop();
        IAbstractQueryResult i1 = qres.pop();
        i1 = makeDerefenceIfReference(i1);
        i2 = makeDerefenceIfReference(i2);
        try {
            i1 = getSingleResult(i1, true);
            i2 = getSingleResult(i2, true);
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitMinusExpression");
            System.exit(1);
        }
        if (!((i1 instanceof IIntegerResult) || (i1 instanceof IDoubleResult)) 
                || !((i2 instanceof IIntegerResult) || (i2 instanceof IDoubleResult))) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitModuloExpression(IModuloExpression expr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitMultiplyExpression(IMultiplyExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult i2 = qres.pop();
        IAbstractQueryResult i1 = qres.pop();
        i1 = makeDerefenceIfReference(i1);
        i2 = makeDerefenceIfReference(i2);
        try {
            i1 = getSingleResult(i1, true);
            i2 = getSingleResult(i2, true);
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitMultiplyExpression");
            System.exit(1);
        }
        if (!((i1 instanceof IIntegerResult) || (i1 instanceof IDoubleResult)) 
                || !((i2 instanceof IIntegerResult) || (i2 instanceof IDoubleResult))) {
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
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        qres.push(new BooleanResult(!equals(leftRes, rightRes)));
    }

    @Override
    public void visitOrderByExpression(IOrderByExpression expr) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visitOrExpression(IOrExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        if (!(leftRes instanceof IBooleanResult) || !(rightRes instanceof IBooleanResult)) {
            System.out.println("ERROR: Wrong expression results in visitAndExpression : " + leftRes + ", " + rightRes);
            System.exit(1);
        }
        qres.push(new BooleanResult(((IBooleanResult) leftRes).getValue() && ((IBooleanResult) rightRes).getValue()));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void visitPlusExpression(IPlusExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult i2 = qres.pop();
        IAbstractQueryResult i1 = qres.pop();
        i1 = makeDerefenceIfReference(i1);
        i2 = makeDerefenceIfReference(i2);
        try {
            i1 = getSingleResult(i1, true);
            i2 = getSingleResult(i2, true);
        } catch (TypeCoercionException ex) {
            System.out.println("ERROR: TypeCoercionException in visitPlusExpression");
            System.exit(1);
        }
        if ((i1 instanceof IStringResult) || (i2 instanceof IStringResult)) {
            qres.push(new StringResult(getStringValue((ISimpleResult) i1) + getStringValue((ISimpleResult) i2)));
            return;
        }
        if (!((i1 instanceof IIntegerResult) || (i1 instanceof IDoubleResult)) 
                || !((i2 instanceof IIntegerResult) || (i2 instanceof IDoubleResult))) {
            System.out.println("ERROR: wrong types for visitPlusExpression: " + i1 + ", " + i2);
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
        eresList.addAll(getSingleResultList(qres.pop(), false));
        eresList.addAll(getSingleResultList(qres.pop(), false));
        qres.push(new BagResult(eresList));
    }

    @Override
    public void visitWhereExpression(IWhereExpression expr) {
        List<ISingleResult> eresList = new ArrayList<ISingleResult>();
        expr.getLeftExpression().accept(this);
        IAbstractQueryResult leftRes = qres.pop();
        for (ISingleResult el : getSingleResultList(leftRes, true)) {
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
            envs.pop();
        }
        qres.push(new BagResult(eresList));
    }

    @Override
    public void visitXORExpression(IXORExpression expr) {
        expr.getLeftExpression().accept(this);
        expr.getRightExpression().accept(this);
        IAbstractQueryResult rightRes = qres.pop();
        IAbstractQueryResult leftRes = qres.pop();
        rightRes = makeDerefenceIfReference(rightRes);
        leftRes = makeDerefenceIfReference(leftRes);
        if (!(leftRes instanceof IBooleanResult) || !(rightRes instanceof IBooleanResult)) {
            System.out.println("ERROR: Wrong types for XOR: " + leftRes + ", " + rightRes);
            System.exit(1);
        }
        boolean left = ((IBooleanResult) leftRes).getValue();
        boolean right = ((IBooleanResult) rightRes).getValue();
        qres.push(new BooleanResult(left != right));
    }

    @Override
    public void visitBooleanTerminal(IBooleanTerminal expr) {
        qres.push(new BooleanResult(expr.getValue()));
    }

    @Override
    public void visitDoubleTerminal(IDoubleTerminal expr) {
        qres.push(new DoubleResult(expr.getValue()));
    }

    @Override
    public void visitIntegerTerminal(IIntegerTerminal expr) {
        qres.push(new IntegerResult(expr.getValue()));
    }

    @Override
    public void visitNameTerminal(INameTerminal expr) {
        qres.push(envs.bind(expr.getName()));
    }

    @Override
    public void visitStringTerminal(IStringTerminal expr) {
        qres.push(new StringResult(expr.getValue()));
    }

    @Override
    public void visitBagExpression(IBagExpression expr) {
        expr.getInnerExpression().accept(this);
        qres.push(new BagResult(getSingleResultList(qres.pop(), false))); // ONE LINE! :-D
    }

    @Override
    public void visitCountExpression(ICountExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop(), false);
        qres.push(new IntegerResult(resList.size()));
    }

    @Override
    public void visitExistsExpression(IExistsExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop(), false);
        if (resList.size() > 0) {
            qres.push(new BooleanResult(true));
        } else {
            qres.push(new BooleanResult(false));
        }
    }

    @Override
    public void visitMaxExpression(IMaxExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop(), true);
        double max = -Double.MAX_VALUE;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            sRes = (ISingleResult) makeDerefenceIfReference(sRes);
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
                System.out.println("Illegal type for operation max: " + sRes);
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
        List<ISingleResult> resList = getSingleResultList(qres.pop(), true);
        double min = Double.MAX_VALUE;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            sRes = (ISingleResult) makeDerefenceIfReference(sRes);
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
        expr.getInnerExpression().accept(this);
        IAbstractQueryResult sRes = qres.pop();
        sRes = makeDerefenceIfReference(sRes);
        try {
            sRes = getSingleResult(sRes, true);
        } catch (TypeCoercionException e) {
            System.out.println("Illegal type for operation not: " + sRes);
            System.exit(1);
        }
        if (!(sRes instanceof IBooleanResult)) {
            System.out.println("Illegal type for operation not: " + sRes);
            System.exit(1);
        }
        qres.push(new BooleanResult(!((IBooleanResult) sRes).getValue()));
    }

    @Override
    public void visitStructExpression(IStructExpression expr) {
        expr.getInnerExpression().accept(this);
        IAbstractQueryResult sRes = qres.pop();
        if (sRes instanceof IStructResult) {
            qres.push(sRes);
        } else {
            qres.push(new StructResult(getSingleResultList(sRes, true)));
        }
    }

    @Override
    public void visitSumExpression(ISumExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop(), true);
        double sum = 0;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            sRes = (ISingleResult) makeDerefenceIfReference(sRes);
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
        expr.getInnerExpression().accept(this);
        IAbstractQueryResult aRes = qres.pop();
        try {
            aRes = getSingleResult(aRes, true);
        } catch (TypeCoercionException e) {
            // NOP
        }
        if (aRes instanceof ISingleResult) {
            qres.push(new BagResult(Arrays.asList(new ISingleResult[] {(ISingleResult) aRes})));
        } else { // aRes instanceof ICollectionResult
            List<ISingleResult> tmpList;
            if (aRes instanceof IBagResult) {
                tmpList = new ArrayList<ISingleResult>();
                tmpList.addAll(((IBagResult) aRes).getElements());
            } else { // aRes instanceof ISequenceResult
                tmpList = ((ISequenceResult) aRes).getElements();
            }
            List<ISingleResult> resList = new ArrayList<ISingleResult>();
            for (ISingleResult sRes : tmpList) {
                boolean isUnique = true;
                for (ISingleResult sInnerRes : resList) {
                    if (equals(sRes, sInnerRes)) {
                        isUnique = false;
                        break;
                    }
                }
                if (isUnique) {
                    resList.add(sRes);
                }
            }
            qres.push(new BagResult(resList));
        }
    }

    @Override
    public void visitAvgExpression(IAvgExpression expr) {
        expr.getInnerExpression().accept(this);
        List<ISingleResult> resList = getSingleResultList(qres.pop(), true);
        double avg = 0;
        boolean isResDouble = false;
        for (ISingleResult sRes : resList) {
            sRes = (ISingleResult) makeDerefenceIfReference(sRes);
            if (sRes instanceof IDoubleResult) {
                double val = ((IDoubleResult) sRes).getValue();
                isResDouble = true;
                avg += val;
            } else if (sRes instanceof IIntegerResult) {
                int val = ((IIntegerResult) sRes).getValue();
                avg += val;
            } else {
                System.out.println("Illegal type for operation avg: " + sRes);
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
        queryTreeRoot.accept(this);
        IAbstractQueryResult res = qres.pop();
        try {
            res = getSingleResult(res, false);
        } catch (TypeCoercionException e) {
            // NOP
        }
        return res;
    }
    
    private ISingleResult getSingleResult(final IAbstractQueryResult aResult, boolean resolveStruct) throws TypeCoercionException {
        if (aResult instanceof ISingleResult) {
            if (aResult instanceof IStructResult) {
                List<ISingleResult> list = ((IStructResult) aResult).elements();
                if (list.size() != 1) {
                    throw new TypeCoercionException();
                } else {
                    ISingleResult sRes = list.get(0);
                    if (resolveStruct && sRes instanceof IStructResult) {
                        sRes = getSingleResult(sRes, true);
                    }
                    return sRes;
                }
            } else {
                return (ISingleResult) aResult;
            }
        } else {
          if (aResult instanceof IBagResult) {
              Collection<ISingleResult> collection = ((IBagResult) aResult).getElements();
              if (collection.size() != 1) {
                  throw new TypeCoercionException();
              } else {
                  ISingleResult sRes = (ISingleResult) collection.toArray()[0];
                  if (resolveStruct && sRes instanceof IStructResult) {
                      sRes = getSingleResult(sRes, true);
                  }
                  return sRes;
              }
          } else if (aResult instanceof ISequenceResult) {
              List<ISingleResult> list = ((ISequenceResult) aResult).getElements();
              if (list.size() != 1) {
                  throw new TypeCoercionException();
              } else {
                  ISingleResult sRes = list.get(0);
                  if (resolveStruct && sRes instanceof IStructResult) {
                      sRes = getSingleResult(sRes, true);
                  }
                  return sRes;
              }
          }
        }
        throw new TypeCoercionException();
    }
    
    private List<ISingleResult> getSingleResultList(final IAbstractQueryResult result, boolean resolveStruct) {
        List<ISingleResult> resList = new ArrayList<ISingleResult>();
        List<ISingleResult> tmpList = new ArrayList<ISingleResult>();
        if (result instanceof ISingleResult) {
            if (result instanceof IStructResult) {
                tmpList.addAll(((IStructResult) result).elements());
                if (resolveStruct) {
                    for (ISingleResult sRes : tmpList) {
                        resList.addAll(getSingleResultList(sRes, true));
                    }
                } else {
                    resList.addAll(tmpList);
                }
            } else {
                resList.add((ISingleResult) result);
            }
        } else { // leftResult instanceof ICollectionResult
            if (result instanceof IBagResult) {
                tmpList.addAll(((IBagResult) result).getElements());
                if (resolveStruct) {
                    for (ISingleResult sRes : tmpList) {
                        resList.addAll(getSingleResultList(sRes, true));
                    }
                } else {
                    resList.addAll(tmpList);
                }
            } else { // leftResult instanceof ISequenceResult
                tmpList.addAll(((ISequenceResult) result).getElements());
                if (resolveStruct) {
                    for (ISingleResult sRes : tmpList) {
                        resList.addAll(getSingleResultList(sRes, true));
                    }
                } else {
                    resList.addAll(tmpList);
                }
            }
        }
        return resList;
    }
    
    @SuppressWarnings("rawtypes")
    private String getStringValue(final ISingleResult result) {
        if (result instanceof IStringResult) {
            return ((IStringResult) result).getValue();
        } else if (result instanceof ISimpleResult) {
            return ((ISimpleResult) result).getValue().toString();
        } else if (result instanceof IStructResult) {
            try {
                return getStringValue(getSingleResult(result, true));
            } catch (TypeCoercionException e) {
                return "[TypeCoercionException in getStringValue]";
            }
        } else if (result instanceof IReferenceResult) {
            IOID oid = ((IReferenceResult) result).getOIDValue();
            return oid.toString();
        } else { // result instanceof IBinderResult
            IBinderResult binder = (IBinderResult) result;
            try {
                return getStringValue(getSingleResult(binder.getValue(), true)) + " as " + binder.getName();
            } catch (TypeCoercionException e) {
                return "[TypeCoercionException in getStringValue]";
            }
        }
    }
    
    private IAbstractQueryResult makeDerefenceIfReference(IAbstractQueryResult rRef) {
        ISingleResult tmpRes = null;
        try {
            tmpRes = getSingleResult(rRef, true);
        } catch (TypeCoercionException e) {
            return rRef;
        }
        if (tmpRes instanceof IReferenceResult) {
            IOID oid = ((IReferenceResult) tmpRes).getOIDValue();
            ISBAObject obj = store.retrieve(oid);
            return convert(obj);
        } else {
            return tmpRes;
        }
    }
    
    private ISingleResult convert(ISBAObject obj) {
        if (obj instanceof IBooleanObject) {
            return new BooleanResult(((IBooleanObject) obj).getValue());
        } else if (obj instanceof IDoubleObject){
            return new DoubleResult(((IDoubleObject) obj).getValue());
        } else if (obj instanceof IIntegerObject) {
            return new IntegerResult(((IIntegerObject) obj).getValue());
        } else if (obj instanceof IStringObject) {
            return new StringResult(((IStringObject) obj).getValue());
        } else { // obj instanceof IComplexObject
            return new ReferenceResult(obj.getOID());
        }
    }
    
    public static class TypeCoercionException extends Exception {
        private static final long serialVersionUID = -378579275226248409L;
    }

}
