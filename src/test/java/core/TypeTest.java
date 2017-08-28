package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class TypeTest {
	@Test
	public void equalsType() {

		Type binary = Type.BINARY;
		Type binary2 = Type.BINARY;
		Type repetitive = Type.REPETITIVE;
		Type withStage = Type.WITH_STAGE;
		assertEquals(binary2, binary2);
		assertFalse(repetitive.equals(binary));
		assertFalse(repetitive.equals(withStage));

	}
}
