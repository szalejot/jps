package edu.pjwstk.mherman.jps.datastore;

import edu.pjwstk.jps.datastore.IDoubleObject;
import edu.pjwstk.jps.datastore.IOID;

public class DoubleObject implements IDoubleObject {
	
	private Double value;
	private String name;
	private IOID oid;
	
	public DoubleObject(Double value, String name, IOID oid) {
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

    @Override
    public String toString() {
        return "<" + ((OID) oid).getId() + ", " + name + ", " + value + ">";
    }	

}
