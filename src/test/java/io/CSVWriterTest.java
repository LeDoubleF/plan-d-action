package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import core.Aim;
import core.Diagram;
import core.Task;
import exception.FileReaderException;
import exception.Message;

public class CSVWriterTest {
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
	public void writeFile() throws IOException {
		String fileName = "csvOutput.cvs";

		CSVWriter csvWriter = new CSVWriter(goodDiagram,fileName);
//		output file 
//		aim;
//		D;aim
//		C;aim
//		A;D
//		B;D;aim
		CSVReader csvReader = new CSVReader(fileName);

		String fileContent = csvReader.toString();

		assertTrue(fileContent.contains("aim/[]"));
		assertTrue(fileContent.contains("D/[aim]"));
		assertTrue(fileContent.contains("C/[aim]"));
		assertTrue(fileContent.contains("A/[D]"));
		assertTrue(fileContent.contains("B/[D, aim]"));

	}


	
}
