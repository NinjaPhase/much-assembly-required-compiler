package mar.compiler.parser;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import mar.compiler.exception.ParserException;
import mar.compiler.lexer.Lexer;
import mar.compiler.token.Tag;
import mar.compiler.token.Token;
import mar.compiler.token.Word;

/**
 * <p>
 * 	The {@code Parser} is responsible for parsing the lexical blocks and generating code
 * 	from them.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Parser {

	private Method current;
	private Lexer lexer;
	private Token look;
	private Map<Word, Method> methods;

	/**
	 * <p>
	 * 	Constructs a new {@code Parser} using a given {@code Lexer}.
	 * </p>
	 * 
	 * @param lexer The lexer.
	 */
	public Parser(Lexer lexer) {
		this.methods = new HashMap<Word, Method>();
		this.lexer = lexer;
		this.move();
	}

	/**
	 * <p>
	 * 	Parses and generates the program.
	 * </p>
	 */
	public void parse(OutputStream out) {
		PrintStream ps = new PrintStream(out);
		while(this.look != null) {
			if(this.look.getId() != Tag.ID)
				error("Unexpected token " + Tag.toString(this.look.getId()));
			this.current = new Method(this, lexer, (Word) this.look);
			this.methods.put((Word) this.look, this.current);
			this.current.parse(ps);
			this.move();
		}
	}
	
	/**
	 * <p>
	 * 	Moves to the next token.
	 * </p>
	 */
	private void move() {
		this.look = this.lexer.scan();
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
	 * @param w The identifier.
	 * @return The method for the given identifier.
	 */
	public Method getMethod(Word w) {
		return this.methods.get(w);
	}

}
