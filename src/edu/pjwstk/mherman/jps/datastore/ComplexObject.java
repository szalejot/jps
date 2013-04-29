package edu.pjwstk.mherman.jps.datastore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.pjwstk.jps.datastore.IComplexObject;
import edu.pjwstk.jps.datastore.IOID;

public class ComplexObject implements IComplexObject {

	private List<IOID> childOIDs;
	private String name;
	private OID oid;
	
	public ComplexObject(Collection<IOID> childOIDs, String name, OID oid) {
		super();
		this.childOIDs = new ArrayList<IOID>();
		this.childOIDs.addAll(childOIDs);
		this.name = name;
		this.oid = oid;
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
	public List<IOID> getChildOIDs() {
		return childOIDs;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<" + ((OID) oid).getId() + ", " + name + ", {");
        
        for (IOID oid : childOIDs) {
            sb.append(((OID) oid).getId() + ", ");
        }
        if (!childOIDs.isEmpty()) {
            //usun ostatni przecinek
            sb.delete(sb.length()-2, sb.length());
        }
        sb.append("}>");
        return  sb.toString();
    }
	
}
