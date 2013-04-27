package edu.pjwstk.mherman.jps.main;

import java.util.ArrayList;
import java.util.List;

import edu.pjwstk.jps.result.ISingleResult;
import edu.pjwstk.mherman.jps.datastore.SBAStore;
import edu.pjwstk.mherman.jps.interpreter.qres.QResStack;
import edu.pjwstk.mherman.jps.result.BagResult;
import edu.pjwstk.mherman.jps.result.BinderResult;
import edu.pjwstk.mherman.jps.result.BooleanResult;
import edu.pjwstk.mherman.jps.result.IntegerResult;
import edu.pjwstk.mherman.jps.result.StringResult;
import edu.pjwstk.mherman.jps.result.StructResult;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// mini-projekt 2
		{
			// (struct(1, 2+1), (bag("test", „Ala”) as nazwa));
			QResStack qres = new QResStack();
			// comma
			// struct
			// comma
			qres.push(new IntegerResult(1));
			qres.push(new IntegerResult(2));
			qres.push(new IntegerResult(1));
			IntegerResult plusRight = (IntegerResult) qres.pop();
			IntegerResult plusLeft = (IntegerResult) qres.pop();
			IntegerResult plusResult = new IntegerResult(plusLeft.getValue() + plusRight.getValue());
			qres.push(plusResult);
			ISingleResult comma1Right = (ISingleResult) qres.pop();
			ISingleResult comma1Left = (ISingleResult) qres.pop();
			List<ISingleResult> tmpList = new ArrayList<ISingleResult>();
			tmpList.add(comma1Left);
			tmpList.add(comma1Right);
			qres.push(new BagResult(tmpList));
			// end comma
			BagResult tmpBag = (BagResult) qres.pop();
			qres.push(new StructResult(new ArrayList<ISingleResult>(tmpBag.getElements())));
			// end struct
			//as
			//bag
			//comma
			qres.push(new StringResult("test"));
			qres.push(new StringResult("Ala"));
			ISingleResult comma2Right = (ISingleResult) qres.pop();
			ISingleResult comma2Left = (ISingleResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.add(comma2Left);
			tmpList.add(comma2Right);
			qres.push(new BagResult(tmpList));
			//end comma
			tmpBag = (BagResult) qres.pop();
			qres.push(new BagResult(new ArrayList<ISingleResult>(tmpBag.getElements())));
			//end bag
			BagResult tmpRes = (BagResult)qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			for (ISingleResult isr : tmpRes.getElements()) {
				tmpList.add(new BinderResult("nazwa", isr));
			}
			qres.push(new BagResult(tmpList));
			//end as
			BagResult comma3Right = (BagResult) qres.pop();
			StructResult comma3Left = (StructResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.addAll(comma3Left.elements());
			tmpList.addAll(comma3Right.getElements());
			qres.push(new BagResult(tmpList));
			// end comma
			System.out.println(qres.pop().toString());
		}
		
		{
			//(bag("ala"+" ma"+" kota"), bag(8*10, false));
			QResStack qres = new QResStack();
			//comma
			//bag
			//left plus
			qres.push(new StringResult("ala"));
			//right plus
			qres.push(new StringResult(" ma"));
			qres.push(new StringResult(" kota"));
			StringResult plusRight = (StringResult) qres.pop();
			StringResult plusLeft = (StringResult) qres.pop();
			qres.push(new StringResult(plusLeft.getValue() + plusRight.getValue()));
			//end right plus
			plusRight = (StringResult) qres.pop();
			plusLeft = (StringResult) qres.pop();
			qres.push(new StringResult(plusLeft.getValue() + plusRight.getValue()));
			//end left plus
			List<ISingleResult> tmpList = new ArrayList<ISingleResult>();
			tmpList.add((ISingleResult) qres.pop());
			qres.push(new BagResult(tmpList));
			//end bag
			//bag
			//comma
			//multiply
			qres.push(new IntegerResult(8));
			qres.push(new IntegerResult(10));
			IntegerResult multRight = (IntegerResult) qres.pop();
			IntegerResult multLeft = (IntegerResult) qres.pop();
			qres.push(new IntegerResult(multLeft.getValue() * multRight.getValue()));
			//end multiply
			qres.push(new BooleanResult(false));
			ISingleResult comma1Right = (ISingleResult) qres.pop();
			ISingleResult comma1Left = (ISingleResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.add(comma1Left);
			tmpList.add(comma1Right);
			qres.push(new BagResult(tmpList));
			//end comma
			BagResult tmpBag = (BagResult) qres.pop();
			qres.push(new BagResult(new ArrayList<ISingleResult>(tmpBag.getElements())));
			//end bag
			BagResult comma2Right = (BagResult) qres.pop();
			BagResult comma2Left = (BagResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.addAll(comma2Left.getElements());
			tmpList.addAll(comma2Right.getElements());
			qres.push(new BagResult(tmpList));
			//end comma
			System.out.println(qres.pop().toString());
		}
		
		{
			//(((bag(1, 2) groupas x), bag(3, 4), 5;
			QResStack qres = new QResStack();
			//comma
			//groupas
			//bag
			//comma
			qres.push(new IntegerResult(1));
			qres.push(new IntegerResult(2));
			ISingleResult comma1Right = (ISingleResult) qres.pop();
			ISingleResult comma1Left = (ISingleResult) qres.pop();
			ArrayList<ISingleResult> tmpList = new ArrayList<ISingleResult>();
			tmpList.add(comma1Left);
			tmpList.add(comma1Right);
			qres.push(new BagResult(tmpList));
			//end comma
			BagResult tmpBag = (BagResult) qres.pop();
			qres.push(new BagResult(new ArrayList<ISingleResult>(tmpBag.getElements())));
			//end bag
			tmpBag = (BagResult) qres.pop();
			qres.push(new BinderResult("x", tmpBag));
			//end groupas
			//right comma
			//bag
			//comma
			qres.push(new IntegerResult(3));
			qres.push(new IntegerResult(4));
			ISingleResult comma2Right = (ISingleResult) qres.pop();
			ISingleResult comma2Left = (ISingleResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.add(comma2Left);
			tmpList.add(comma2Right);
			qres.push(new BagResult(tmpList));
			//end comma
			tmpBag = (BagResult) qres.pop();
			qres.push(new BagResult(new ArrayList<ISingleResult>(tmpBag.getElements())));
			//end bag
			qres.push(new IntegerResult(5));
			ISingleResult comma3Right = (ISingleResult) qres.pop();
			BagResult comma3Left = (BagResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.addAll(comma3Left.getElements());
			tmpList.add(comma3Right);
			qres.push(new BagResult(tmpList));
			//end right comma
			BagResult comma4Right = (BagResult) qres.pop();
			BinderResult comma4Left = (BinderResult) qres.pop();
			tmpList = new ArrayList<ISingleResult>();
			tmpList.add(comma4Left);
			tmpList.addAll(comma4Right.getElements());
			qres.push(new BagResult(tmpList));
			//end comma
			System.out.println(qres.pop().toString());
		}
		
		
		SBAStore store = new SBAStore();
		store.loadXML("..\res\test.xml");

	}

}
