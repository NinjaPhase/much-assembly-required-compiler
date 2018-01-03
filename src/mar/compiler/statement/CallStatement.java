package mar.compiler.statement;

import java.io.PrintStream;

import mar.compiler.parser.Method;
import mar.compiler.token.NumValue;
import mar.compiler.token.Tag;
import mar.compiler.token.Token;
import mar.compiler.token.Word;

/**
 * <p>
 * 	The {@code CallStatement} is used to call a method from.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class CallStatement implements IStatement {
	
	private Method calling;
	private Object[] vars;
	
	/**
	 * <p>
	 * 	Constructs a new {@code CallStatement}.
	 * </p>
	 * 
	 * @param calling The calling method.
	 * @param vars 
	 */
	public CallStatement(Method calling, Object[] vars) {
		this.calling = calling;
		this.vars = vars;
	}
	
	@Override
	public void parse(Method m, PrintStream out) {
		for(Object o : vars) {
			if(o instanceof NumValue) {
				out.println("\tpush " + ((NumValue) o).getValue());
			} else if(o instanceof Word) {
				out.println("\tpush [BP - " + m.getLocalPosition((Word) o) + "]");
			}
		}
		out.println("\tcall " + Method.getASMName(this.calling));
	}
	
	
	
}
