package mar.compiler.expression;

import java.io.PrintStream;

import mar.compiler.parser.Method;
import mar.compiler.token.Word;

/**
 * <p>
 * 	The {@code CallExpression} is an expression in which a value is
 * 	called from a block.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class CallExpression extends Expression {
	
	private Method calling;
	
	public CallExpression(Method calling) {
		this.calling = calling;
	}
	
	@Override
	public void generate(Method m, PrintStream ps) {
		ps.println("\tcall " + Method.getASMName(calling));
		ps.println("\tmov Y, X");
	}

}
