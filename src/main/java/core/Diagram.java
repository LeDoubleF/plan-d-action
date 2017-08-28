package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import exception.ComplianceException;

public class Diagram {
	private Aim aim;
	private List<Task> tasks = new ArrayList<>();

	private List<Task> feasibleTask = new ArrayList<>();

	public Diagram(Task aim) {
		if (aim instanceof Aim) {
			this.setAim((Aim) aim);
		} else {
			throw new ComplianceException("You have one and only one aim");
		}
	}

	public Diagram(List<Task> taskList) {
		addInitiatTasksList(taskList);
		verifyNotTaskAlone();
		Collections.sort(this.tasks);

	}

	private void addInitiatTasksList(List<Task> taskList) {
		int nbAim = 0;
		for (Task task : taskList) {
			if (task instanceof Aim) {
				nbAim++;
				if (nbAim > 1) {
					throw new ComplianceException("You have one and only one aim");
				}
				this.setAim((Aim) task);
				tasks.add(task);
			} else {
				tasks.add(task);
				if (!task.hasPrevious()) {
					feasibleTask.add(task);
				}
			}
		}
	}

	private boolean finalIsAim(Task task) {
		if (task.hasNext()) {
			boolean result = true;
			for (Task son : task.getNext()) {
				result = result && finalIsAim(son);
			}
			return result;
		} else {
			return task instanceof Aim;
		}
	}

	private void verifyNotTaskAlone() {
		StringBuilder tasksNameError = new StringBuilder();
		for (Task task : tasks) {
			if (!finalIsAim(task)) {
				tasksNameError.append(" ").append(task.getName());
			}
		}
		if (!tasksNameError.toString().equals("")) {
			throw new ComplianceException("Task :" + tasksNameError.toString() + " Should have Next");
		}
	}

	public void addTask(Task task) {
		if (task instanceof Aim) {
			throw new ComplianceException("You have one and only one aim");
		} else {
			tasks.add(task);
			if (!task.hasPrevious()) {
				feasibleTask.add(task);
			}
			for (Task followers:task.getNext()){
				if (feasibleTask.contains(followers)){
					feasibleTask.remove(followers);
				}
				
			}
			verifyNotTaskAlone();
			Collections.sort(this.tasks);
		}
	}

	public Collection<Task> getFeasibleTask() {
		return feasibleTask;
	}

	public int getTaskOrder(Task task) {
		return tasks.indexOf(task);
	}

	public Aim getAim() {
		return this.aim;
	}

	private void setAim(Aim aim) {
		this.aim = aim;
	}

	public List<Task> getTasks() {
		return tasks;
	}
}
