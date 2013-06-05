package edu.pjwstk.mherman.jps.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.mherman.jps.datastore.SBAStore;
import edu.pjwstk.mherman.jps.envs.ENVS;
import edu.pjwstk.mherman.jps.envs.Interpreter;
import edu.pjwstk.mherman.jps.interpreter.qres.QResStack;
import edu.pjwstk.mherman.jps.parser.SBAParser;

public class MainClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// mini-projekt 5
		SBAStore store = new SBAStore();
		store.loadXML("../res/test.xml");
		System.out.println("Loaded store:");
		store.printStoreContent();
		ENVS envs = new ENVS();
		envs.init(store.getEntryOID(), store);
		QResStack qres = new QResStack();
		Interpreter interpreter = new Interpreter(store, envs, qres);
		String exitSequence = "exit";
		String showStoreSequence = "showStore";
		String curLine = "";
		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader in = new BufferedReader(converter);
		
		do {
            System.out.println("---------------");
            System.out.println("Enter query (or '" + exitSequence + "' to exit, '" + showStoreSequence
                    + "' to show loaded store)");
            curLine = in.readLine();

		    if (showStoreSequence.equalsIgnoreCase(curLine)) {
		        System.out.println("Loaded store:");
		        store.printStoreContent();
		    } else if (exitSequence.equalsIgnoreCase(curLine)) {
		        System.out.println("exiting...");
		        break;
		    } else {
        		SBAParser parser = new SBAParser(curLine);
        		IExpression res = null;
        		try {
        		parser.user_init();
                parser.parse();
                res = parser.RESULT;
        		} catch (Exception ex) {
        		    System.out.println("Exception while parsing query, try again.");
        		    continue;
        		}
                
                res.accept(interpreter);
                System.out.println("Result: " + qres.pop());
		    }
		} while (true);
	}

}
