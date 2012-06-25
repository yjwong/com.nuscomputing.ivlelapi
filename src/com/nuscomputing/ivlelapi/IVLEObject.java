package com.nuscomputing.ivlelapi;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

/**
 * Base class for all IVLE components.
 * @author Wong Yong Jie
 */

public class IVLEObject {
	// {{{ properties
	
	/** ID of this object */
	public String ID;
	
	/** Reference to the IVLE class */
	protected IVLE ivle;
	
	/** Flags */
	private Set<Integer> flags = new HashSet<Integer>();
	
	// }}}
	// {{{ methods
	
	/**
	 * Method: getFlags
	 * <p>
	 * Gets all the flags for this IVLE object.
	 */
	public Set<Integer> getFlags() {
		return this.flags;
	}
	
	/**
	 * Method: hasFlag
	 * <p>
	 * Checks if this IVLE object has the specified flag.
	 */
	public boolean hasFlag(int flagId) {
		return this.flags.contains(flagId);
	}
	
	/**
	 * Method: setFlag
	 * <p>
	 * Sets a flag for this IVLE object.
	 */
	public void setFlag(int flagId) {
		this.flags.add(flagId);
	}
	
	/**
	 * Method: extractInt
	 * Utility method to extract integer values from the parsed JSON.
	 */	
	public static Integer extractInt(String key, Map<?, ?> map) {
		// Sanity checks.
		if (map == null) {
			throw new IllegalArgumentException("map was null, key = " + key);
		}
		
		if (key == null) {
			throw new IllegalArgumentException("key was null");
		}
		
		// Attempt to parse the integer.
		try {
			return map.get(key) == null ? null : Integer.parseInt(map.get(key).toString());
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	/**
	 * Method: extractDouble
	 * Utility method to extract double values from the parsed JSON.
	 */
	public static Double extractDouble(String key, Map<?, ?> map) {
		// Sanity checks.
		if (map == null) {
			throw new IllegalArgumentException("map was null, key = " + key);
		}
		
		if (key == null) {
			throw new IllegalArgumentException("key was null");
		}
		
		// Attempt to parse the double.
		return map.get(key) == null ? null : Double.parseDouble(map.get(key).toString());
	}
	
	/**
	 * Method: extractBool
	 * Utility method to extract boolean values from the parsed JSON.
	 */
	public static Boolean extractBool(String key, Map<?, ?> map) {
		// Sanity checks.
		if (map == null) {
			throw new IllegalArgumentException("map was null, key = " + key);
		}
		
		if (key == null) {
			throw new IllegalArgumentException("key was null");
		}
		
		// Return the boolean value.
		return map.get(key) == null ? null : new Boolean(map.get(key).toString());
	}
	
	/**
	 * Method: extractString
	 * Utility method to extract string values from the parsed JSON.
	 */
	public static String extractString(String key, Map<?, ?> map) {
		// Sanity checks.
		if (map == null) {
			throw new IllegalArgumentException("map was null, key = " + key);
		}
		
		if (key == null) {
			throw new IllegalArgumentException("key was null");
		}
		
		// Return the string value.
		return map.get(key) == null ? null : map.get(key).toString();
	}
	
	/**
	 * Method: extractDateTime
	 * Utility method to extract DateTime values from the parsed JSON.
	 */
	public static DateTime extractDateTime(String key, Map<?, ?> map) {
		// Sanity checks.
		if (map == null) {
			throw new IllegalArgumentException("map was null, key = " + key);
		}
		
		if (key == null) {
			throw new IllegalArgumentException("key was null");
		}
		
		// This uses the .NET serialized date format...
		if (map.get(key) == null) { 
			return null;
		}
		
		String val = map.get(key).toString();
		int i1 = val.indexOf("(");
		int i2 = val.indexOf(")");
		val = val.substring(i1 + 1, i2);
		val = val.replace("+0800", "");
		Long l = Long.valueOf(val);
		return new DateTime(l);
	}
	
	// }}}
}
