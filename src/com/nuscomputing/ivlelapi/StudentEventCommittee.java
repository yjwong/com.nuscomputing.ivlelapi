package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

/**
 * Represents a student committee.
 * @author Wong Yong Jie
 */
public class StudentEventCommittee extends IVLEObject {
	// {{{ properties
	
	/** The name of the committee */
	public final String title;
	
	/** Badge to be used for this committee */
	public final Integer badgeCategory;
	
	/** Store the map, for we will use it later */
	private final Map<?, ?> map;
	
	/** Flags */
	public static final int FLAG_HAS_EVENTS = 1;
	public static final int FLAG_TITLE_ONLY = 2;
	
	// }}}
	// {{{ methods
	
	StudentEventCommittee(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = map;
		this.title = extractString("Title", map);
		this.badgeCategory = extractInt("BadgeCategory", map);
	}
	
	
	/**
	 * Method: getEvents
	 * <p>
	 * Gets all the events associated with this student committee.
	 * If there are no events, this method returns an array of length 0.
	 * XXX: Untested method. To be tested.
	 * 
	 * @return StudentEvent[]
	 */
	public StudentEvent[] getEvents() throws JSONParserException,
	FailedLoginException, NetworkErrorException {
		// Obtain the list of events.
		if (this.hasFlag(FLAG_HAS_EVENTS)) {
			List<?> studentEventList = ((List<?>) this.map.get("Events"));
			
			// Create a StudentEvent object for each event in the list.
			StudentEvent[] se = new StudentEvent[studentEventList.size()];
			for (int i = 0; i < studentEventList.size(); i++) {
				se[i] = new StudentEvent(this.ivle, (Map<?, ?>) studentEventList.get(i));
			}
			
			return se;
			
		} else {
			// We need to send another request.
			return this.ivle.getStudentEventsByCommittee(this.ID, this.hasFlag(FLAG_TITLE_ONLY));
		}
	}
	
	// }}}
}