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
		super(ivle, map);
		
		// Read data from JSON.
		this.ID = extractString("ID");
		this.bookingStartDate = extractDateTime("BookingStartDate");
		this.bookingEndDate = extractDateTime("BookingEndDate");
		this.consultationStartDate = extractDateTime("ConsultationStartDate");
		this.consultationEndDate = extractDateTime("ConsultationEndDate");
		this.contactMethod = extractString("ContactMethod");
		this.courseCode = extractString("CourseCode");
		this.courseId = extractString("CourseID");
		this.courseName = extractString("CourseName");
		this.duration = extractString("Duration");
		this.isSignedUp = extractBool("IsSignUp");
		this.lecturer = new User(ivle, (Map<?, ?>) map.get("Lecturer"));
		this.signUpDate = extractDateTime("SignUpDate");
		this.signUpUser = new User(ivle, (Map<?, ?>) map.get("SignUpUser"));
		this.venue = extractString("Venue");
	}
	
	// }}}
}
