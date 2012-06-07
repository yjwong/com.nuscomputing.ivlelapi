package com.nuscomputing.ivlelapi;

import java.util.Map;

/**
 * A data structure consisting academic year, semester and name.
 * @author Wong Yong Jie
 */
public class AcadYearSem {
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
	AcadYearSem() {
		throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
	}
	
	AcadYearSem(IVLE ivle, Map<?, ?> map) {
		// Read data from JSON.
		this.acadYear = IVLEObject.extractString("AcadYear", map);
		this.acadSemester = IVLEObject.extractString("AcadSemester", map);
		this.acadSemesterName = IVLEObject.extractString("AcadSemesterName", map);
		this.isCurrent = IVLEObject.extractBool("isCurrent", map);
	}
	
	// }}}
}
