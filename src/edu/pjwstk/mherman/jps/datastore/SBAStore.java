package edu.pjwstk.mherman.jps.datastore;

import java.util.Collection;

import edu.pjwstk.jps.datastore.IOID;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;

public class SBAStore implements ISBAStore {
	
	private static Long actualOID = 0L;
	
	@Override
	public ISBAObject retrieve(IOID oid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IOID getEntryOID() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void loadXML(String filePath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IOID generateUniqueOID() {
		return new OID(actualOID++);
	}

	@Override
	public void addJavaObject(Object o, String objectName) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addJavaCollection(Collection o, String collectionName) {
		throw new UnsupportedOperationException();
	}

}
