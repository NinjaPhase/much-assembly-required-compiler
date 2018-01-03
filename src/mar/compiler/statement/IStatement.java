package mar.compiler.statement;

import java.io.PrintStream;

import mar.compiler.parser.Method;

/**
 * <p>
 * 	A {@code IStatement} is a processable statement.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public interface IStatement {
	
	/**
	 * <p>
	 * 	Parses and generates the statement.
	 * </p>
	 * 
	 * @param m The method.
	 * @param out The printstream.
	 */
	public void parse(Method m, PrintStream out);
	
}
