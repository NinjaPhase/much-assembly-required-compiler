package mar.compiler.parser;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mar.compiler.exception.ParserException;
import mar.compiler.expression.CallExpression;
import mar.compiler.expression.ConstantExpression;
import mar.compiler.expression.Expression;
import mar.compiler.expression.VariableExpression;
import mar.compiler.lexer.Lexer;
import mar.compiler.statement.CallStatement;
import mar.compiler.statement.IStatement;
import mar.compiler.statement.ReturnStatement;
import mar.compiler.statement.SetStatement;
import mar.compiler.token.AssemblyBlock;
import mar.compiler.token.Tag;
import mar.compiler.token.Token;
import mar.compiler.token.Word;

/**
 * <p>
 * 	The {@code Method} tells the {@code Parser} we are currently within a method.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Method {

	private String methodName;
	private Parser parser;
	private Lexer lexer;
	private Token token;
	private List<Word> passedVariablesList;
	private Map<Word, Integer> localVariables;
	private int inPos, locPos, reserved;

	/**
	 * <p>
	 * 	Constructs a new {@code Method}.
	 * </p>
	 * 
	 * @param lexer  The lexer.
	 * @param identifier The identifier.
	 */
	public Method(Parser parser, Lexer lexer, Word identifier) {
		this.parser = parser;
		this.passedVariablesList = new ArrayList<Word>();
		this.localVariables = new HashMap<Word, Integer>();
		this.reserved = 0;
		this.inPos = 2;
		this.locPos = 1;
		this.lexer = lexer;
		this.methodName = identifier.getWord();
		this.token = lexer.scan();
	}

	/**
	 * <p>
	 * 	Parses the method.
	 * </p>
	 * 
	 * @param ps The printstream to print to.
	 */
	public void parse(PrintStream ps) {
		this.match(Tag.LPAREN);

		while(this.token.getId() != Tag.RPAREN) {
			if(this.token.getId() != Tag.ID)
				error("Unexpected token " + Tag.toString(this.token.getId()));
			Word var = (Word) this.token;
			this.passedVariablesList.add(var);
			this.localVariables.put(var, this.inPos++);
			this.token = lexer.scan();
		}
		List<IStatement> statements = new LinkedList<IStatement>();

		this.match(Tag.RPAREN);
		this.match(Tag.LBLOCK);

		ps.print(Method.getASMName(this));
		if(!this.methodName.equals("main"))
			ps.println(":");
		else ps.println();

		ps.println("\tpush BP");
		ps.println("\tmov BP, SP");

		for(Word w : this.passedVariablesList) {
			ps.println("\tmov [BP - " + this.locPos + "], [BP + " + this.localVariables.get(w) + "]");
			this.localVariables.put(w, this.locPos++);
		}

		while(this.token.getId() != Tag.RBLOCK) {
			IStatement stmt = null;

			switch(this.token.getId()) {

			case Tag.ASM:
				AssemblyBlock asm = (AssemblyBlock) this.token;
				stmt = asm;
				this.token = this.lexer.scan();
				this.matchNoFollow(Tag.EXPR_TERMINATOR);
				break;

			case Tag.ID:
				Word id = (Word) this.token;
				this.token = this.lexer.scan();

				if(this.token.getId() == Tag.LPAREN) {
					Method m = this.parser.getMethod(id);
					List<Object> vars = new LinkedList<Object>();
					while(this.token.getId() != Tag.RPAREN) {
						vars.add(this.token);

						this.token = this.lexer.scan();
					}
					this.match(Tag.RPAREN);
					this.matchNoFollow(Tag.EXPR_TERMINATOR);
					stmt = new CallStatement(m, vars.toArray(new Object[vars.size()]));
				} else if(this.token.getId() == Tag.SET) {
					Expression exp = null;

					this.token = this.lexer.scan();
					exp = this.getExpression();

					if(!this.localVariables.containsKey(id)) {
						this.localVariables.put(id, this.locPos);
						this.locPos++;
					}

					stmt = new SetStatement(id, exp);
					this.matchNoFollow(Tag.EXPR_TERMINATOR);
				}

				break;

			case Tag.RETURN:
				this.token = this.lexer.scan();
				Expression exp = this.getExpression();

				stmt = new ReturnStatement(exp);
				this.matchNoFollow(Tag.EXPR_TERMINATOR);
				break;
			}
			if(stmt == null)
				error("Invalid statement(" + this.token + ") was found");

			statements.add(stmt);

			this.token = lexer.scan();
		}

		if(this.reserved > 0) {

		}

		for(IStatement stmt : statements) {
			stmt.parse(this, ps);
		}

		this.matchNoFollow(Tag.RBLOCK);

		ps.println("\tpop BP");

		if(this.methodName.equals("main")) {
			ps.println("\tbrk\n");
		} else {
			ps.println("\tret\n");
		}
	}

	/**
	 * <p>
	 * 	Gets an expression.
	 * </p>
	 * 
	 * @return The expression.
	 */
	private Expression getExpression() {
		Expression exp = null;
		while(this.token.getId() != Tag.EXPR_TERMINATOR) {
			boolean skip = true;
			switch(this.token.getId()) {
			case Tag.NUM:
				exp = new ConstantExpression(this.token);
				break;

			case Tag.ID:
				skip = false;
				Token next = this.lexer.scan();
				Word id = (Word) this.token;
				
				if(next.getId() == Tag.LPAREN) {
					Method m = this.parser.getMethod((Word) this.token);
					List<Object> vars = new LinkedList<Object>();
					while(this.token.getId() != Tag.RPAREN) {
						vars.add(this.token);

						this.token = this.lexer.scan();
					}
					this.match(Tag.RPAREN);
					exp = new CallExpression(m);
				} else {
					this.token = next;
					exp = new VariableExpression(id, this);
				}
				break;

			default:
				error("Invalid expression type " + this.token.getId());
			}
			if(skip) this.token = this.lexer.scan();
		}
		return exp;
	}

	/**
	 * <p>
	 * 	Matches the next token with an id.
	 * </p>
	 * 
	 * @param id The id to match with.
	 */
	private void match(int id) {
		if(this.token.getId() == id)
			this.token = lexer.scan();
		else
			error("Unexpected token " + Tag.toString(this.token.getId()));
	}

	/**
	 * <p>
	 * 	Matches the next token with an id.
	 * </p>
	 * 
	 * @param id The id to match with.
	 */
	private void matchNoFollow(int id) {
		if(this.token.getId() != id)
			error("Unexpected token " + Tag.toString(this.token.getId()));
	}

	/**
	 * <p>
	 * 	Throws an error.
	 * </p>
	 */
	private void error(String msg) {
		throw new ParserException(this.lexer, msg);
	}

	/**
	 * <p>
	 * 	Gets the list of words.
	 * </p>
	 * 
	 * @return The list of words.
	 */
	public List<Word> getWords() {
		return this.passedVariablesList;
	}

	/**
	 * <p>
	 * 	Gets the local position of a variable.
	 * </p>
	 * 
	 * @param w The variable.
	 * @return The local position.
	 */
	public int getLocalPosition(Word w) {
		return this.localVariables.get(w);
	}

	public Map<Word, Integer> getLocalPositions() {
		return this.localVariables;
	}

	/**
	 * <p>
	 * 	Gets the asm name.
	 * </p>
	 * 
	 * @param name The name.
	 * @return The asm name.
	 */
	public static String getASMName(Method m) {
		if(m.methodName.equals("main"))
			return ".text";
		StringBuilder str = new StringBuilder("_" + m.methodName);
		if(m.passedVariablesList.size() == 0)
			return str.toString();
		str.append("___");
		int i = 0;
		for(int j = 0; j < m.getWords().size(); j++) {
			str.append("int");
			i++;
			if(i < m.passedVariablesList.size()-1)
				str.append("_");
		}
		return str.toString();
	}

}
