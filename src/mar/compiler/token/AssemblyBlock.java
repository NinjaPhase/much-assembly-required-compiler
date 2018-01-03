package mar.compiler.token;

import java.io.PrintStream;
import java.util.Iterator;

import mar.compiler.parser.Method;
import mar.compiler.statement.IStatement;

/**
 * <p>
 * 	The {@code AssemblyBlock} holds a block of assembly code, this is not analysed.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class AssemblyBlock extends Word implements IStatement {

	private String asmCode;

	/**
	 * <p>
	 * 	Constructs a new {@code AssemblyBlock}.
	 * </p>
	 * 
	 * @param code The code.
	 */
	public AssemblyBlock(String code) {
		super(code, Tag.ASM);
		this.asmCode = code;
	}

	/**
	 * <p>
	 * 	The assembly code.
	 * </p>
	 * 
	 * @return The assembly code.
	 */
	public String getCode() {
		return this.asmCode;
	}

	@Override
	public String toString() {
		return "<AssemblyBlock " + this.asmCode + ">";
	}

	@Override
	public void parse(Method m, PrintStream out) {
		for(String s : this.asmCode.split("\n")) {
			if(s.trim().length() == 0)
				continue;
			Iterator<Word> wordStr = m.getLocalPositions().keySet().iterator();
			while(wordStr.hasNext()) {
				Word w = wordStr.next();
				s = s.replaceAll("\\$" + w.getWord(), "[BP - " + m.getLocalPositions().get(w) + "]");
			}
			out.println("\t" + s.trim());
		}
	}

}
