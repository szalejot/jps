package edu.pjwstk.mherman.jps.datastore;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
        if (getEntryOID() == null) {
            System.out.println("ERROR: Xml have to be loaded before adding java objects to store.");
            return;
        }
        addJavaObject(o, objectName, true);
    }

	@SuppressWarnings("rawtypes")
    private OID addJavaObject(Object o, String objectName, boolean isRoot) {    
	    ISBAObject object = null;
	    OID oid = generateUniqueOID();
	    //is it simple object?
	    if (o instanceof Integer) {
	        object = new IntegerObject((Integer) o, objectName, oid);
	    } else if (o instanceof Double) {
	        object = new DoubleObject((Double) o, objectName, oid);
	    } else if (o instanceof Boolean) {
	        object = new BooleanObject((Boolean) o, objectName, oid);
	    } else if (o instanceof String) {
	        object = new StringObject((String) o, objectName, oid);
	    } else if (o.getClass().isEnum()) {
	        object = new StringObject(((Enum) o).name(), objectName, oid);
	    } else { //complex object
	        List<IOID> list = new ArrayList<IOID>();
	        List<Field> fieldList = new ArrayList<Field>();
	        Class tmpClass = o.getClass();
	        while (tmpClass != null) {
	            fieldList.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
	            tmpClass = tmpClass.getSuperclass();
	        }
    	    for (Field f : fieldList) {
    	        f.setAccessible(true);
    	        Object fContent = null;
                try {
                    fContent = f.get(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
    	        IOID retOid;
    	        if (fContent instanceof Collection) {
    	            retOid = addJavaCollection((Collection) fContent, f.getName(), false);
    	        } else {
    	            retOid = addJavaObject(fContent, f.getName(), false);
    	        }
                if (retOid != null) {
                    list.add(retOid);
                }
    	    }
    	    object = new ComplexObject(list, objectName, oid);
	    }
	    store.put(oid, object);
	    if (isRoot) {
	        ComplexObject root = (ComplexObject) retrieve(getEntryOID());
	        root.getChildOIDs().add(oid);
	    }
	    return oid;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addJavaCollection(Collection o, String collectionName) {
        if (getEntryOID() == null) {
            System.out.println("ERROR: Xml have to be loaded before adding java collections to store.");
            return;
        }
		addJavaCollection(o, collectionName, true);
	}
	
	@SuppressWarnings("rawtypes")
    private OID addJavaCollection(Collection o, String collectionName, boolean isRoot) {
        OID oid = generateUniqueOID();
        List<IOID> list = new ArrayList<IOID>();
        for (Object obj : o) {
            IOID retOid;
            if (obj instanceof Collection) {
                retOid = addJavaCollection((Collection) obj, collectionName + ".member", false);
            } else {
                retOid = addJavaObject(obj, collectionName + ".member", false);
            }
            if (retOid != null) {
                list.add(retOid);
            }
        }
        ISBAObject object = new ComplexObject(list, collectionName, oid);
        store.put(oid, object);
        if (isRoot) {
            ComplexObject root = (ComplexObject) retrieve(getEntryOID());
            root.getChildOIDs().add(oid);
        }
        return oid;
    }
	
	private IOID processNode(Node node) throws Exception {
		if (node == null) {
			return null;
		}
		OID oid = null;
		String name = node.getNodeName();
		ISBAObject object = null;
		switch (node.getNodeType()) {
		case Node.ELEMENT_NODE:
		    oid = generateUniqueOID();
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
		    //natrafilismy na xmlowe smieci (pewnie '\n\t' albo cos w tym rodzaju)
		    return null;
		case Node.DOCUMENT_NODE:
			NodeList nodes = node.getChildNodes();
			if (nodes != null) {
			    return processNode(nodes.item(0));
			}
            break;
        default:
            throw new Exception("Not supported XML node type: " + node.getNodeType());
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
