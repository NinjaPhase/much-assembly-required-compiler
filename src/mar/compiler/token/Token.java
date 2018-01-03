package mar.compiler.token;

/**
 * <p>
 * 	A {@code Token} is a value or set of values read by the lexer and converted to
 *  a simple number.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Token {

	private int id;
	private String name;
	
	/**
	 * <p>
	 * 	Constructs a new {@code Token}.
	 * </p>
	 * 
	 * @param id The id of the token.
	 */
	public Token(int id) {
		this(Tag.toString(id), id);
	}
	
	/**
	 * <p>
	 * 	Constructs a new {@code Token} with a given name.
	 * </p>
	 * 
	 * @param name The name of the token.
 	 * @param id The id of the token.
	 */
	public Token(String name, int id) {
		this.id = id;
		this.name = name;
	}

	/**
	 * @return The id of the token.
	 */
	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "<Token " + this.id + ":" + this.name + ">";
	}
	
	public static Token TOKEN_LBLOCK = new Token("{", Tag.LBLOCK),
						TOKEN_RBLOCK = new Token("}", Tag.RBLOCK),
						TOKEN_LPAREN = new Token("(", Tag.LPAREN),
						TOKEN_RPAREN = new Token(")", Tag.RPAREN),
						TOKEN_EXPR = new Token(";", Tag.EXPR_TERMINATOR),
						TOKEN_SET = new Token("=", Tag.SET);
	
}
