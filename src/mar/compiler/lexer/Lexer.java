package mar.compiler.lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import mar.compiler.exception.LexerException;
import mar.compiler.token.AssemblyBlock;
import mar.compiler.token.NumValue;
import mar.compiler.token.Tag;
import mar.compiler.token.Token;
import mar.compiler.token.Word;

/**
 * <p>
 * 	The {@code Lexer} analyses the code line by line and converts it to
 * 	tokens.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Lexer {

	private char peek;
	private int lineNum, position;
	private InputStream in;
	private boolean isFinished;
	private Map<String, Token> keywords;

	/**
	 * <p>
	 * 	Constructs a new lexer with a given stream.
	 * </p>
	 * 
	 * @param in
	 */
	public Lexer(InputStream in) {
		if(in == null)
			throw new NullPointerException("InputStream cannot be null.");
		this.keywords = new HashMap<String, Token>();
		this.in = in;
		this.lineNum = 1;
		this.position = 1;
		this.peek = ' ';
		this.reserveKeywords();
	}

	/**
	 * <p>
	 * 	Reserves keywords from {@code Token}.
	 * </p>
	 */
	private void reserveKeywords() {
		this.keywords.put("asm", new Word("asm", Tag.ASM));
		this.keywords.put("return", Word.KEYWORD_RETURN);
	}

	/**
	 * <p>
	 * 	Reads the next character.
	 * </p>
	 * 
	 * @return Whether to carry on reading.
	 * @throws IOException Thrown if there is an error reading from the current stream.
	 */
	public boolean read() throws IOException {
		int i = this.in.read();
		this.position++;
		if(i == -1) {
			this.isFinished = true;
			return false;
		} else {
			this.peek = (char) i;
			return true;
		}
	}

	/**
	 * <p>
	 * 	Scans the current stream for {@code Token}.
	 * </p>
	 * 
	 * @throws LexerException Thrown if there is an error with the lexical analysis.
	 */
	public Token scan() throws LexerException {
		try {
			for(; !this.isFinished; this.read()) {
				if(this.peek == ' ' || this.peek == '\t') {
					continue;
				} else if(this.peek == '\r') {
					this.position = 1;
					this.lineNum++;
					this.read();
					if(this.peek == ' ' || this.peek == '\t') {
						continue;
					} else if(this.peek == '\r') {
						this.position = 1;
						this.lineNum++;
					} else if(this.peek == '\n') {
						this.position = 1;
						continue;
					} else
						break;
					continue;
				} else if(this.peek == '\n') {
					this.position = 1;
					this.lineNum++;
				} else break;
			}

			if(this.isFinished)
				return null;

			switch(this.peek) {
			case '{':
				this.peek = ' ';
				return Token.TOKEN_LBLOCK;
			case '}':
				this.peek = ' ';
				return Token.TOKEN_RBLOCK;
			case '(':
				this.peek = ' ';
				return Token.TOKEN_LPAREN;
			case ')':
				this.peek = ' ';
				return Token.TOKEN_RPAREN;
			case ';':
				this.peek = ' ';
				return Token.TOKEN_EXPR;
			case '=':
				this.peek = ' ';
				return Token.TOKEN_SET;
			}
			
			if(Character.isDigit(this.peek)) {
				int base = 10;
				StringBuilder str = new StringBuilder();
				do {
					str.append(peek);
					this.read();
				} while (Character.isDigit(peek) && !this.isFinished);
				if(peek == 'x') {
					base = 16;
					this.read();
					do {
						str.append(peek);
						this.read();
					} while (Character.isLetterOrDigit(peek) && !this.isFinished);
				}
				return new NumValue(Integer.parseInt(str.toString(), base));
			}

			if(Character.isLetter(this.peek)) {
				StringBuilder str = new StringBuilder();
				do {
					str.append(peek);
					this.read();
				} while ((Character.isLetterOrDigit(peek) || peek == '_') && !this.isFinished);
				String token = str.toString();
				if(token.equals("asm")) {
					StringBuilder code = new StringBuilder();
					do {
						if(peek != '{')
							code.append(peek);
						read();
					} while (peek != '}' && !this.isFinished);
					this.peek = ' ';
					return new AssemblyBlock(code.toString());
				}
				if(this.keywords.containsKey(token))
					return this.keywords.get(token);
				this.keywords.put(token, new Word(token, Tag.ID));
				return this.keywords.get(token);
			}

		} catch (IOException e) {
			throw new LexerException(this.lineNum, this.position, e.getMessage());
		}

		throw new LexerException(this.lineNum, this.position, "Unexpected character " + this.peek);
	}

	public int getLine() {
		return this.lineNum;
	}
	
	public int getPos() {
		return this.position;
	}

}
