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
		super(ivle, map);
		
		// Read data from JSON.
		this.title = extractString("Title");
		this.description = extractString("Description");
		this.eventDateTime = extractString("EventDateTime");
		this.organizer = extractString("Organizer");
		this.venue = extractString("Venue");
		this.price = extractString("Price");
		this.agenda = extractString("Agenda");
		this.contact = extractString("Contact");
		this.ID = extractString("ID");
	}
	
	// }}}
}
