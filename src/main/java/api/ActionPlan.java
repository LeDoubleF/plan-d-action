package api;

import java.util.List;

import core.Aim;
import core.Diagram;
import core.Task;
import exception.ComplianceException;
import exception.Message;

public class ActionPlan {
	private Diagram diagram;

	public ActionPlan(String aimName) {
		super();
		Aim aim = new Aim(aimName);
		diagram = new Diagram(aim);
	}

	public void addTask(String taskName, String... followers) {
		Task task = new Task(taskName);
		for (String nextName : followers) {
			Task nextTask = diagram.getTaskByName(nextName);
			if (nextTask == null) {
				throw new ComplianceException(Message.DATA_ERROR);
			}
			task.addNext(nextTask);
		}
		diagram.addTask(task);
	}

	public List<Task> getTasks() {
		return diagram.getTasks();
	}

	public String getFeasibleTaskName() {
		StringBuilder feasibleTask = new StringBuilder();
		for (Task task : diagram.getFeasibleTask()) {
			feasibleTask.append(task.getName());
			feasibleTask.append(" ");
		}
		return feasibleTask.toString();
	}

	public boolean isFinish(String taskName) {
		Task task = diagram.getTaskByName(taskName);
		return task.isFinish();
	}

	public void finish(String taskName) {
		diagram.finish(diagram.getTaskByName(taskName));
	}
}
