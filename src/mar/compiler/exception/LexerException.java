package mar.compiler.exception;

/**
 * <p>
 * 	The {@code LexerException} contains issues with the lexer.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class LexerException extends RuntimeException {
	private static final long serialVersionUID = -5075336310970988099L;
	
	/**
	 * <p>
	 * 	Constructs a new {@code LexerException}
	 * </p>
	 * 
	 * @param line The line.
	 * @param pos The position.
	 * @param message The message.
	 */
	public LexerException(int line, int pos, String message) {
		super("[" + Integer.toString(line) + " : " + Integer.toString(pos) + "]: " + message);
	}
	
}
