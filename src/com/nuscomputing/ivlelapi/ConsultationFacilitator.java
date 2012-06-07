package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

/**
 * Represents a consultation facilitator.
 * @author yjwong
 */
public class ConsultationFacilitator extends IVLEObject {
	// {{{ properties
	
	/** Is this facilitator also a manager? */
	public final Boolean isManager;
	
	/** The lecturer user corresponding to this facilitator */
	public final User lecturer;
	
	/** Save the map, since we'll use it later */
	public final Map<?, ?> map;

	/** Flags */
	public static final int FLAG_INCLUDE_SLOTS = 1;
	
	// }}}
	// {{{ methods
	
	ConsultationFacilitator(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = map;
		this.ID = extractString("ID", map);
		this.lecturer = new User(ivle, (Map<?, ?>) map.get("Lecturer"));
		this.isManager = extractBool("IsManager", map);
	}
	
	/**
	 * Method: getSlots
	 * <p>
	 * Gets the consultation timeslots for this facilitator.
	 */
	public ConsultationSlot[] getSlots() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check if we already have the slots.
		if (this.hasFlag(FLAG_INCLUDE_SLOTS)) {
			List<?> consultationSlotList = (List<?>) this.map.get("Slots");
			ConsultationSlot[] cs = new ConsultationSlot[consultationSlotList.size()];
			for (int i = 0; i < consultationSlotList.size(); i++) {
				cs[i] = new ConsultationSlot(this.ivle, (Map<?, ?>) consultationSlotList.get(i));
			}
			
			return cs;
			
		} else {
			// Not available yet, request for the slots.
			ConsultationSlot[] csTaken = this.ivle.getConsultationSlots(this.ID, 'S');
			ConsultationSlot[] csAvail = this.ivle.getConsultationSlots(this.ID, 'A');
			ConsultationSlot[] cs = new ConsultationSlot[csTaken.length + csAvail.length];
			System.arraycopy(csTaken, 0, cs, 0, csTaken.length);
			System.arraycopy(csAvail, 0, cs, csTaken.length, csAvail.length);
			return cs;
		}
	}
	
	// }}}
}
