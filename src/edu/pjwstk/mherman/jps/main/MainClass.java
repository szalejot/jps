package edu.pjwstk.mherman.jps.main;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.jps.result.IAbstractQueryResult;
import edu.pjwstk.mherman.jps.ast.auxname.AsExpression;
import edu.pjwstk.mherman.jps.ast.binary.AndExpression;
import edu.pjwstk.mherman.jps.ast.binary.CommaExpression;
import edu.pjwstk.mherman.jps.ast.binary.DotExpression;
import edu.pjwstk.mherman.jps.ast.binary.ForAnyExpression;
import edu.pjwstk.mherman.jps.ast.binary.JoinExpression;
import edu.pjwstk.mherman.jps.ast.binary.PlusExpression;
import edu.pjwstk.mherman.jps.ast.terminal.BooleanTerminal;
import edu.pjwstk.mherman.jps.ast.terminal.DoubleTerminal;
import edu.pjwstk.mherman.jps.ast.terminal.IntegerTerminal;
import edu.pjwstk.mherman.jps.ast.terminal.NameTerminal;
import edu.pjwstk.mherman.jps.ast.unary.BagExpression;
import edu.pjwstk.mherman.jps.ast.unary.MaxExpression;
import edu.pjwstk.mherman.jps.ast.unary.SumExpression;
import edu.pjwstk.mherman.jps.datastore.SBAStore;
import edu.pjwstk.mherman.jps.envs.ENVS;
import edu.pjwstk.mherman.jps.envs.Interpreter;
import edu.pjwstk.mherman.jps.interpreter.qres.QResStack;

public class MainClass {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// mini-projekt 4
		SBAStore store = new SBAStore();
		store.loadXML("../res/test.xml");
		System.out.println("Loaded store:");
		store.printStoreContent();
		
		// Query: 2 + 2
		{
		    ENVS envs = new ENVS();
		    envs.init(store.getEntryOID(), store);
		    QResStack qres = new QResStack();
		    IExpression ex = new PlusExpression(
		                        new IntegerTerminal(2),
		                        new IntegerTerminal(2));
		    Interpreter interpreter = new Interpreter(store, envs, qres);
		    IAbstractQueryResult res = interpreter.eval(ex);
		    System.out.println("-----------------");
		    System.out.println("Query: 2 + 2");
		    System.out.println("Result: " + res.toString());
		}
		
		// Query: true and false
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new AndExpression(
                                new BooleanTerminal(true), 
                                new BooleanTerminal(false));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: true and false");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: 1 as liczba
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new AsExpression(
                                new IntegerTerminal(1), 
                                "liczba");
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: 1 as liczba");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: pomidor
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new NameTerminal("pomidor");
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: pomidor");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: bag(1+2,3)
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new BagExpression(
                                new CommaExpression(
                                        new PlusExpression(
                                                new IntegerTerminal(1),
                                                new IntegerTerminal(2)),
                                        new IntegerTerminal(3)));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: bag(1+2,3)");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: max (bag(1,3.35,3))
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new MaxExpression(
                                new BagExpression(
                                        new CommaExpression(
                                                new CommaExpression(
                                                        new IntegerTerminal(1),
                                                        new DoubleTerminal(3.35)),
                                                new IntegerTerminal(3))));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: max (bag(1,3.35,3))");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: sum (bag(1.01,2.35,3))
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new SumExpression(
                                new BagExpression(
                                        new CommaExpression(
                                                new DoubleTerminal(1.01),
                                                new CommaExpression(
                                                        new DoubleTerminal(2.35),
                                                        new IntegerTerminal(3)))));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: sum (bag(1.01,2.35,3))");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: 1 join 2
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new JoinExpression(
                                new IntegerTerminal(1),
                                new IntegerTerminal(2));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: 1 join 2");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: any emp married
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new ForAnyExpression(
                                new NameTerminal("emp"),
                                new NameTerminal("married"));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: any emp married");
            System.out.println("Result: " + res.toString());
        }
        
        // Query: emp.book.author
        {
            ENVS envs = new ENVS();
            envs.init(store.getEntryOID(), store);
            QResStack qres = new QResStack();
            IExpression ex = new DotExpression(
                                new NameTerminal("emp"),
                                new DotExpression(
                                        new NameTerminal("book"),
                                        new NameTerminal("author")));
            Interpreter interpreter = new Interpreter(store, envs, qres);
            IAbstractQueryResult res = interpreter.eval(ex);
            System.out.println("-----------------");
            System.out.println("Query: emp.book.author");
            System.out.println("Result: " + res.toString());
        }
	}

}
