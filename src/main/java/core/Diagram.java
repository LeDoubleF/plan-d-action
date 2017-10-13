package core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import exception.ComplianceException;
import exception.Message;

public class Diagram {

	private Aim aim;
	private List<Task> tasks = new ArrayList<>();
	private HashMap<String, Task> tasksByName = new HashMap<>();
	private List<Task> feasibleTask = new ArrayList<>();

	public Diagram(Task aim) {
		if (aim instanceof Aim) {
			this.setAim((Aim) aim);
			addTaskAndName(aim);
		} else {
			throw new ComplianceException(Message.ONE_AND_ONLY_ONE_AIM);
		}
	}

	private void addTaskAndName(Task aim) {
		tasks.add(aim);
		tasksByName.put(aim.getName(), aim);
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
					throw new ComplianceException(Message.ONE_AND_ONLY_ONE_AIM);
				}
				this.setAim((Aim) task);
				addTaskAndName(task);
			} else {
				addTaskAndName(task);
				if (!task.hasPrevious()) {
					feasibleTask.add(task);
				}
			}
		}
		sortList();
	}

	private void sortList() {
		Collections.sort(this.tasks);
		Collections.sort(this.feasibleTask);
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
			throw new ComplianceException(Message.TASK_SHOULD_HAVE_NEXT + tasksNameError.toString());
		}
	}

	public void addTask(Task task) {
		if (task instanceof Aim) {
			throw new ComplianceException(Message.ONE_AND_ONLY_ONE_AIM);
		} else {
			addTaskAndName(task);
			if (!task.hasPrevious()) {
				feasibleTask.add(task);
			}
			for (Task followers : task.getNext()) {
				if (feasibleTask.contains(followers)) {
					feasibleTask.remove(followers);
				}

			}
			verifyNotTaskAlone();
			sortList();
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

	public void finish(Task task) {
		if (tasks.contains(task)) {
			task.finish();
			if (feasibleTask.contains(task)) {
				feasibleTask.remove(task);
				addNextInFeasibleTask(task);
			}  
			else{
				removePreviousFromFeasibleTask(task);
			}
			Collections.sort(this.feasibleTask);
		} else {
			throw new ComplianceException(Message.TASK_NOT_IN_DIAGRAM);
		}
	}

	private void removePreviousFromFeasibleTask(Task task) {
		for(Task previous:task.getPrevious()){
			boolean remove=true;
			for(Task next:previous.getNext()){
				if(next!=task && !next.isFinish() ){
					remove=false;
				}
			}
			if(remove){
				feasibleTask.remove(previous);
			}
		}
	}
	private void addNextInFeasibleTask(Task task) {
		for(Task next:task.getNext()){
			boolean isFeasible=true;
			for(Task nextPrevious:next.getPrevious()){
				if(!nextPrevious.isFinish() ){
					isFeasible=false;
				}
			}
			if(isFeasible){
				feasibleTask.add(next);
			}
		}
	}

	public Task getTaskByName(String taskName) {
		return tasksByName.get(taskName);
	}
}
