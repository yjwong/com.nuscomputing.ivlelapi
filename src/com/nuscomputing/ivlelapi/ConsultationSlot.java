package com.nuscomputing.ivlelapi;

import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents a consultation slot.
 * @author yjwong
 */
public class ConsultationSlot extends IVLEObject {
	// {{{ properties
	
	/** Booking start date */
	public final DateTime bookingStartDate;
	
	/** Booking end date */
	public final DateTime bookingEndDate;
	
	/** Consultation start date */
	public final DateTime consultationStartDate;
	
	/** Consultation end date */
	public final DateTime consultationEndDate;
	
	/** Contact method */
	public final String contactMethod;
	
	/** Course code */
	public final String courseCode;
	
	/** Course ID */
	public final String courseId;
	
	/** Course name */
	public final String courseName;
	
	/** Duration */
	public final String duration;
	
	/** Have we signed up for this? */
	public final Boolean isSignedUp;
	
	/** Lecturer in charge of this slot */
	public final User lecturer;
	
	/** Sign up late */
	public final DateTime signUpDate;
	
	/** User who signed up for this slot */
	public final User signUpUser;
	
	/** Venue */
	public final String venue;
	
	// }}}
	// {{{ methods
	
	ConsultationSlot(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.ID = extractString("ID", map);
		this.bookingStartDate = extractDateTime("BookingStartDate", map);
		this.bookingEndDate = extractDateTime("BookingEndDate", map);
		this.consultationStartDate = extractDateTime("ConsultationStartDate", map);
		this.consultationEndDate = extractDateTime("ConsultationEndDate", map);
		this.contactMethod = extractString("ContactMethod", map);
		this.courseCode = extractString("CourseCode", map);
		this.courseId = extractString("CourseID", map);
		this.courseName = extractString("CourseName", map);
		this.duration = extractString("Duration", map);
		this.isSignedUp = extractBool("IsSignUp", map);
		this.lecturer = new User(ivle, (Map<?, ?>) map.get("Lecturer"));
		this.signUpDate = extractDateTime("SignUpDate", map);
		this.signUpUser = new User(ivle, (Map<?, ?>) map.get("SignUpUser"));
		this.venue = extractString("Venue", map);
	}
	
	// }}}
}
