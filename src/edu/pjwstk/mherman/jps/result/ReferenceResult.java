package edu.pjwstk.mherman.jps.result;

import edu.pjwstk.jps.datastore.IOID;
import edu.pjwstk.jps.result.IReferenceResult;

public class ReferenceResult implements IReferenceResult {

	private IOID value;
	
	public ReferenceResult(IOID value) {
		this.value = value;
	}
	
	@Override
	public IOID getOIDValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ReferenceResult [value=" + value + "]";
	}

}
