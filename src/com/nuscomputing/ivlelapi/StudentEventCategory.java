package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

/**
 * Represents a student event category.
 * @author Wong Yong Jie
 */
public class StudentEventCategory extends IVLEObject {
	// {{{ properties
	
	/** Category title */
	public final String title;
	
	/** The badge for this category */
	public final Integer badgeCategory;
	
	/** Flags */
	public static final int FLAG_INCLUDE_EVENTS = 1;
	public static final int FLAG_TITLE_ONLY = 2;
	
	// }}}
	// {{{ methods
	
	StudentEventCategory(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.title = extractString("Title");
		this.badgeCategory = extractInt("BadgeCategory");
		this.ID = extractString("ID");
	}
	
	/**
	 * Method: getEvents
	 * <p>
	 * Gets all the events associated with this student event category.
	 * If there are no events, this method returns an array of length 0.
	 * XXX: Untested method. To be tested.
	 * 
	 * @return StudentEvent[]
	 */
	public StudentEvent[] getEvents() throws JSONParserException,
		FailedLoginException, NetworkErrorException {
		// Obtain the list of events.
		if (this.hasFlag(FLAG_INCLUDE_EVENTS)) {
			List<?> studentEventList = ((List<?>) this.map.get("Events"));
			
			// Create a StudentEvent object for each event in the list.
			StudentEvent[] se = new StudentEvent[studentEventList.size()];
			for (int i = 0; i < studentEventList.size(); i++) {
				se[i] = new StudentEvent(this.ivle, (Map<?, ?>) studentEventList.get(i));
			}
			
			return se;
			
		} else {
			// We need to send another request.
			return this.ivle.getStudentEventsByCategory(this.ID, this.hasFlag(FLAG_TITLE_ONLY));
		}
	}
	
	// }}}
}