package edu.pjwstk.mherman.jps.result;

import edu.pjwstk.jps.result.IStringResult;

public class StringResult implements IStringResult {

	private String value;
	
	public StringResult(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "StringResult [value=" + value + "]";
	}

}
