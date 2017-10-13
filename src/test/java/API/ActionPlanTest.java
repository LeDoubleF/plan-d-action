package API;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import api.ActionPlan;
import exception.Message;

public class ActionPlanTest {

	@Test
	public void addTaskAndGetfeasibleTask() {
		ActionPlan plan = new ActionPlan("aim");
		plan.addTask("A", "aim");
		assertEquals(2, plan.getTasks().size());
		plan.addTask("C", "aim");
		assertEquals(3, plan.getTasks().size());
		plan.addTask("D", "A");
		assertEquals(4, plan.getTasks().size());
		String feasibleTask = plan.getFeasibleTaskName();
		assertTrue(feasibleTask.contains("D"));
		assertTrue(feasibleTask.contains("C"));
		assertFalse(feasibleTask.contains("A"));
	}

	@Test
	public void addTaskWithFollowerWhoNotExist() {
		try {
			ActionPlan plan = new ActionPlan("aim");
			plan.addTask("A", "aim,B");
			fail("Should throw exception when a follower is not in the diagram");
		} catch (Exception aExp) {
			assertTrue(aExp.getMessage().contains(Message.DATA_ERROR));
		}
	}

	@Test
	public void isFinishAndGetfeasibleTask() {
		ActionPlan plan = new ActionPlan("aim");
		plan.addTask("A", "aim");
		plan.addTask("B", "aim");
		plan.addTask("C", "A");
		plan.addTask("D", "A");
		plan.addTask("E", "B");

		assertFalse(plan.isFinish("A"));
		assertFalse(plan.isFinish("B"));
		assertFalse(plan.isFinish("C"));
		assertFalse(plan.isFinish("D"));
		assertFalse(plan.isFinish("E"));

		String feasibleTask = plan.getFeasibleTaskName();
		assertFalse(feasibleTask.contains("A"));
		assertFalse(feasibleTask.contains("B"));
		assertTrue(feasibleTask.contains("C"));
		assertTrue(feasibleTask.contains("D"));
		assertTrue(feasibleTask.contains("E"));

		plan.finish("C");
		plan.finish("E");
		assertFalse(plan.isFinish("A"));
		assertFalse(plan.isFinish("B"));
		assertTrue(plan.isFinish("C"));
		assertFalse(plan.isFinish("D"));
		assertTrue(plan.isFinish("E"));

		feasibleTask = plan.getFeasibleTaskName();
		assertFalse(feasibleTask.contains("A"));
		assertTrue(feasibleTask.contains("B"));
		assertFalse(feasibleTask.contains("C"));
		assertTrue(feasibleTask.contains("D"));
		assertFalse(feasibleTask.contains("E"));
	}

	@Test
	public void getTaskNameAndWeight() {
		ActionPlan plan = new ActionPlan("aim");
		plan.addTask("C", "aim");
		plan.addTask("D", "aim");
		plan.addTask("B", "aim", "D");
		plan.addTask("A", "D");

		String feasibleTask = plan.getFeasibleTaskName();
		assertTrue(feasibleTask.contains("A"));
		assertTrue(feasibleTask.contains("B"));
		assertTrue(feasibleTask.contains("C"));
		assertFalse(feasibleTask.contains("D"));

		String taskNameAndWeight = plan.getTaskNameAndWeight();
		assertTrue(taskNameAndWeight.contains("A:2"));
		assertTrue(taskNameAndWeight.contains("B:3"));
		assertTrue(taskNameAndWeight.contains("C:1"));
		assertTrue(taskNameAndWeight.contains("D:1"));
		assertTrue(taskNameAndWeight.contains("aim:0"));
	}
}
