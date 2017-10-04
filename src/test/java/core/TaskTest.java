package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class TaskTest {
	private static final String NEW_NAME = "newName";
	private static final String NAME = "test";
	Task task;

	@Before
	public void initialize() {
		task = new Task(NAME);
	}

	@Test
	public void taskShouldHaveAName() {
		try {
			task = new Task(null);
			fail("Should throw exception when a task don't have a name");
		} catch (Exception aExp) {
			assertTrue(aExp.getMessage().contains("A task should have a name"));
		}
	}

	@Test
	public void taskShouldHaveANameNotEmpty() {
		try {
			task = new Task(" ");
			fail("Should throw exception when a task don't have a name");
		} catch (Exception aExp) {
			assertTrue(aExp.getMessage().contains("A task should have a name"));
		}
	}

	@Test
	public void SimpleSetAndGetName() {
		assertEquals(NAME, task.getName());
		task.setName(NEW_NAME);
		assertEquals(NEW_NAME, task.getName());
	}


	@Test
	public void successiveAddition() {

		Task a = new Task("a");
		Task d = new Task("d");
		assertEquals(0, a.getWeight());
		assertEquals(0, d.getWeight());

		a.addNext(d);
		assertEquals(1, a.getWeight());
		assertEquals(0, d.getWeight());
		assertTrue(a.getNext().contains(d));
		assertTrue(d.getPrevious().contains(a));

		Task h = new Task("h");
		assertEquals(0, h.getWeight());

		d.addNext(h);

		assertEquals(2, a.getWeight());
		assertEquals(1, d.getWeight());
		assertEquals(0, h.getWeight());

		Task f = new Task("f");
		assertEquals(0, f.getWeight());

		d.addNext(f);
		assertEquals(3, a.getWeight());
		assertEquals(2, d.getWeight());
		assertEquals(0, h.getWeight());
		assertEquals(0, f.getWeight());

		Task g = new Task("g");
		Task b = new Task("b");
		assertEquals(0, g.getWeight());
		assertEquals(0, b.getWeight());

		g.addNext(b);
		assertEquals(1, g.getWeight());
		assertEquals(0, b.getWeight());

		b.addNext(d);
		assertEquals(4, g.getWeight());
		assertEquals(3, b.getWeight());
		assertEquals(3, a.getWeight());
		assertEquals(2, d.getWeight());
		assertEquals(0, h.getWeight());
		assertEquals(0, f.getWeight());

		assertTrue(a.getNext().contains(d));
		assertTrue(d.getNext().contains(f));
		assertTrue(d.getNext().contains(h));
		assertTrue(b.getNext().contains(d));
		assertTrue(g.getNext().contains(b));

		assertTrue(f.getPrevious().contains(d));
		assertTrue(f.getPrevious().contains(d));
		assertTrue(d.getPrevious().contains(a));
		assertTrue(d.getPrevious().contains(b));
		assertTrue(b.getPrevious().contains(g));

	}

	@Test
	public void canNotAddSeveralTimeSameTask() {

		Task a = new Task("a");
		Task d = new Task("d");
		assertEquals(0, a.getWeight());
		assertEquals(0, d.getWeight());

		a.addNext(d);
		assertEquals(1, a.getWeight());
		assertEquals(0, d.getWeight());
		assertTrue(a.getNext().contains(d));
		assertTrue(d.getPrevious().contains(a));

		a.addNext(d);
		assertEquals(1, a.getWeight());
		assertEquals(0, d.getWeight());
		assertTrue(a.getNext().contains(d));
		assertTrue(d.getPrevious().contains(a));
	}

	@Test
	public void SimpleSetProgress() {
		task.setProgress(50);
		assertTrue(task.getProgress() == 50);
	}

	@Test
	public void equalsTask() {

		Task a = new Task("a");
		Task d = new Task("a");
		String test = new String("test");
		assertEquals(a, a);
		assertFalse(a.equals(null));
		assertFalse(a.equals(test));
		assertEquals(a, d);
		assertTrue(a.equals(d) && d.equals(a));
		assertTrue(a.hashCode() == d.hashCode());
		

		a.setProgress(50);
		assertFalse(a.equals(d));

		d.setProgress(50);
		assertEquals(a, d);

	}

	@Test
	public void isFinalize(){
		
		task.setProgress(50);
		assertTrue(task.getProgress() == 50);
		assertFalse(task.isFinish());
		
		
		task.setProgress(85);
		assertTrue(task.getProgress() == 85);
		assertTrue(task.isFinish());
		
		task.setProgress(10);
		assertTrue(task.getProgress() == 10);
		task.finish();
		assertTrue(task.getProgress() == 100);
		assertTrue(task.isFinish());
		
	}
	
	@Test
	public void shouldNotHaveCycle() {
		try {
			Task aim, taskA, taskB, taskC, taskD;
			aim = new Aim("aim");
			taskA = new Task("A");
			taskB = new Task("B");
			taskC = new Task("C");
			taskD = new Task("D");
			taskA.addNext(taskD);
			taskB.addNext(taskD);
			taskB.addNext(aim);
			taskC.addNext(aim);
			taskD.addNext(aim);
			taskD.addNext(taskC);
			taskC.addNext(taskB);

			fail("Should throw exception when a diagram have a cycle");
		} catch (Exception aExp) {
			assertTrue(aExp.getMessage().contains("Shouldn't have cycle"));
		}
	}

}
