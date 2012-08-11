package com.nuscomputing.ivlelapi;

import java.util.Map;

/**
 * Represents an IVLE group.
 * @author Wong Yong Jie
 */
public class Group extends IVLEObject {
	// {{{ properties
	
	/** Academic year */
	public final String acadYear;
	
	/** Class group description */
	public final String classGroupDesc;
	
	/** Class group ID */
	public final String classGroupId;
	
	/** Number of students enrolled in this group */
	public final Integer countStudentsEnrolled;
	
	/** Course ID */
	public final String courseId;
	
	// }}}
	// {{{ methods
	
	Group(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.acadYear = extractString("AcadYear");
		this.classGroupDesc = extractString("ClassGroupDesc");
		this.classGroupId = extractString("ClassGroupID");
		this.countStudentsEnrolled = extractInt("CountStudentsEnrolled");
		this.courseId = extractString("CourseID");
	}
	
	// }}}
}
