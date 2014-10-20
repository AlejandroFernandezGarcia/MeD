package ampa.sa.test;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ampa.sa.student.FamilyService;
import ampa.sa.util.exceptions.InstanceNotFoundException;


public class TestAlumnos {

	private FamilyService familyService = FamilyService.getInstance();

	
	@Before
	public void before() throws ParseException {
		new DatosMock();
	}
	
	@Test
	public void getExpensesHousehold() {
		
		BigDecimal expenses = null;
		try {
			
			//expenses = familyService.getHouseholdExpenses(familyService.findHousehold(1));
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//assertEquals(expenses,0);
	}

}
