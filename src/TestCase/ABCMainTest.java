package TestCase;

import static org.junit.Assert.*;

import org.junit.Test;

import Player.Main;

public class ABCMainTest {

	@Test
	public void test() {
		String filename = "sample_docs/invention.abc";
		Main.play(filename);
		assertTrue(true);
	}

}
