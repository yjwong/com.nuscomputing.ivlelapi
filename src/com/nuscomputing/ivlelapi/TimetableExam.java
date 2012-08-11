package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents an exam timetable.
 * @author yjwong
 */
public class TimetableExam {
	// {{{ properties
	
	/** Timetable slots in this timetable */
	public final Slot[] slots;
	
	// }}}
	// {{{ methods
	
	TimetableExam(IVLE ivle, List<?> slotList) {
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
		
		/** Module ID */
		public final String courseId;
		
		/** Academic year */
		public final String acadYear;
		
		/** Semester */
		public final String semester;
		
		/** Exam date */
		public final DateTime examDate;
		
		/** Exam session */
		public final String examSession;
		
		/** Exam information */
		public final String examInfo;
		
		/** Module code */
		public final String moduleCode;
		
		// }}}
		// {{{ methods
		
		Slot(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			this.courseId = extractString("CourseID");
			this.acadYear = extractString("AcadYear");
			this.semester = extractString("Semester");
			this.examDate = extractDateTime("ExamDate");
			this.examSession = extractString("ExamSession");
			this.examInfo = extractString("ExamInfo");
			this.moduleCode = extractString("ModuleCode");
		}
		
		// }}}
	}
	
	// }}}
}
