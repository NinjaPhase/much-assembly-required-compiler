package mar.compiler.exception;

import mar.compiler.lexer.Lexer;

/**
 * <p>
 * 	A {@code ParserException} is thrown if there is a problem parsing.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class ParserException extends RuntimeException {
	private static final long serialVersionUID = 2979443308752968015L;
	
	/**
	 * <p>
	 * 	Constructs a new {@code ParserException}.
	 * </p>
	 */
	public ParserException(Lexer lex, String msg) {
		super(msg + " near " + lex.getLine() + " : " + lex.getPos());
	}
	
}
