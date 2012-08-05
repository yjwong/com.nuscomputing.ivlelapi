package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

/**
 * Represents a timetable.
 * @author yjwong
 */
public class Timetable {
	// {{{ properties
	
	/** Timetable slots in this timetable */
	public final Slot[] slots;
	
	// }}}
	// {{{ methods
	
	Timetable(IVLE ivle, List<?> slotList) {
		// Create the slots.
		this.slots = new Slot[slotList.size()];
		for (int i = 0; i < slotList.size(); i++) {
			this.slots[i] = new Slot(ivle, (Map<?, ?>) slotList.get(i));
		}
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a timetable slot.
	 * @author Wong Yong Jie
	 */
	public class Slot {
		// {{{ properties
		
		/** Academic year */
		public final String acadYear;
		
		/** Semester */
		public final String semester;
		
		/** Starting time */
		public final String startTime;
		
		/** Ending time */
		public final String endTime;
		
		/** Module code */
		public final String moduleCode;
		
		/** Class number */
		public final String classNo;
		
		/** Lesson type */
		public final String lessonType;
		
		/** Venue */
		public final String venue;
		
		/** Day code */
		public final String dayCode;
		
		/** Day text */
		public final String dayText;
		
		/** Week code */
		public final String weekCode;
		
		/** Week text */
		public final String weekText;
		
		// }}}
		// {{{ methods
		
		Slot(IVLE ivle, Map<?, ?> map) {
			this.acadYear = IVLEObject.extractString("AcadYear", map);
			this.semester = IVLEObject.extractString("Semester", map);
			this.startTime = IVLEObject.extractString("StartTime", map);
			this.endTime = IVLEObject.extractString("EndTime", map);
			this.moduleCode = IVLEObject.extractString("ModuleCode", map);
			this.classNo = IVLEObject.extractString("ClassNo", map);
			this.lessonType = IVLEObject.extractString("LessonType", map);
			this.venue = IVLEObject.extractString("Venue", map);
			this.dayCode = IVLEObject.extractString("DayCode", map);
			this.dayText = IVLEObject.extractString("DayText", map);
			this.weekCode = IVLEObject.extractString("WeekCode", map);
			this.weekText = IVLEObject.extractString("WeekText", map);
		}
		
		// }}}
	}
	
	// }}}
}
