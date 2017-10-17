package io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import core.Diagram;
import core.Task;

public class CSVWriter extends CsvIO {

	public CSVWriter(Diagram diagram, String fileName) throws IOException {
		super();
		try (FileWriter fileWriter = new FileWriter(fileName)) {

			List<Task> a = diagram.getTasks();

			ListIterator<Task> li = a.listIterator(a.size());

			// Iterate in reverse.
			while (li.hasPrevious()) {
				Task task = li.previous();

				fileWriter.append(task.getName());
				fileWriter.append(COMMA_DELIMITER);

				List<Task> nextList = task.getNext();
				int nextListSize = nextList.size();
				for (int i = 0; i < nextListSize; i++) {
					Task next = nextList.get(i);
					fileWriter.append(next.getName());
					if (i < nextListSize - 1) {
						fileWriter.append(COMMA_DELIMITER);
					}
				}
				fileWriter.append(NEW_LINE_SEPARATOR);
			}

		}
	}

}
