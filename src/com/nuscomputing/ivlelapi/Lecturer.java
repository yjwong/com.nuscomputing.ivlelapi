package com.nuscomputing.ivlelapi;

import java.util.Map;

/**
 * Represents a lecturer.
 * @author Wong Yong Jie
 */
public class Lecturer extends IVLEObject {
	// {{{ properties
	
	/** Consultation hours */
	public final String consultHrs;
	
	/** Order */
	public final Integer order;
	
	/** Role */
	public final String role;
	
	/** User */
	public final User user;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	Lecturer() {
		throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
	}
	
	Lecturer(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.consultHrs = extractString("ConsultHrs", map);
		this.ID = extractString("ID", map);
		this.order = extractInt("Order", map);
		this.role = extractString("Role", map);
		this.user = new User(this.ivle, (Map<?, ?>) map.get("User"));
	}
	
	// }}}
}