package core;

import java.util.Collections;
import java.util.List;

import exception.Message;

public class Aim extends Task{
	
	public Aim(String name) {
		super(name);
	}
	
	@Override
	public List<Task> getNext() {
		return Collections.emptyList();
	}
	
	@Override
	public void addNext(Task next) {
		 throw new UnsupportedOperationException(Message.ERROR_NO_NEXT);	
	}

	@Override
	public int getWeight() {
		return 0;
	}
	
}
