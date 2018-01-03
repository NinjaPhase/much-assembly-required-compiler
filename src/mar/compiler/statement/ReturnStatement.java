package mar.compiler.statement;

import java.io.PrintStream;

import mar.compiler.expression.ConstantExpression;
import mar.compiler.expression.Expression;
import mar.compiler.expression.VariableExpression;
import mar.compiler.parser.Method;
import mar.compiler.token.Word;

public class ReturnStatement implements IStatement {
	
	private Expression exp;
	
	/**
	 * <p>
	 * 	A {@code ReturnStatement} is used to return a value.
	 * </p>
	 * 
	 * @param exp The expression.
	 */
	public ReturnStatement(Expression exp) {
		this.exp = exp;
	}
	
	@Override
	public void parse(Method m, PrintStream out) {
		if(this.exp instanceof ConstantExpression) {
			out.println("\tmov X, " + ((ConstantExpression) this.exp).getValue());
		} else if(this.exp instanceof VariableExpression) {
			Word id = ((VariableExpression) this.exp).getId();
			out.println("\tmov X, [BP - " + m.getLocalPosition(id) + "]");
		} else {
			this.exp.generate(m, out);
			out.println("\tmov X, Y");
		}
	}

}
