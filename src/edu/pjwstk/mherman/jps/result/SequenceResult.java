package edu.pjwstk.mherman.jps.result;

import java.util.List;

import edu.pjwstk.jps.result.ISequenceResult;
import edu.pjwstk.jps.result.ISingleResult;

public class SequenceResult implements ISequenceResult {

	private List<ISingleResult> elements;
	
	public SequenceResult(List<ISingleResult> elements) {
		this.elements = elements;
	}
	
	@Override
	public List<ISingleResult> getElements() {
		return elements;
	}

	@Override
	public String toString() {
		return "SequenceResult [elements=" + elements + "]";
	}

}
