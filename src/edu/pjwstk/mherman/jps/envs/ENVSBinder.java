package edu.pjwstk.mherman.jps.envs;

import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.result.IAbstractQueryResult;

public class ENVSBinder implements IENVSBinder {

    private String name;
    private IAbstractQueryResult value;
    
    public ENVSBinder(String name, IAbstractQueryResult value) {
        this.name = name;
        this.value = value;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public IAbstractQueryResult getValue() {
        return value;
    }

}
