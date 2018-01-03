package test.mar.compiler;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import mar.compiler.exception.LexerException;
import mar.compiler.lexer.Lexer;
import mar.compiler.token.Tag;
import mar.compiler.token.Token;
import mar.compiler.token.Word;

/**
 * <p>
 * 	Tests the {@link mar.compiler.lexer.Lexer}.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class TestLexer {
	
	@Test(expected = NullPointerException.class)
	public void testNullInput() {
		new Lexer(null);
	}
	
	@Test
	public void testReadNothing() throws IOException {
		StringBuilder str = new StringBuilder("");
		ByteArrayInputStream bis = new ByteArrayInputStream(str.toString().getBytes());
		Lexer lex = new Lexer(bis);
		assertFalse(lex.read());
		bis.close();
	}
	
	@Test
	public void testReadOne() throws IOException {
		StringBuilder str = new StringBuilder("{");
		ByteArrayInputStream bis = new ByteArrayInputStream(str.toString().getBytes());
		Lexer lex = new Lexer(bis);
		assertTrue(lex.read());
		assertFalse(lex.read());
		bis.close();
	}
	
	@Test
	public void testReadTwo() throws IOException {
		StringBuilder str = new StringBuilder("{}");
		ByteArrayInputStream bis = new ByteArrayInputStream(str.toString().getBytes());
		Lexer lex = new Lexer(bis);
		assertTrue(lex.read());
		assertTrue(lex.read());
		assertFalse(lex.read());
		bis.close();
	}
	
	@Test
	public void testReadID() throws LexerException, IOException {
		StringBuilder str = new StringBuilder("test");
		ByteArrayInputStream bis = new ByteArrayInputStream(str.toString().getBytes());
		Lexer lex = new Lexer(bis);
		Word t = (Word) lex.scan();
		assertEquals(Tag.ID, t.getId());
		assertEquals("test", t.getWord());
		bis.close();
	}
	
	@Test
	public void testWhitespace() throws LexerException, IOException {
		StringBuilder str = new StringBuilder("    test   \r\n     \r\n     test2 \r\n");
		ByteArrayInputStream bis = new ByteArrayInputStream(str.toString().getBytes());
		Lexer lex = new Lexer(bis);
		Word t = (Word) lex.scan();
		assertEquals(Tag.ID, t.getId());
		assertEquals("test", t.getWord());
		t = (Word) lex.scan();
		assertEquals(Tag.ID, t.getId());
		assertEquals("test2", t.getWord());
		bis.close();
	}
	
	@Test
	public void testBlock() throws LexerException, IOException {
		StringBuilder str = new StringBuilder("{}");
		ByteArrayInputStream bis = new ByteArrayInputStream(str.toString().getBytes());
		Lexer lex = new Lexer(bis);
		Token t = lex.scan();
		assertEquals(Tag.LBLOCK, t.getId());
		t = lex.scan();
		assertEquals(Tag.RBLOCK, t.getId());
		bis.close();
	}

}
