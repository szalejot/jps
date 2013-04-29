package edu.pjwstk.mherman.jps.datastore;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pjwstk.jps.datastore.IOID;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.ISBAStore;

public class SBAStore implements ISBAStore {
	
	private static Long actualOID = 0L;
	private Map<IOID, ISBAObject> store = new HashMap<IOID, ISBAObject>();
	private IOID entryOID = null; 
	
	@Override
	public ISBAObject retrieve(IOID oid) {
		return store.get(oid);
	}

	@Override
	public IOID getEntryOID() {
		return entryOID;
	}

	@Override
	public void loadXML(String filePath) {
		try {
			final DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        final DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        FileInputStream fis = new FileInputStream(filePath);
	        final Document doc = docBuilder.parse(fis);
	        store.clear();
	        entryOID = processNode(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public OID generateUniqueOID() {
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
	
	private IOID processNode(Node node) throws Exception {
		if (node == null) {
			return null;
		}
		OID oid = generateUniqueOID();
		String name = node.getNodeName();
		ISBAObject object = null;
		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE:
			NodeList nl = node.getChildNodes();
			if (nl != null) {
			    if (nl.getLength() == 1) { //mamy wartosc
			        String val = nl.item(0).getNodeValue();
			        if (isInteger(val)) {
			            object = new IntegerObject(Integer.parseInt(val), name, oid);
			        } else if (isDouble(val)) {
			            object = new DoubleObject(Double.parseDouble(val), name, oid);
			        } else if (isBoolean(val)) {
			            object = new BooleanObject(val.equalsIgnoreCase("true"), name, oid);
			        } else { //isString
			            object = new StringObject(val, name, oid);
			        }
			    } else {
			        List<IOID> list = new ArrayList<IOID>();
                    for (int i = 0; i < nl.getLength(); i++) {
                        IOID retOid = processNode(nl.item(i));
                        if (retOid != null) {
                            list.add(retOid);
                        }
                    }
                    object = new ComplexObject(list, name, oid);
			    }
			}
			break;
		case Node.TEXT_NODE:
		    //natrafilismy na xmlowe smieci (pewnie '\n\t' albo cos w tym rodzaju)s
		    return null;
		case Node.DOCUMENT_NODE:
			NodeList nodes = node.getChildNodes();
			if (nodes != null) {
			    return processNode(nodes.item(0));
			}
            break;
        default:
            throw new Exception("Not supported node type: " + node.getNodeType());
        }
		store.put(oid, object);
		return oid;
    }
	
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
    
    private static boolean isBoolean(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
    
    public void printStoreContent() {
        System.out.println("entryOID = " + ((OID) entryOID).getId());
        for(Map.Entry<IOID, ISBAObject> entry : store.entrySet()) {
            System.out.println(entry.getValue().toString());
        }
    }

}
