package edu.pjwstk.mherman.jps.result;

import java.util.Collection;

import edu.pjwstk.jps.result.IBagResult;
import edu.pjwstk.jps.result.ISingleResult;

public class BagResult implements IBagResult {

	private Collection<ISingleResult> elements;
	
	public BagResult(Collection<ISingleResult> elements) {
		this.elements = elements;
	}
	
	@Override
	public Collection<ISingleResult> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "BagResult [elements=" + elements + "]";
	}

}
