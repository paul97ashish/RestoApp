package ca.mcgill.resto.ecse223.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;



import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.model.RestoApp;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;

public class RestoAppControllerTest {
	
	
	@BeforeClass
	public static void setUpOnce() {
		String filename = "testdata.resto";
		RestoAppApplication.setFilename(filename);
		File f = new File(filename);
		f.delete();
	}
	
	@Before
	public void setUp() {
		// clear all data
		RestoApp r = RestoAppApplication.getr();
		r.delete();
	}
	
	@Test
	public void testCreateTableSuccess() {
		RestoApp r = RestoAppApplication.getr();
		int number = 1;
		int x=2;
		int y=3;
		int width = 4;
		int length = 6;
		int numberOfSeats = 8;
		
		try {
			RestoAppController.createTable(number, x, y, width, length, numberOfSeats);
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}
		
		// check model in memory
		checkResultTable(number, x,  y, width, length, numberOfSeats,r);
	}
	
	@Test
	public void testCreateTableNotPositive() {
		RestoApp r = RestoAppApplication.getr();		
		int number = 0;
		int x=-1;
		int y=-1;
		int width = 0;
		int length = 0;
		int numberOfSeats = 0;

		String error = null;
		try {
			RestoAppController.createTable(number, x, y, width, length, numberOfSeats);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("The number of the table must be greater than zero. The x coordinate of the table must be greater than zero. The y coordinate of the table must be greater than zero. The width of the table must be greater than zero. The length of the table must be greater than zero. The numberOfSeats of the table must be greater than zero.", error);
//		assertEquals("The x coordinate of the table must be greater than zero. ", error);
//		assertEquals("The y coordinate of the table must be greater than zero. ", error);
//		
//		assertEquals("The width of the table must be greater than zero.  ", error);
//		assertEquals("The length of the table must be greater than zero.  ", error);
//		assertEquals("The numberOfSeats of the table must be greater than zero.  ", error);
		
		
		// check no change in memory
		checkResultTable(number, x,  y, width, length, numberOfSeats,r);
	}
	
	@Test
	public void testCreateTableDuplicate() {
		RestoApp r = RestoAppApplication.getr();		
		int number = 1;
		int x=2;
		int y=3;
		int width = 4;
		int length = 6;
		int numberOfSeats = 8;
		
		try {
			RestoAppController.createTable(number, x, y, width, length, numberOfSeats);
		} catch (InvalidInputException e) {
			// check that no error occurred
			fail();
		}

		String error = null;
		try {
			RestoAppController.createTable(number, 2, 5, 3, 87, 7);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("A table with this number already exists. Please use a different number.", error);
		// check no change in memory
		checkResultTable(number, x,  y, width, length, numberOfSeats,r);
	}
	
	@Test
	public void testCreateTableOverlap() {
		RestoApp r = RestoAppApplication.getr();		
		int number = 3;
		int x=100;
		int y=100;
		int width = 1;
		int length = 1;
		int numberOfSeats = 3;
		
		try {
			RestoAppController.createTable(number, x, y, width, length, numberOfSeats);
		} catch (InvalidInputException e) {
			
			// check that no error occurred
			fail();
		}

		String error = null;
		try {
			RestoAppController.createTable(number, x, y, width, length, numberOfSeats);
		} catch (InvalidInputException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("A table already exists at this location", error);
		// check no change in memory
		checkResultTable(number, x,  y, width, length, numberOfSeats,r);
	}

	private void checkResultTable(int number, int x, int y ,int width, int length, int nbOfSeats , RestoApp r) {
		assertEquals(number, r.getTables().size());
		if (number > 0) {
			assertEquals(number, r.getTable(0).getNumber());
			assertEquals(x, r.getTable(0).getX());
			assertEquals(r, r.getTable(0).getRestoApp());
			assertEquals(y, r.getTable(0).getY());
			assertEquals(width, r.getTable(0).getWidth());
			assertEquals(length, r.getTable(0).getLength());
			assertEquals(nbOfSeats, r.getTable(0).getSeats().size());
		}

	}


}
