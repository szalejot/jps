package edu.pjwstk.mherman.jps.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pjwstk.jps.datastore.IBooleanObject;
import edu.pjwstk.jps.datastore.IIntegerObject;
import edu.pjwstk.jps.datastore.ISBAObject;
import edu.pjwstk.jps.datastore.IStringObject;
import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.mherman.jps.datastore.SBAStore;
import edu.pjwstk.mherman.jps.envs.ENVS;
import edu.pjwstk.mherman.jps.interpreter.qres.QResStack;
import edu.pjwstk.mherman.jps.result.BagResult;
import edu.pjwstk.mherman.jps.result.ReferenceResult;
import edu.pjwstk.mherman.jps.result.StringResult;

public class MainClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// mini-projekt 3
		SBAStore store = new SBAStore();
		store.loadXML("../res/test.xml");
		System.out.println("Loaded store:");
		store.printStoreContent();

		//((emp where married) union (emp where lName="Nowak")).fName
		{
		    ENVS envs = new ENVS();
		    QResStack qres = new QResStack();
		    envs.init(store.getEntryOID(), store);
		    
		    //dot
		    //union
		    //where
		    BagResult empBag = (BagResult) envs.bind("emp");
		    qres.push(empBag);
		    empBag = (BagResult) qres.pop();
		    List<ISingleResult> whereList = new ArrayList<ISingleResult>();
		    for (ISingleResult sResult : empBag.getElements()) {
		        envs.push(envs.nested(sResult, store));
		        BagResult whereBindBag = (BagResult) envs.bind("married");
		        qres.push(whereBindBag);
		        whereBindBag = (BagResult) qres.pop();
		        // if we have one boolean result that value is true
		        if (whereBindBag.getElements().size() == 1) {
		            ReferenceResult refRes = (ReferenceResult) whereBindBag.getElements().toArray()[0];
		            ISBAObject obj = store.retrieve(refRes.getOIDValue());
		            if (obj instanceof IBooleanObject
		                    && ((IBooleanObject) obj).getValue()) {
		                whereList.add(sResult);
		            }
		        }
		        envs.pop();
		    }
		    BagResult whereRes = new BagResult(whereList);
		    qres.push(whereRes);
		    //end where
		    //where
		    empBag = (BagResult) envs.bind("emp");
            qres.push(empBag);
            empBag = (BagResult) qres.pop();
            whereList = new ArrayList<ISingleResult>();
            for (ISingleResult sResult : empBag.getElements()) {
                envs.push(envs.nested(sResult, store));
                BagResult whereBindBag = (BagResult) envs.bind("lName");
                qres.push(whereBindBag);
                whereBindBag = (BagResult) qres.pop();
                // if we have one String result that value is "Nowak"
                if (whereBindBag.getElements().size() == 1) {
                    ReferenceResult refRes = (ReferenceResult) whereBindBag.getElements().toArray()[0];
                    ISBAObject obj = store.retrieve(refRes.getOIDValue());
                    if (obj instanceof IStringObject
                            && ((IStringObject) obj).getValue().equals("Nowak")) {
                        whereList.add(sResult);
                    }
                }
                envs.pop();
            }
            whereRes = new BagResult(whereList);
            qres.push(whereRes);
		    //end where
            List<ISingleResult> unionList = new ArrayList<ISingleResult>();
            BagResult unionBag = (BagResult) qres.pop();
            unionList.addAll(unionBag.getElements());
            unionBag = (BagResult) qres.pop();
            unionList.addAll(unionBag.getElements());
            qres.push(new BagResult(unionList));
		    //end union
            BagResult dotBag = (BagResult) qres.pop();
            List<ISingleResult> dotList = new ArrayList<ISingleResult>();
            for (ISingleResult sResult : dotBag.getElements()) {
                envs.push(envs.nested(sResult, store));
                BagResult dotBindBag = (BagResult) envs.bind("fName");
                qres.push(dotBindBag);
                dotBindBag = (BagResult) qres.pop();
                dotList.addAll(dotBindBag.getElements());
                envs.pop();
            }
            qres.push(new BagResult(dotList));
            //end dot
            System.out.println("-----");
            System.out.println("Query: ((emp where married) union (emp where lName=\"Nowak\")).fName");
            System.out.println("Result: " + ((BagResult) qres.pop()).toString());
		}
		
		// ((emp where exists address) where address.number>20).(street, city)
		{
		    ENVS envs = new ENVS();
            QResStack qres = new QResStack();
            envs.init(store.getEntryOID(), store);
            
            //dot
            //outer where
            //inner where
            BagResult empBag = (BagResult) envs.bind("emp");
            qres.push(empBag);
            empBag = (BagResult) qres.pop();
            List<ISingleResult> whereList = new ArrayList<ISingleResult>();
            for (ISingleResult sResult : empBag.getElements()) {
                envs.push(envs.nested(sResult, store));
                //exists
                BagResult existsBindBag = (BagResult) envs.bind("address");
                qres.push(existsBindBag);
                existsBindBag = (BagResult) qres.pop();
                if (existsBindBag.getElements().size() >= 1) {
                    whereList.add(sResult);
                }
                //end exists
                envs.pop();
            }
            BagResult whereRes = new BagResult(whereList);
            qres.push(whereRes);
            //end inner where
            empBag = (BagResult) qres.pop();
            whereList = new ArrayList<ISingleResult>();
            for (ISingleResult sResult : empBag.getElements()) {
                envs.push(envs.nested(sResult, store));
                //comparison
                //dot
                BagResult dotBindBag = (BagResult) envs.bind("address");
                qres.push(dotBindBag);
                dotBindBag = (BagResult) qres.pop();
                List<ISingleResult> dotList = new ArrayList<ISingleResult>();
                for (ISingleResult dResult : dotBindBag.getElements()) {
                    envs.push(envs.nested(dResult, store));
                    BagResult dotInnerBindBag = (BagResult) envs.bind("number");
                    // if we have one Integer result 
                    if (dotInnerBindBag.getElements().size() == 1) {
                        ReferenceResult refRes = (ReferenceResult) dotInnerBindBag.getElements().toArray()[0];
                        ISBAObject obj = store.retrieve(refRes.getOIDValue());
                        if (obj instanceof IIntegerObject) {
                            dotList.add(refRes);
                        }
                    }
                    envs.pop();
                }
                qres.push(new BagResult(dotList));
                //end dot
                BagResult compBag = (BagResult) qres.pop();
                if (compBag.getElements().size() != 1) {
                    throw new Exception("Wrong number of elements to comparison");
                }
                ReferenceResult compRes = (ReferenceResult) compBag.getElements().toArray()[0];
                ISBAObject obj = store.retrieve(compRes.getOIDValue());
                if (!(obj instanceof IIntegerObject)) {
                    throw new Exception("Wrong result type to comparison");
                }
                if (((IIntegerObject) obj).getValue() > 20) {
                    whereList.add(sResult);
                }
                //end comparison
                envs.pop();
            }
            whereRes = new BagResult(whereList);
            qres.push(whereRes);
            //end outer where
            BagResult dotRes = (BagResult) qres.pop();
            List<ISingleResult> dotList = new ArrayList<ISingleResult>();
            for (ISingleResult sResult : dotRes.getElements()) {
                envs.push(envs.nested(sResult, store));
                //comma
                StringResult sr1 = new StringResult("street");
                StringResult sr2 = new StringResult("city");
                qres.push(new BagResult(Arrays.asList(new ISingleResult[]{sr1, sr2})));
                //end comma
                BagResult dotBag = (BagResult) qres.pop();
                for (ISingleResult dResult : dotBag.getElements()) {
                    //envs.push(envs.nested(dResult, store));
                    BagResult dotInnerBindBag = (BagResult) envs.bind(((StringResult) dResult).getValue());
                    // if we have one result 
                    if (dotInnerBindBag.getElements().size() == 1) {
                        ReferenceResult refRes = (ReferenceResult) dotInnerBindBag.getElements().toArray()[0];
                        dotList.add(refRes);
                    }
                    //envs.pop();
                }
                envs.pop();
            }
            qres.push(new BagResult(dotList));
            //end dot
            System.out.println("-----");
            System.out.println("Query: ((emp where exists address) where address.number>20).(street, city)");
            System.out.println("Result: " + ((BagResult) qres.pop()).toString());
		}
		System.out.println("--- End of program ---");
	}

}
