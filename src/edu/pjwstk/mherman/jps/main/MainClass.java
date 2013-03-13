package edu.pjwstk.mherman.jps.main;

import edu.pjwstk.jps.ast.IExpression;
import edu.pjwstk.mherman.jps.ast.binary.CommaExpression;
import edu.pjwstk.mherman.jps.ast.binary.DotExpression;
import edu.pjwstk.mherman.jps.ast.binary.EqualsExpression;
import edu.pjwstk.mherman.jps.ast.binary.GreaterThanExpression;
import edu.pjwstk.mherman.jps.ast.binary.InExpression;
import edu.pjwstk.mherman.jps.ast.binary.MinusExpression;
import edu.pjwstk.mherman.jps.ast.binary.PlusExpression;
import edu.pjwstk.mherman.jps.ast.binary.WhereExpression;
import edu.pjwstk.mherman.jps.ast.terminal.DoubleTerminal;
import edu.pjwstk.mherman.jps.ast.terminal.IntegerTerminal;
import edu.pjwstk.mherman.jps.ast.terminal.NameTerminal;
import edu.pjwstk.mherman.jps.ast.terminal.StringTerminal;
import edu.pjwstk.mherman.jps.ast.unary.AvgExpression;
import edu.pjwstk.mherman.jps.ast.unary.BagExpression;

public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// Firma where (avg(zatrudnia.pensja) > 2550.50)
		IExpression ex1 = new WhereExpression(
							new NameTerminal("Firma"),
							new GreaterThanExpression(
								new AvgExpression(
									new DotExpression(
										new NameTerminal("zatrudnia"),
										new NameTerminal("pensja")
									)
								),
								new DoubleTerminal(2550.5)
							)
						);
		
		// Pracownik where (adres.miasto in (bag(„Warszawa”, „Łódź”)))
		IExpression ex2 = new WhereExpression(
							new NameTerminal("Pracownik"),
							new InExpression(
								new DotExpression(
									new NameTerminal("adres"),
									new NameTerminal("miasto")
								),
								new BagExpression(
									new CommaExpression(
										new StringTerminal("Warszawa"),
										new StringTerminal("Łódź")
									)
								)
							)
						);
		
		// bag(1,2+1) in bag(4-1,3-2)
		IExpression ex3 = new InExpression(
							new BagExpression(
								new CommaExpression(
									new IntegerTerminal(1),
									new PlusExpression(
										new IntegerTerminal(2),
										new IntegerTerminal(1)
									)
								)
							),
							new BagExpression(
								new CommaExpression(
									new MinusExpression(
										new IntegerTerminal(4),
										new IntegerTerminal(1)
									),
									new MinusExpression(
										new IntegerTerminal(3),
										new IntegerTerminal(2)
									)
								)
							)
						);
		
		// (Pracownik where nazwisko=”Kowalski”).(adres where miasto=”Łódź”)
		IExpression ex4 = new DotExpression(
							new WhereExpression(
								new NameTerminal("Pracownik"),
								new EqualsExpression(
									new NameTerminal("nazwisko"),
									new StringTerminal("Kowalski")
								)
							),
							new WhereExpression(
								new NameTerminal("adres"),
								new EqualsExpression(
									new NameTerminal("miasto"),
									new StringTerminal("Łódź")
								)
							)
						);

	}

}
