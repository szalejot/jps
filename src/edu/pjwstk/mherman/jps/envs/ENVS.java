package edu.pjwstk.mherman.jps.envs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import edu.pjwstk.jps.datastore.IOID;
import edu.pjwstk.jps.datastore.ISBAStore;
import edu.pjwstk.jps.interpreter.envs.IENVS;
import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISimpleResult;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.mherman.jps.result.BagResult;
import edu.pjwstk.mherman.jps.result.BinderResult;
import edu.pjwstk.mherman.jps.result.ReferenceResult;
import edu.pjwstk.mherman.jps.result.StructResult;

public class ENVS implements IENVS {

    private Vector<IENVSFrame> envs = new Vector<IENVSFrame>();
    
    @Override
    public void init(IOID rootOID, ISBAStore store) {
        this.push(this.nested(new ReferenceResult(rootOID), store));
    }

    @Override
    public IENVSFrame pop() {
        return envs.remove(envs.size() - 1);
    }

    @Override
    public void push(IENVSFrame frame) {
        envs.addElement(frame);
    }

    @Override
    public IBagResult bind(String name) {
        List<ReferenceResult> tmpList = new ArrayList<ReferenceResult>();
        for (int i = envs.size(); i >=0; i--) {
            Collection<IENVSBinder> col = envs.get(i).getElements();
            for (IENVSBinder binder : col) {
                if (binder.getName().equals(name)) {
                    tmpList.add(new ReferenceResult()) //???
                }
            }
        }
    }

    @Override
    public IENVSFrame nested(IAbstractQueryResult result, ISBAStore store) {
        if (result instanceof ISimpleResult) {
            return new ENVSFrame(null);
        } else if (result instanceof BinderResult) {
            return new ENVSFrame(Arrays.asList(new IENVSBinder[] { new ENVSBinder(((BinderResult) result).getName(),
                    ((BinderResult) result).getValue()) }));
        } else if (result instanceof StructResult) {
            List<IENVSBinder> tmpList = new ArrayList<IENVSBinder>();
            for (ISingleResult singleResult : ((StructResult) result).elements()) {
                ENVSFrame tmpFrame = (ENVSFrame) this.nested(singleResult, store);
                if (tmpFrame.getElements() != null) {
                    tmpList.addAll(tmpFrame.getElements());
                }
            }
            if (tmpList.isEmpty()) {
                return new ENVSFrame(null);
            } else {
                return new ENVSFrame(tmpList);
            }
        } else if (result instanceof BagResult) {
            List<IENVSBinder> tmpList = new ArrayList<IENVSBinder>();
            for (ISingleResult singleResult : ((BagResult) result).getElements()) {
                ENVSFrame tmpFrame = (ENVSFrame) this.nested(singleResult, store);
                if (tmpFrame.getElements() != null) {
                    tmpList.addAll(tmpFrame.getElements());
                }
            }
            if (tmpList.isEmpty()) {
                return new ENVSFrame(null);
            } else {
                return new ENVSFrame(tmpList);
            }
        } else if (result instanceof ReferenceResult) {
            //TODO
            return new ENVSFrame(null);
        } else {
            return new ENVSFrame(null);
        }
    }

}
