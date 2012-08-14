package com.nuscomputing.ivlelapi;

import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents an IVLE project.
 * @author yjwong
 */
public class Project extends IVLEObject {
	// {{{ properties
	
	/** Guidelines */
	public final String guidelines;
	
	/** Is the current user the manager of the group? */
	public final Boolean isManager;
	
	/** The title of the group */
	public final String title;
	
	/** Flags */
	public static final int FLAG_INCLUDE_GROUPS = 1;
	
	// }}}
	// {{{ methods
	
	Project(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.guidelines = extractString("Guidelines");
		this.isManager = extractBool("IsManager");
		this.title = extractString("Title");
	}
	
	/**
	 * Method: getGroups
	 * <p>
	 * Gets the groups associated with this project.
	 */
	
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents an IVLE project group.
	 * @author yjwong
	 */
	public class Group extends IVLEObject {
		// {{{ properties
		
		/** The course code */
		public final String courseCode;
		
		/** Current enrollment */
		public final Integer currEnrollment;
		
		/** Description */
		public final String description;
		
		/** Is the user currently in the group? */
		public final Boolean isUserInGroup;
		
		/** Maximum enrollment */
		public final Integer maxEnrollment;
		
		/** Name */
		public final String name;
		
		/** Project ID */
		public final String projectID;
		
		/** Project title */
		public final String projectTitle;
		
		/** Self-enrolled? */
		public final Boolean selfEnrol;
		
		/** Self-enrollment expiry date */
		public final DateTime selfEnrolExpiryDate;
		
		/** Self-enrollment opening date */
		public final DateTime selfEnrolOpenDate;
		
		// }}}
		// {{{ methods
		
		Group(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.courseCode = extractString("CourseCode");
			this.currEnrollment = extractInt("CurrEnrollment");
			this.description = extractString("Description");
			this.isUserInGroup = extractBool("IsUserInGroup");
			this.maxEnrollment = extractInt("MaxEnrollment");
			this.name = extractString("Name");
			this.projectID = extractString("ProjectID");
			this.projectTitle = extractString("ProjectTitle");
			this.selfEnrol = extractBool("SelfEnrol");
			this.selfEnrolExpiryDate = extractDateTime("SelfEnrolExpiryDate");
			this.selfEnrolOpenDate = extractDateTime("SelfEnrolOpenDate");
		}
		
		// }}}
	}
	
	// }}}
}
