package com.nuscomputing.ivlelapi;

import java.util.Map;

/**
 * Represents a student event.
 * @author Wong Yong Jie
 */
public class StudentEvent extends IVLEObject {
	// {{{ properties
	
	/** Title */
	public final String title;
	
	/** Description */
	public final String description;
	
	/** The time and date */
	public final String eventDateTime;
	
	/** Organizer */
	public final String organizer;
	
	/** Venue */
	public final String venue;
	
	/** Price */
	public final String price;
	
	/** Agenda */
	public final String agenda;
	
	/** Contact details */
	public final String contact;
	
	/** Flags */
	public static final int FLAG_TITLE_ONLY = 1;
	
	// }}}
	// {{{ methods
	
	StudentEvent(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.title = extractString("Title", map);
		this.description = extractString("Description", map);
		this.eventDateTime = extractString("EventDateTime", map);
		this.organizer = extractString("Organizer", map);
		this.venue = extractString("Venue", map);
		this.price = extractString("Price", map);
		this.agenda = extractString("Agenda", map);
		this.contact = extractString("Contact", map);
		this.ID = extractString("ID", map);
	}
	
	// }}}
}
