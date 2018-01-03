package mar.compiler.expression;

import java.io.PrintStream;

import mar.compiler.parser.Method;
import mar.compiler.token.Word;

/**
 * <p>A {@code VariableExpression} is a variable within a method.</p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class VariableExpression extends Expression {
	
	private Word id;
	private Method method;
	
	/**
	 * <p>
	 * 	The {@code VariableExpression} is used to handle variables.
	 * </p>
	 * 
	 * @param id The id of the variable.
	 * @param m The method.
	 */
	public VariableExpression(Word id, Method m) {
		this.id = id;
		this.method = m;
	}

	@Override
	public void generate(Method m, PrintStream ps) {
		
	}
	
	public Word getId() {
		return this.id;
	}
	
}
