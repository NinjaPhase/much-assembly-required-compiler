package test.mar.compiler;

import static org.junit.Assert.*;

import org.junit.Test;

import mar.compiler.token.Tag;

/**
 * <p>
 * 	Tests that the {@link Tag} work as expected.
 * </p>
 * 
 * @author NinjaPhase
 * @version 03 January 2018
 *
 */
public class TestTag {
	
	@Test
	public void testToString() {
		assertEquals("ID", Tag.toString(Tag.ID));
		System.out.println(Tag.toString(Tag.ID));
	}
	
}
