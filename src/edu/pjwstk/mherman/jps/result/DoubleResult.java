package edu.pjwstk.mherman.jps.result;

import edu.pjwstk.jps.result.IDoubleResult;

public class DoubleResult implements IDoubleResult {

	private Double value;
	
	public DoubleResult(Double value) {
		this.value = value;
	}
	
	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "DoubleResult [value=" + value + "]";
	}

}
