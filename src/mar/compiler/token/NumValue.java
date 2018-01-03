package mar.compiler.token;

/**
 * <p>
 * 	The {@code NumValue} is a static numerical value.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class NumValue extends Token {
	
	private int value;
	
	/**
	 * <p>
	 * 	Constructs a new {@code NumValue}.
	 * </p>
	 * 
	 * @param value The value of the number.
	 */
	public NumValue(int value) {
		super(Tag.NUM);
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "<NumValue " + Integer.toString(value) + ">";
	}

	public int getValue() {
		return this.value;
	}
	
}
