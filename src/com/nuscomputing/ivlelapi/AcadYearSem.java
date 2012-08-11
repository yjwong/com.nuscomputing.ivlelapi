package com.nuscomputing.ivlelapi;

import java.util.Map;

/**
 * A data structure consisting academic year, semester and name.
 * @author Wong Yong Jie
 */
public class AcadYearSem extends IVLEObject {
	// {{{ properties
	
	/** Academic year */
	public final String acadYear;
	
	/** Academic semester */
	public final String acadSemester;
	
	/** Academic semester name */
	public final String acadSemesterName;
	
	/** isCurrent (undocumented) */
	public final Boolean isCurrent;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	AcadYearSem(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.acadYear = extractString("AcadYear");
		this.acadSemester = extractString("AcadSemester");
		this.acadSemesterName = extractString("AcadSemesterName");
		this.isCurrent = extractBool("isCurrent");
	}
	
	// }}}
}
