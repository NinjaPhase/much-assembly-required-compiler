package mar.compiler;

import java.io.FileInputStream;

import mar.compiler.lexer.Lexer;
import mar.compiler.parser.Parser;

/**
 * <p>
 * 	The {@code Program} is the main access to the code.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class Program {

	public static void main(String[] args) {
		if(args.length != 1) {
			System.err.println("You must specify an input file.");
			System.err.println("Usage:");
			System.err.println("<program_name> <filename>");
			return;
		}
		String f = args[0];
		try {
			FileInputStream fis = new FileInputStream(f);
			Lexer lexer = new Lexer(fis);
			Parser p = new Parser(lexer);
			p.parse(System.out);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
