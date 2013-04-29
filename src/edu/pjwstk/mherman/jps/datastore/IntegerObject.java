package edu.pjwstk.mherman.jps.datastore;

import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.IOID;

public class IntegerObject implements IIntegerObject {

	private Integer value;
	private String name;
	private IOID oid;
	
	public IntegerObject(Integer value, String name, IOID oid) {
		this.value = value;
		this.name = name;
		this.oid = oid;
	}

	@Override
	public Integer getValue() {
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
