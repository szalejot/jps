package edu.pjwstk.mherman.jps.result;

import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.jps.result.IBinderResult;

public class BinderResult implements IBinderResult {

	private String name;
	private IAbstractQueryResult value;
	
	public BinderResult(String name, IAbstractQueryResult value) {
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

	@Override
	public String toString() {
		return "BinderResult [name=" + name + ", value=" + value + "]";
	}

}
