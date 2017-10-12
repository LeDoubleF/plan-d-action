package io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

import core.Diagram;
import core.Task;
import exception.FileReaderException;
import exception.Message;

public class CSVReaderTest {

	@Test
	public void readGoodFile() throws IOException {
		String fileName = "csvReaderEntry.cvs";
		String filePath = getClass().getClassLoader().getResource(fileName).getFile();

		CSVReader csvReader = new CSVReader(filePath);

		String fileContent = csvReader.toString();
		assertTrue(fileContent.contains("A/[D]"));
		assertTrue(fileContent.contains("B/[D]"));
		assertTrue(fileContent.contains("C/[aim]"));
		assertTrue(fileContent.contains("aim/[]"));
		assertTrue(fileContent.contains("D/[aim]"));

		Diagram goodDiagram = csvReader.getDiagram();
		Collection<Task> feasibleTask = goodDiagram.getFeasibleTask();
		assertEquals(3, feasibleTask.size());
	}

	@SuppressWarnings("unused")
	@Test
	public void readWrongFile() throws IOException {
		String fileName = "csvReaderWrongEntry.cvs";
		String filePath = getClass().getClassLoader().getResource(fileName).getFile();

		try {
			CSVReader csvReader = new CSVReader(filePath);

			fail("Should throw exception when the file is not ok");
		} catch (Exception aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains(Message.FILE_CONTENT_KO));
		}
	}
	@Test
	public void readEmptyFile() throws IOException {
		String fileName = "csvReaderEmptyEntry.cvs";
		String filePath = getClass().getClassLoader().getResource(fileName).getFile();
			CSVReader csvReader = new CSVReader(filePath);
			
			String fileContent = csvReader.toString();
			assertEquals("", fileContent);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void readNoFile() throws IOException {
		String fileName = "noFile.cvs";
		
		try {
			CSVReader csvReader = new CSVReader(fileName);
			
			fail("Should throw exception when the file is not ok");
		} catch (FileReaderException aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains(Message.NO_FILE));
		}
	}
	
}
