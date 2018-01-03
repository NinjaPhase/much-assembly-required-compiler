package mar.compiler.token;

/**
 * <p>
 * 	The {@code Word} is a {@link Token} that handles words (As in dictionary words).
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Word extends Token {
	
	private String word;
	
	/**
	 * <p>
	 * 	Constructs a new {@code Word}.
	 * </p>
	 * 
	 * @param word The word.
 	 * @param id The id.
	 */
	public Word(String word, int id) {
		super(word, id);
		this.word = word;
	}
	
	/**
	 * <p>
	 * 	Gets the word of this {@code Word}.
	 * </p>
	 * 
	 * @return The word.
	 */
	public String getWord() {
		return this.word;
	}
	
	public static final Word KEYWORD_RETURN = new Word("return", Tag.RETURN);
	
}
