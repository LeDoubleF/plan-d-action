package core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.Aim;
import core.Diagram;
import core.Task;
import exception.ComplianceException;
import exception.Message;

@SuppressWarnings("unused")
public class DiagramTest {
	Task aim, taskA, taskB, taskC, taskD;

	List<Task> listForGoodDiagram;
	Diagram goodDiagram;

	@Before
	public void initialize() {
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

		listForGoodDiagram = new ArrayList<Task>();
		listForGoodDiagram.add(taskA);
		listForGoodDiagram.add(taskB);
		listForGoodDiagram.add(taskC);
		listForGoodDiagram.add(taskD);
		listForGoodDiagram.add(aim);
		goodDiagram = new Diagram(listForGoodDiagram);
	}

	@Test
	public void diagramHaveTheGoodAim() {
		assertEquals(aim, goodDiagram.getTaskByName("aim"));
		assertEquals(taskA, goodDiagram.getTaskByName("A"));
		assertEquals(taskC, goodDiagram.getTaskByName("C"));
	}
	
	@Test
	public void getTaskBynamem() {
		Diagram simpleDiagram = new Diagram(aim);
		assertEquals(aim, simpleDiagram.getAim());
	}

	@Test
	public void diagramCanNotHaveTwoAim() {
		Task aim2 = new Aim("aim2");

		List<Task> TaskListWithTwoAim = new ArrayList<Task>();
		TaskListWithTwoAim.add(aim);
		TaskListWithTwoAim.add(aim2);

		try {
			Diagram simpleDiagramWithTwoAim = new Diagram(TaskListWithTwoAim);
			fail("Should throw exception when a list of task have two aims");
		} catch (Exception aExp) {
			assertTrue(aExp.getMessage().contains(Message.ONE_AND_ONLY_ONE_AIM));
		}
	}

	@Test
	public void diagramCanHaveNoAim() {

		try {
			Diagram simpleDiagram = new Diagram(taskA);
			fail("Should throw exception when a diagram not have aim");
		} catch (Exception aExp) {
			assertTrue(aExp.getMessage().contains(Message.ONE_AND_ONLY_ONE_AIM));
		}
	}

	@Test
	public void aTaskShouldHaveNext() {
		Task solo = new Task("solo");
		List<Task> listForWrongDiagram = new ArrayList<Task>();
		listForWrongDiagram.add(taskA);
		listForWrongDiagram.add(taskB);
		listForWrongDiagram.add(taskC);
		listForWrongDiagram.add(taskD);
		listForWrongDiagram.add(aim);
		listForWrongDiagram.add(solo);
		try {
			Diagram wrongDiagram = new Diagram(listForWrongDiagram);
		} catch (Exception aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains(Message.TASK_SHOULD_HAVE_NEXT ));
			assertTrue(message.contains(solo.getName() ));
		}
	}

	@Test
	public void aTaskShouldHaveNextInTheDiagram() {
		Task solo = new Task("solo");
		Task duo = new Task("duo");
		Task trio = new Task("trio");
		solo.addNext(trio);
		duo.addNext(trio);

		List<Task> listForWrongDiagram = new ArrayList<Task>();
		listForWrongDiagram.add(taskA);
		listForWrongDiagram.add(taskB);
		listForWrongDiagram.add(taskC);
		listForWrongDiagram.add(taskD);
		listForWrongDiagram.add(aim);
		listForWrongDiagram.add(solo);
		listForWrongDiagram.add(duo);

		try {
			Diagram wrongDiagram = new Diagram(listForWrongDiagram);
			fail("Should throw exception when a diagram have a task with no next in the diagram");
		} catch (Exception aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains(Message.TASK_SHOULD_HAVE_NEXT));
			assertTrue(message.contains(solo.getName()));
			assertTrue(message.contains(duo.getName()));
		}
	}

	@Test
	public void AimCanNotHaveNext() {
		assertTrue(aim.getNext().isEmpty());
	}

	@Test
	public void getFeasibleTask() {
		assertEquals(5, goodDiagram.getTasks().size());
		assertTrue(goodDiagram.getFeasibleTask().contains(taskA));
		assertTrue(goodDiagram.getFeasibleTask().contains(taskB));
		assertTrue(goodDiagram.getFeasibleTask().contains(taskC));
	}

	@Test
	public void getTaskWeigth() {
		assertEquals(0, aim.getWeight());
		assertEquals(1, taskD.getWeight());
		assertEquals(1, taskC.getWeight());
		assertEquals(2, taskA.getWeight());
		assertEquals(3, taskB.getWeight());
	}

	@Test
	public void taskOrder() {

		assertEquals(0, goodDiagram.getTaskOrder(taskB));
		assertEquals(1, goodDiagram.getTaskOrder(taskA));
		assertEquals(2, goodDiagram.getTaskOrder(taskC));
		assertEquals(3, goodDiagram.getTaskOrder(taskD));
		assertEquals(4, goodDiagram.getTaskOrder(aim));

	}

	@Test
	public void addTaskIsAim() {
		try {
			Task aim2 = new Aim("aim2");
			goodDiagram.addTask(aim2);
			fail("Should throw exception when you add a  new aim into the diagram");
		} catch (Exception aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains(Message.ONE_AND_ONLY_ONE_AIM));
		}
	}

	@Test
	public void addTask() {
		Task taskF = new Task("F");
		taskF.addNext(taskB);
		taskF.addNext(taskD);
		goodDiagram.addTask(taskF);

		assertEquals(0, aim.getWeight());
		assertEquals(1, taskD.getWeight());
		assertEquals(1, taskC.getWeight());
		assertEquals(2, taskA.getWeight());
		assertEquals(3, taskB.getWeight());
		assertEquals(6, taskF.getWeight());

		assertTrue(goodDiagram.getFeasibleTask().contains(taskA));
		assertTrue(goodDiagram.getFeasibleTask().contains(taskF));
		assertTrue(goodDiagram.getFeasibleTask().contains(taskC));
		assertFalse(goodDiagram.getFeasibleTask().contains(taskB));
	}

	@Test
	public void finishTasknotInDiagram() {

		
		try {
			Task taskF = new Task("F");
			goodDiagram.finish(taskF);
			fail("Should throw exception when you finish a task which is not into the diagram");
		} catch (Exception aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains(Message.TASK_NOT_IN_DIAGRAM));
		}
	}
	@Test
	public void finishTaskinFeasibleTask() {
		Collection<Task> feasibleTask = goodDiagram.getFeasibleTask();
		assertEquals(5, goodDiagram.getTasks().size());
		assertTrue(feasibleTask.contains(taskA));
		assertTrue(feasibleTask.contains(taskB));
		assertTrue(feasibleTask.contains(taskC));
		
		goodDiagram.finish(taskB);
		assertEquals(5, goodDiagram.getTasks().size());
		assertEquals(2, feasibleTask.size());
		assertTrue(goodDiagram.getFeasibleTask().contains(taskA));
		assertTrue(goodDiagram.getFeasibleTask().contains(taskC));
	}

	@Test
	public void finishTaskInTheMiddle1() {
		Collection<Task> feasibleTask = goodDiagram.getFeasibleTask();
		assertEquals(5, goodDiagram.getTasks().size());
		assertTrue(feasibleTask.contains(taskA));
		assertTrue(feasibleTask.contains(taskB));
		assertTrue(feasibleTask.contains(taskC));

		goodDiagram.finish(taskD);
		feasibleTask = goodDiagram.getFeasibleTask();

		assertEquals(5, goodDiagram.getTasks().size());
		assertEquals(2, feasibleTask.size());
		assertTrue(feasibleTask.contains(taskB));
		assertTrue(feasibleTask.contains(taskC));
	}

	@Test
	public void finishTaskInTheMiddle2() {
		aim = new Aim("aim");
		taskA = new Task("A");
		taskB = new Task("B");
		taskC = new Task("C");
		taskD = new Task("D");

		taskA.addNext(taskD);
		taskB.addNext(taskD);
		taskC.addNext(aim);
		taskD.addNext(aim);

		listForGoodDiagram = new ArrayList<Task>();
		listForGoodDiagram.add(taskA);
		listForGoodDiagram.add(taskB);
		listForGoodDiagram.add(taskC);
		listForGoodDiagram.add(taskD);
		listForGoodDiagram.add(aim);
		goodDiagram = new Diagram(listForGoodDiagram);
		
		Collection<Task> feasibleTask = goodDiagram.getFeasibleTask();
		assertEquals(5, goodDiagram.getTasks().size());
		assertTrue(feasibleTask.contains(taskA));
		assertTrue(feasibleTask.contains(taskB));
		assertTrue(feasibleTask.contains(taskC));

		goodDiagram.finish(taskD);
		feasibleTask = goodDiagram.getFeasibleTask();

		assertEquals(5, goodDiagram.getTasks().size());
		assertEquals(1, feasibleTask.size());
		assertTrue(feasibleTask.contains(taskC));
	}
}
