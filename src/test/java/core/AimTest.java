package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import exception.Message;

public class AimTest {
	Task aim;

	@Before
	public void initialize() {
		aim = new Aim("aim");
	}

	@Test
	public void AimCanNotHaveNext() {
		assertTrue(aim.getNext().isEmpty());
	}

	
	@Test
	public void weightEqualZero() {
		assertEquals(0, aim.getWeight());
	}
	@Test
	public void AimCanaddNext() {
		try {
			Task next = new Task("test");
			aim.addNext(next);
			fail("Should throw exception when try to set next");
		} catch (UnsupportedOperationException aExp) {
			assertTrue (aExp.getMessage().contains(Message.ERROR_NO_NEXT));
		}
	}
	


	@Test
	public void AimIsATaskButATaskNotAlwaysAAim() {
		assertTrue(aim instanceof Task);
		assertTrue(aim instanceof Aim);
		
		Aim otherAim = new Aim("otherAim ");
		assertTrue(otherAim instanceof Task);
		assertTrue(otherAim instanceof Aim);
		
		Task task = new Task("task");
		assertTrue(task instanceof Task);
		assertFalse(task instanceof Aim);
	}

}
