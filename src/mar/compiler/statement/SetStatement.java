package mar.compiler.statement;

import java.io.PrintStream;

import mar.compiler.expression.ConstantExpression;
import mar.compiler.expression.Expression;
import mar.compiler.parser.Method;
import mar.compiler.token.Word;

/**
 * <p>
 * 	The {@code SetStatement} sets a local value to something.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class SetStatement implements IStatement {

	private Word id;
	private Expression expr;

	/**
	 * <p>
	 * 	Constructs a new {@code SetStatement}.
	 * </p>
	 * 
	 * @param id The id.
	 * @param expr The expression.
	 */
	public SetStatement(Word id, Expression expr) {
		this.id = id;
		this.expr = expr;
	}

	@Override
	public void parse(Method m, PrintStream out) {
		if(this.expr instanceof ConstantExpression) {
			out.println("\tmov [BP - " + m.getLocalPosition(this.id) + "], " + ((ConstantExpression) this.expr).getValue());
		} else {
			this.expr.generate(m, out);
			out.println("\tmov [BP - " + m.getLocalPosition(this.id) + "], Y");
		}
	}

}
