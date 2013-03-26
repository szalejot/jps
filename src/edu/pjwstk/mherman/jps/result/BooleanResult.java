package edu.pjwstk.mherman.jps.result;

import edu.pjwstk.jps.result.IBooleanResult;

public class BooleanResult implements IBooleanResult {

	private Boolean value;
	
	public BooleanResult(Boolean value) {
		this.value = value;
	}
	
	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "BooleanResult [value=" + value + "]";
	}

}
