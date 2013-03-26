package edu.pjwstk.mherman.jps.result;

import edu.pjwstk.jps.result.IIntegerResult;

public class IntegerResult implements IIntegerResult {

	private Integer value;
	
	public IntegerResult(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "IntegerResult [value=" + value + "]";
	}

}
