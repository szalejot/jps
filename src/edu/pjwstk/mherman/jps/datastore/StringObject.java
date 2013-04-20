package edu.pjwstk.mherman.jps.datastore;

import edu.pjwstk.jps.datastore.IOID;
import edu.pjwstk.jps.datastore.IStringObject;

public class StringObject implements IStringObject {

	private String value;
	private String name;
	private OID oid;
	
	public StringObject(String value, String name, OID oid) {
		this.value = value;
		this.name = name;
		this.oid = oid;
	}

	@Override
	public String getValue() {
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
