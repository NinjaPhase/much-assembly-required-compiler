package mar.compiler.expression;

import java.io.PrintStream;

import mar.compiler.parser.Method;

/**
 * <p>
 * 	An {@code Expression} is a piece of arithmetic or an assignment.
 * </p>
 * 
 * <p>
 * 	This will form expression trees to work out order of operations.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public abstract class Expression {

	/**
	 * <p>
	 * 	Generates a given expression.
	 * </p>
	 * 
	 * @param m The method.
	 * @param ps The prinstream.
	 */
	public abstract void generate(Method m, PrintStream ps);
	
}
