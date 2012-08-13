/**
 * Here we don't use the package com.nuscomputing.ivleapi, because this is
 * supposed to test the public API and we should have not any access to
 * package-private classes/variables/methods.
 */
package com.domain.subdomain;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nuscomputing.ivlelapi.IVLE;
import com.nuscomputing.ivlelapi.MyOrganizer;
import com.nuscomputing.ivlelapi.MyOrganizer.AcadSemesterInfo;
import com.nuscomputing.ivlelapi.MyOrganizer.SpecialDay;

/**
 * Test suite for IVLE base class.
 * @author Wong Yong Jie
 */

public class IVLETest {
	// {{{ properties
	
	/** The API key to use for testing */
	protected static String APIKEY = "UMvnz7ETKJ0oTWOX4zORt";
	
	/** The authentication token associated with the API key */
	protected static String AUTHTOKEN = "B83E39C72937B871084DF64BFD6CCADEB1D59240134476F45C3D6E9201748F4D346EA5A36BB0EFC39C889E8B1382F858A620CE5CC462A9BD4D994EB53CEEA152B90A754386262163C26405E140CF166D2732E05468F6F2B32B94535AE4D5D7AD73CF6BF012E733E6DFC5C8749056FEB9AE464FF53F3A9544823DBD0D23E8337D540D3532BBAC840EEFBB3542E35AC3C76A994D9F5B1DFA5507BFA2874FDFF43E04FD81AC9DA3876913F88966B0752CA546267413C5F2B4D0FBD37DEB163EE16440DCD678020894D51D1D8A80B0AFD79F";
	
	// }}}
	// {{{ methods
	
	/**
	 * Method: setUpBeforeClass
	 * Sets up this test case for use.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		// Tell user we need these details, unless they have already been set.
		if (IVLETest.APIKEY == null || IVLETest.APIKEY == null) {
			System.out.println("IVLE API Test Suite");
			System.out.println("-------------------");
			System.out.println("In order to run this test suite, you need to provide the following:");
			System.out.println("1) An API key");
			System.out.println("2) An authentication token");
			
			// Initialize scanner to read input.
			Scanner sc = new Scanner(System.in);
			System.out.print("API key: ");
			IVLETest.APIKEY = sc.next();
			System.out.print("Authentication token: ");
			IVLETest.AUTHTOKEN = sc.next();
		}
			
		// At this point we're ready to perform the tests.
	}
	
	/**
	 * Method: testIVLE
	 * This tests the class constructor.
	 */
	@Test
	public void testIVLE() throws Exception {
		@SuppressWarnings("unused")
		IVLE ivle = new IVLE(IVLETest.APIKEY, IVLETest.AUTHTOKEN);
		ivle = new IVLE(IVLETest.APIKEY, "A0088826", "j^jN6e77");
	}
	
	/**
	 * Method: testIVLEUninitialized
	 * An IllegalStateException should be returned if any attempt is made to
	 * call methods in the IVLE object when the object is still uninitialized.
	 */
	@Test
	public void testIVLEUninitialized() throws Exception {
		// Test those that don't require parameters.
		IVLE ivle = new IVLE();
		try { ivle.getModules(); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModulesStaff(); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModulesStudent(); throw new Exception(); } catch (IllegalStateException e) { };

		// getModule(courseID, ...)
		try { ivle.getModule("00000000-0000-0000-0000-000000000000"); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", true); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", false); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", 0); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", 100); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", 0, true); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", 0, false); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", 100, true); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModule("00000000-0000-0000-0000-000000000000", 100, false); throw new Exception(); } catch (IllegalStateException e) { };
		
		// getModules(duration)
		try { ivle.getModules(0); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModules(100); throw new Exception(); } catch (IllegalStateException e) { };
		
		// getModulesStaff(duration)
		try { ivle.getModulesStaff(0); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModulesStaff(100); throw new Exception(); } catch (IllegalStateException e) { };
		
		// getModuleStudent(duration)
		try { ivle.getModulesStudent(0); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getModulesStudent(100); throw new Exception(); } catch (IllegalStateException e) { };
		
		// getWorkbin(workbinID, ...)
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000"); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", true); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", false); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", 0); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", 100); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", 0, true); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", 0, false); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", 100, true); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.getWorkbin("00000000-0000-0000-0000-000000000000", 100, false); throw new Exception(); } catch (IllegalStateException e) { };
		
		// searchModules(criterion, ...)
		Map<String, String> criterion = new HashMap<String, String>();
		criterion.put("ModuleTitle", "Test");
		try { ivle.searchModules(criterion); throw new Exception(); } catch (IllegalStateException e) { };
		try { ivle.searchModules(criterion, true); throw new Exception(); } catch (IllegalStateException e) {	 };
		try { ivle.searchModules(criterion, false); throw new Exception(); } catch (IllegalStateException e) { };
		
		// validateLogin()
		try { ivle.validateLogin(); throw new Exception(); } catch (IllegalStateException e) { };
	}
	
	/**
	 * Method: testGetModules
	 * Tests the getModules method.
	 * None of the methods should return NULL, even if the list of modules
	 * is actually empty. In the case where there are no modules to report,
	 * the getModules method should return a Module[] with length 0.
	 */
	@Test
	public void testGetModules() throws Exception {
		// We are using an actual account here.
		IVLE ivle = new IVLE(IVLETest.APIKEY, IVLETest.AUTHTOKEN);
		assertTrue(ivle.getModules() != null);
		assertTrue(ivle.getModules(0) != null);
		assertTrue(ivle.getModules(100) != null);
		assertTrue(ivle.getModulesStaff() != null);
		assertTrue(ivle.getModulesStaff(0) != null);
		assertTrue(ivle.getModulesStaff(100) != null);
		assertTrue(ivle.getModulesStudent() != null);
		assertTrue(ivle.getModulesStudent(0) != null);
		assertTrue(ivle.getModulesStudent(100) != null);
	}
	
	/**
	 * Method: testGetSpecialDays
	 * <p>
	 * Test to get the special days on IVLE.
	 */
	@Test
	public void testGetSpecialDays() throws Exception {
		IVLE ivle = new IVLE(IVLETest.APIKEY, IVLETest.AUTHTOKEN);
		MyOrganizer organizer = ivle.getMyOrganizer();
		SpecialDay[] days = organizer.getSpecialDays("13-Aug-2012", "13-Aug-2013");
		for (SpecialDay day : days) {
			System.out.print("description = ".concat(day.description).concat(", "));
			System.out.print("type = ".concat(day.type).concat(", "));
			System.out.print("startDate = ".concat(day.startDate.toString()).concat(", "));
			System.out.println("endDate = ".concat(day.endDate.toString()));
		}
	}
	
	/**
	 * Method: testGetAcadSemesterInfo
	 * <p>
	 * Test to get the academic semester information.
	 */
	@Test
	public void testGetAcadSemesterInfo() throws Exception {
		IVLE ivle = new IVLE(IVLETest.APIKEY, IVLETest.AUTHTOKEN);
		MyOrganizer organizer = ivle.getMyOrganizer();
		AcadSemesterInfo[] info = organizer.getAcadSemesterInfo("2012/2013", "1");
		for (AcadSemesterInfo acadSem : info) {
			System.out.print("acadYear = ".concat(acadSem.acadYear).concat(", "));
			System.out.print("evenOddWeek = ".concat(acadSem.evenOddWeek).concat(", "));
			System.out.print("lectureStartDate = ".concat(acadSem.lectureStartDate.toString()).concat(", "));
			System.out.print("semester = ".concat(acadSem.semester).concat(", "));
			System.out.print("semesterEndDate = ".concat(acadSem.semesterEndDate.toString()).concat(", "));
			System.out.print("semesterStartDate = ".concat(acadSem.semesterStartDate.toString()).concat(", "));
			System.out.print("tutorialStartDate = ".concat(acadSem.tutorialStartDate.toString()).concat(", "));
			System.out.print("typeName = ".concat(acadSem.typeName).concat(", "));
			System.out.print("weekTypeEndDate = ".concat(acadSem.weekTypeEndDate.toString()).concat(", "));
			System.out.println("weekTypeStartDate = ".concat(acadSem.weekTypeStartDate.toString()));
		}
	}
	
	/**
	 * Method: testEventPersonal
	 * <p>
	 * Test to add a personal event.
	 */
	@Test
	public void testEventPersonal() throws Exception {
		IVLE ivle = new IVLE(IVLETest.APIKEY, IVLETest.AUTHTOKEN);
		MyOrganizer organizer = ivle.getMyOrganizer();
		String id = organizer.addEventPersonal("Test", null, null, null, null, null, null, null);
		System.out.println("event added with id = " + id);
		
		// Remove the event.
		Boolean result = organizer.deleteEventPersonal(id, true);
		System.out.println("event deleted with result = " + result.toString());
	}
	
	/**
	 * Method: dump
	 * Utility method to dump the state of an IVLE object.
	 */
	public static void dump(Object obj) {
		Field[] f = obj.getClass().getDeclaredFields();
		for (Field f_item : f) {
			f_item.setAccessible(true);
			try {
				System.out.println(f_item.getName() + ": " + f_item.get(obj).toString());
			} catch (Exception e) {
				// Do nothing.
			}
		}
	}
	
	// }}}
}