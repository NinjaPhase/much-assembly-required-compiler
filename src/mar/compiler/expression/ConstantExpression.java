package mar.compiler.expression;

import java.io.PrintStream;

import mar.compiler.parser.Method;
import mar.compiler.token.NumValue;
import mar.compiler.token.Token;

/**
 * <p>
 * 	A {@code ConstantExpression} is an expression in which a constant is
 * 	being evaluated.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class ConstantExpression extends Expression {
	
	private Token t;
	
	/**
	 * <p>
	 * 	Constructs a new {@code ConstantExpression}.
	 * </p>
	 * 
	 * @param t The token.
	 */
	public ConstantExpression(Token t) {
		this.t = t;
	}
	
	@Override
	public void generate(Method m, PrintStream ps) {
		ps.println("\tmov Y, " + ((NumValue) t).getValue());
	}

	public int getValue() {
		return ((NumValue) t).getValue();
	}
	
}
