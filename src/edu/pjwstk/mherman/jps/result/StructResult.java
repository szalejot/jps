package edu.pjwstk.mherman.jps.result;

import java.util.List;

import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.jps.result.IStructResult;

public class StructResult implements IStructResult {

	private List<ISingleResult> elements;

	public StructResult(List<ISingleResult> elements) {
		this.elements = elements;
	}

	@Override
	public List<ISingleResult> elements() {
		return elements;
	}

}
