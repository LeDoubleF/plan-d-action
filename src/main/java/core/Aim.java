package core;

import java.util.Collections;
import java.util.List;

public class Aim extends Task{
	
	private static final String ERROR_NO_NEXT = "a aim can't have next";

	public Aim(String name) {
		super(name);
	}
	
	@Override
	public List<Task> getNext() {
		return Collections.emptyList();
	}
	
	@Override
	public void addNext(Task next) {
		 throw new UnsupportedOperationException(ERROR_NO_NEXT);	
	}

	@Override
	public int getWeight() {
		return 0;
	}
	
}
