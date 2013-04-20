package edu.pjwstk.mherman.jps.datastore;

import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.IOID;

public class DoubleObject implements IDoubleObject {
	
	private Double value;
	private String name;
	private OID oid;
	
	public DoubleObject(Double value, String name, OID oid) {
		this.value = value;
		this.name = name;
		this.oid = oid;
	}
	
	@Override
	public Double getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IOID getOID() {
		return oid;
	}

}
