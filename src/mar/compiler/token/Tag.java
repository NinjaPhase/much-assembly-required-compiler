package mar.compiler.token;

import java.lang.reflect.Field;

/**
 * <p>
 * 	The {@code} tag class holds a group of tags which are used to identify {@link mar.compiler.token.Token Token}'s.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Tag {
	private Tag() {}

	public static final int ID = 256,
							LBLOCK = 257,
							RBLOCK = 258,
							LPAREN = 259,
							RPAREN = 260,
							ASM = 261,
							NUM = 262,
							EXPR_TERMINATOR = 263,
							SET = 264,
							RETURN = 265;

	/**
	 * <p>
	 * 	Converts a tag to a string (Mostly used for testing).
	 * </p>
	 * 
	 * @param id The id of the tag.
	 * @return The name of the tag.
	 */
	public static String toString(int id) {
		try {
			for(Field f : Tag.class.getDeclaredFields()) {
				if(f.getInt(Tag.class) == id) {
					return f.getName();
				}
			}
		} catch (IllegalAccessException e) {
			return null;
		}
		return null;
	}

}
