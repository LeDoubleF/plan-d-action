package io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import core.Aim;
import core.Diagram;
import core.Task;

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
			assertTrue(message.contains("erreur dans les donées du fichier"));
		}
	}
	@SuppressWarnings("unused")
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
		} catch (Exception aExp) {
			String message = aExp.getMessage();
			assertTrue(message.contains("Le fichier est introuvable"));
		}
	}
	
}
