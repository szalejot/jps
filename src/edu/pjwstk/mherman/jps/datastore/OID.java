package edu.pjwstk.mherman.jps.datastore;

import edu.pjwstk.jps.datastore.IOID;

public class OID implements IOID, Comparable<OID> {
	private static final long serialVersionUID = 620323840230316378L;
	
	private Long id;
	
	public OID(Long id) {
		this.id = id;
	}
	
	public Long getId() {
	    return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OID other = (OID) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

    @Override
    public int compareTo(OID arg0) {
        if (this.equals(arg0)) {
            return 0;
        } else if (this.id < arg0.id) {
            return -1;
        } else {
            return 1;
        }
    }

}
