package ca.mcgill.resto.ecse223.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import ca.mcgill.ecse223.resto.application.RestoAppApplication;
import ca.mcgill.ecse223.resto.controller.InvalidInputException;
import ca.mcgill.ecse223.resto.controller.RestoAppController;
import ca.mcgill.ecse223.resto.model.RestoApp;

class TestUI {
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
	void test() {
		try {
			RestoAppController.createTable(12, 12, 12, 12, 12, 12);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			fail();
		}
		 RestoApp r2 = RestoAppApplication.load();
		 checkResultTable(12, 12, 12 ,12, 12, 12 , r2); 
	}
	private void checkResultTable(int number, int x, int y ,int width, int length, int nbOfSeats , RestoApp r) {
		assertEquals(number, r.getTables().size());
		if (number > 0) {
			assertEquals(number, r.getTable(1).getNumber());
			assertEquals(x, r.getTable(1).getX());
			assertEquals(r, r.getTable(1).getRestoApp());
			assertEquals(y, r.getTable(1).getY());
			assertEquals(width, r.getTable(1).getWidth());
			assertEquals(length, r.getTable(1).getLength());
			assertEquals(nbOfSeats, r.getTable(1).getSeats().size());
		}
//		assertEquals(0, btms.getDrivers().size());
//		assertEquals(0, btms.getVehicles().size());
//		assertEquals(0, btms.getAssignments().size());
//		assertEquals(0, btms.getSchedule().size());
	}


}
