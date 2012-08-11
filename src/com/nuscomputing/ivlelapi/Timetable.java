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
	public class Slot extends IVLEObject {
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
			super(ivle, map);
			this.acadYear = extractString("AcadYear");
			this.semester = extractString("Semester");
			this.startTime = extractString("StartTime");
			this.endTime = extractString("EndTime");
			this.moduleCode = extractString("ModuleCode");
			this.classNo = extractString("ClassNo");
			this.lessonType = extractString("LessonType");
			this.venue = extractString("Venue");
			this.dayCode = extractString("DayCode");
			this.dayText = extractString("DayText");
			this.weekCode = extractString("WeekCode");
			this.weekText = extractString("WeekText");
		}
		
		// }}}
	}
	
	// }}}
}
