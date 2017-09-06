package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import exception.ComplianceException;

public class Task implements Comparable<Task> {
	private String name;
	private double progress;

	private int weight;
	private Type type;
	private List<Task> previous = new ArrayList<>();
	private List<Task> next = new ArrayList<>();

	public Task(String name, Type type) {
		super();
		if (name == null || name.trim().equals(""))
			throw new ComplianceException("A task should have a name");
		this.name = name.trim();
		this.type = type;
	}

	public Task(String name) {
		this(name, Type.BINARY);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
		if(isFinalize()){
			for(Task followers:getNext()){
				int nbPortion=followers.previous.size()+1;
				double increment=100/nbPortion+	followers.getProgress();
				followers.setProgress(increment);
				followers.previous.remove(this);
			}
			
		}
	}

	public void finalize() {
		setProgress(100);
		
	}

	public boolean isFinalize() {
		if (progress > 75) {
			return true;
		}
		return false;

	}

	public int getWeight() {
		return weight;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Task> getPrevious() {
		return previous;
	}

	public List<Task> getNext() {
		return next;
	}

	public void addNext(Task task) {
		if (!this.next.contains(task)) {
			next.add(task);
			task.addPrevious(this);
		}
	}

	protected void addPrevious(Task task) {
		if (!this.previous.contains(task)) {
			previous.add(task);
			verifyNoCycle(task);
			int variation = getWeight() + 1;
			task.addPrevious(variation);
		}
	}

	private void verifyNoCycle(Task task) {
		List<Task> priors = new ArrayList<>();
		if (!hasFinal(task, priors)) {
			throw new ComplianceException("Shouldn't have cycle");
		}
	}

	protected void addPrevious(int variation) {
		this.weight = this.weight + variation;
		for (Task relative : previous) {
			relative.addPrevious(variation);
		}
	}

	private boolean hasFinal(Task task, List<Task> priors) {
		if (priors.contains(task))
			return false;
		if (task.hasNext()) {
			priors.add(task);
			boolean result = true;
			for (Task son : task.getNext()) {
				result = result && hasFinal(son, priors);
			}
			priors.remove(task);
			return result;
		} else {
			return true;
		}
	}

	public boolean hasPrevious() {
		return previous.isEmpty() ? false : true;
	}

	public boolean hasNext() {
		return next.isEmpty() ? false : true;
	}

	public int compareTo(Task o) {
		final int before = -1;
		final int after = 1;

		final boolean thisHasPrevious = this.hasPrevious();
		final boolean oHasPrevious = o.hasPrevious();
		if (thisHasPrevious == oHasPrevious) {
			if (o.getWeight() == this.getWeight())
				return o.getName().compareTo(getName());
			else
				return o.getWeight() - this.getWeight();
		} else {
			return thisHasPrevious ? after : before;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, progress, weight, type, previous, next);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Task)) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(name, other.name) && Objects.equals(progress, other.progress)
				&& Objects.equals(type, other.type);
	}

}
