package edu.pjwstk.mherman.jps.envs;

import java.util.Collection;

import edu.pjwstk.jps.interpreter.envs.IENVSBinder;
import edu.pjwstk.jps.interpreter.envs.IENVSFrame;

public class ENVSFrame implements IENVSFrame {

    private Collection<IENVSBinder> elements;
    
    public ENVSFrame(Collection<IENVSBinder> elements) {
        this.elements = elements;
    }
    
    @Override
    public Collection<IENVSBinder> getElements() {
        return elements;
    }

}
