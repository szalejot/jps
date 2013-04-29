    package edu.pjwstk.mherman.jps.datastore;

import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.IOID;

public class BooleanObject implements IBooleanObject {

    private Boolean value;
	private String name;
	private IOID oid;
	
	public BooleanObject(Boolean value, String name, IOID oid) {
		this.value = value;
		this.name = name;
		this.oid = oid;
	}

	@Override
	public Boolean getValue() {
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
