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
	Lecturer(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.consultHrs = extractString("ConsultHrs");
		this.ID = extractString("ID");
		this.order = extractInt("Order");
		this.role = extractString("Role");
		this.user = new User(this.ivle, (Map<?, ?>) map.get("User"));
	}
	
	// }}}
}