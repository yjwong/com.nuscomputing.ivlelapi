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

/**
 * Test suite for IVLE base class.
 * @author Wong Yong Jie
 */

public class IVLETest {
	// {{{ properties
	
	/** The API key to use for testing */
	protected static String APIKEY = "UMvnz7ETKJ0oTWOX4zORt";
	
	/** The authentication token associated with the API key */
	protected static String AUTHTOKEN = "E575FFC2FA1EAA40AA7B4DEE2AA872B44EC29D5DE3C1C5224EAEA2F9D3F1C334830839A33A71C78943E0F2014F49F26EB76BB03A253040E4D57604083F1F1110A3ACB951FFF3D2FBB51152BBFC00092F54ECCB32D7868B646F95AAA2D8C8647D356DC14591E1F8EF33FA41EF4DFD7D36990C11909BB1C2B4E61F8F932A7FFE818FE1ACA4AA7841DE4A389DAB48B7FFEFC04F4665211CEC2583A7B100793053DEC67C384046E16864315B60677BC0D9DBF95FC1344952CA2F73F8686F29A2E3F06977BC4B6C9E86A32E4CD9B895E90335";
	
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
		ivle = new IVLE(IVLETest.APIKEY, "A0088826", "j^jN6e78");
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