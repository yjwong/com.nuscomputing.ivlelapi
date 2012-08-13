package com.nuscomputing.ivlelapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * Represents the organizer on IVLE.
 * @author yjwong
 */
public class MyOrganizer extends IVLEObject {
	// {{{ properties
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	MyOrganizer(IVLE ivle) {
		super(ivle, null);
	}
	
	/**
	 * Method: addEventPersonal
	 * <p>
	 * Creates a new personal event, returning the new event ID.
	 */
	public String addEventPersonal(String eventTitle, String venue,
			String eventDateTime, String description, String recurType,
			String weeklyRecurEvery, String strDays, String recurTillDate)
			throws IOException, JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Validate recurrence type.
		if (recurType != null && recurType != "N" && recurType != "W") {
			throw new IllegalArgumentException("Recurrence type can only be null, N or W");
		}
		
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("APIKey", this.ivle.apiKey);
		gen.writeStringField("AuthToken", this.ivle.authToken);
		gen.writeStringField("EventTitle", eventTitle);
		
		// Optional stuff.
		gen.writeStringField("Venue", venue != null ? venue : "");
		gen.writeStringField("EventDateTime", eventDateTime != null ? eventDateTime : "");
		gen.writeStringField("Description", description != null ? description : "");
		gen.writeStringField("RecurType", recurType != null ? recurType : "N");
		if (recurType != null && recurType == "W") {
			// If there's a weekly recurrence, some fields cannot be null.
			if (weeklyRecurEvery == null) {
				throw new IllegalArgumentException("weeklyRecurEvery cannot be null for recurType = W");
			}
			
			if (strDays == null) {
				throw new IllegalArgumentException("strDays cannot be null for recurType = W");
			}
			
			if (recurTillDate == null) {
				throw new IllegalArgumentException("recurTillDate cannot be null for recurType = W");
			}
			
			gen.writeStringField("WeeklyRecurEvery", weeklyRecurEvery);
			gen.writeStringField("strDays", strDays);
			gen.writeStringField("RecurTillDate", recurTillDate);
		} else {
			// Write empty fields.
			gen.writeStringField("WeeklyRecurEvery", "");
			gen.writeStringField("strDays", "");
			gen.writeStringField("RecurTillDate", "");
		}
		
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("MyOrganizer_AddPersonalEvent_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		Map<?, ?> data = (request.execute().data);
		List<?> idList = (List<?>) data.get("Results");
		String id = (String) idList.get(0);
		return id;
	}
	
	public String addEventPersonal(String eventTitle, String venue,
			String eventDateTime, String description)
			throws IOException, JSONParserException, FailedLoginException,
			NetworkErrorException {
		return this.addEventPersonal(eventTitle, venue, eventDateTime, description, "N", null, null, null);
	}
	
	/**
	 * Method: deleteEventPersonal
	 * <p>
	 * Deletes the Personal Event, and returns a boolean indicating the status
	 * of the deletion.
	 */
	public boolean deleteEventPersonal(String eventID, boolean deleteAllRecurrence) 
			throws IOException, JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("APIKey", this.ivle.apiKey);
		gen.writeStringField("AuthToken", this.ivle.authToken);
		gen.writeStringField("EventID", eventID);
		gen.writeBooleanField("DeleteAllRecurrence", deleteAllRecurrence);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("MyOrganizer_DeletePersonalEvent_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		Map<?, ?> data = (request.execute().data);
		List<?> resultList = (List<?>) data.get("Results");
		boolean result = (Boolean) resultList.get(0);
		return result;
	}
	
	/**
	 * Method: getEventsPersonal
	 * <p>
	 * Gets the related My Organizer Personal Events for the indicated period
	 * for the user.
	 */
	public Event[] getEventsPersonal(String startDate, String endDate)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		return this.getEvents(startDate, endDate, Event.Type.PERSONAL);
	}
	
	public Event[] getEventsPersonal() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getEvents(Event.Type.PERSONAL);
	}
	
	/**
	 * Method: getEventsIVLE
	 * <p>
	 * Gets the related My Organizer IVLE Events for the indicated period
	 * for the user.
	 */
	public Event[] getEventsIVLE(String startDate, String endDate)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		return this.getEvents(startDate, endDate, Event.Type.IVLE);
	}
	
	public Event[] getEventsIVLE() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getEvents(Event.Type.IVLE);
	}
	
	/**
	 * Method: getEvents
	 * <p>
	 * Gets the related My Organizer Personal and IVLE Events for the indicated
	 * period for the user.
	 */
	public Event[] getEvents(String startDate, String endDate, Event.Type type)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("StartDate", startDate);
		params.put("EndDate", endDate);
		URL url;
		switch (type) {
			case PERSONAL:
				url = IVLE.prepareURL(this.ivle, "MyOrganizer_Personal", params);
				break;
			case IVLE:
				url = IVLE.prepareURL(this.ivle, "MyOrganizer_IVLE", params);
				break;
			default:
				url = IVLE.prepareURL(this.ivle, "MyOrganizer_Events", params);
		}
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = (Map<?, ?>) request.execute().data;
		List<?> eventList = (List<?>) data.get("Results");
		
		// Feed the eventList into a conventional array.
		Event[] e = new Event[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) {
			e[i] = new Event(this.ivle, (Map<?, ?>) eventList.get(i));
		}
		
		return e;
	}
	
	public Event[] getEvents(Event.Type type) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Prepare the request.
		URL url;
		switch (type) {
			case PERSONAL:
				url = IVLE.prepareURL(this.ivle, "MyOrganizer_Personal", null);
				break;
			case IVLE:
				url = IVLE.prepareURL(this.ivle, "MyOrganizer_IVLE", null);
				break;
			default:
				url = IVLE.prepareURL(this.ivle, "MyOrganizer_Events", null);
		}
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = (Map<?, ?>) request.execute().data;
		List<?> eventList = (List<?>) data.get("Results");
		
		// Feed the specialDayList into a conventional array.
		Event[] e = new Event[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) {
			e[i] = new Event(this.ivle, (Map<?, ?>) eventList.get(i));
		}
		
		return e;
	}
	
	/**
	 * Method: getSpecialDays
	 * <p>
	 * Gets the related special days for the indicated period. This includes
	 * public holidays and non-public holidays like special terms, recess
	 * week, reading week, exam week and vacation.
	 * <p>
	 * The format of the startDate and endDate is DD-MMM-YYYY. For example,
	 * "13-Aug-2012" satisfies the format required.
	 */
	public SpecialDay[] getSpecialDays(String startDate, String endDate) 
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("StartDate", startDate);
		params.put("EndDate", endDate);
		URL url = IVLE.prepareURL(this.ivle, "MyOrganizer_SpecialDays", params);
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = (Map<?, ?>) request.execute().data;
		List<?> specialDayList = (List<?>) data.get("Results");
		
		// Feed the specialDayList into a conventional array.
		SpecialDay[] sd = new SpecialDay[specialDayList.size()];
		for (int i = 0; i < specialDayList.size(); i++) {
			sd[i] = new SpecialDay(this.ivle, (Map<?, ?>) specialDayList.get(i));
		}
		
		return sd;
	}
	
	public SpecialDay[] getSpecialDays() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Prepare the request.
		URL url = IVLE.prepareURL(this.ivle, "MyOrganizer_SpecialDays", null);
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = (Map<?, ?>) request.execute().data;
		List<?> specialDayList = (List<?>) data.get("Results");
		
		// Feed the specialDayList into a conventional array.
		SpecialDay[] sd = new SpecialDay[specialDayList.size()];
		for (int i = 0; i < specialDayList.size(); i++) {
			sd[i] = new SpecialDay(this.ivle, (Map<?, ?>) specialDayList.get(i));
		}
		
		return sd;
	}
	
	/**
	 * Method: getAcadSemesterInfo
	 * <p>
	 * Gets the related academic semester information.
	 * <p>
	 * The academic year format is "xxxx/xxxx". For example, "2012/2013".
	 * The academic semester can be either 1, 2, 3 or 4.
	 */
	public AcadSemesterInfo[] getAcadSemesterInfo(String acadYear,
			String semester) throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("AcadYear", acadYear);
		params.put("Semester", semester);
		URL url = IVLE.prepareURL(this.ivle, "MyOrganizer_AcadSemesterInfo", params);
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = (Map<?, ?>) request.execute().data;
		List<?> acadSemesterInfoList = (List<?>) data.get("Results");
		
		// Feed the acadSemesterInfoList into a conventional array.
		AcadSemesterInfo[] asi = new AcadSemesterInfo[acadSemesterInfoList.size()];
		for (int i = 0; i < acadSemesterInfoList.size(); i++) {
			asi[i] = new AcadSemesterInfo(this.ivle, (Map<?, ?>) acadSemesterInfoList.get(i));
		}
		
		return asi;
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * A data structure representing a special day on IVLE. A special day includes
	 * public and non-public holidays such as special terms, recess week, reading
	 * week, exam week and vacation.
	 * 
	 * @author Wong Yong Jie
	 */
	public class SpecialDay extends IVLEObject {
		// {{{ properties
		
		/** Description */
		public final String description;
		
		/** End date */
		public final DateTime endDate;
		
		/** Start date */
		public final DateTime startDate;
		
		/**
		 * The type of the special day
		 * <p>
		 * There are multiple known types for the special day:
		 * - 'P': Public holiday
		 * - 'R': Recess week
		 * - 'D': Reading week
		 * - 'E': Examination
		 * - 'V': Vacation
		 */
		public final String type;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		SpecialDay(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.description = extractString("Description");
			this.endDate = extractDateTime("EndDate");
			this.startDate = extractDateTime("StartDate");
			this.type = extractString("Type");
		}
		
		// }}}
	}
	
	/**
	 * A data structure containing information about academic semester
	 * information.
	 * 
	 * @author Wong Yong Jie
	 */
	public class AcadSemesterInfo extends IVLEObject {
		// {{{ properties
		
		/** Academic year */
		public final String acadYear;
		
		/**
		 * Even/odd week
		 * <p>
		 * - 'e' indicates an even week
		 * - 'o' indicates an odd week
		 * <p>
		 * If this field is empty, then the week is not an academic week, i.e.
		 * that particular week is a recess week / examination week / etc.
		 */
		public final String evenOddWeek;
		
		/** Lecture start date */
		public final DateTime lectureStartDate;
		
		/**
		 * Semester
		 * <p>
		 * This is in the format of "Semester x", where x is an integer.
		 * For example, "Semester 1".
		 */
		public final String semester;
		
		/** Semester end date */
		public final DateTime semesterEndDate;
		
		/** Semester start date */
		public final DateTime semesterStartDate;
		
		/** Tutorial start date */
		public final DateTime tutorialStartDate;
		
		/**
		 * Week Type name
		 * <p>
		 * If this field is an integer, then it is the week number of the
		 * semester. Otherwise, these are the other week types:
		 * - "Recess"
		 * - "Reading"
		 * - "Examination"
		 * - "Vacation"
		 */
		public final String typeName;
		
		/** Week type end date */
		public final DateTime weekTypeEndDate;
		
		/** Week type start date */
		public final DateTime weekTypeStartDate;

		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		AcadSemesterInfo(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.acadYear = extractString("AcadYear");
			this.evenOddWeek = extractString("EvenOddWeek");
			this.lectureStartDate = extractDateTime("LectureStartDate");
			this.semester = extractString("Semester");
			this.semesterEndDate = extractDateTime("SemesterEndDate");
			this.semesterStartDate = extractDateTime("SemesterStartDate");
			this.tutorialStartDate = extractDateTime("TutorialStartDate");
			this.typeName = extractString("TypeName");
			this.weekTypeEndDate = extractDateTime("WeekTypeEndDate");
			this.weekTypeStartDate = extractDateTime("WeekTypeStartDate");
		}
		
		// }}}
	}
	
	/**
	 * A data structure representing an event in the organizer.
	 * @author Wong Yong Jie
	 */
	public static class Event extends IVLEObject {
		// {{{ properties
		
		/** Date */
		public final DateTime date;
		
		/** Description */
		public final String description;
		
		/** The event type */
		public final String eventType;
		
		/** The location */
		public final String location;
		
		/** The title */
		public final String title;
		
		/** The enum for event types */
		enum Type {
			PERSONAL,
			IVLE,
			ALL
		}

		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Event(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.date = extractDateTime("Date");
			this.description = extractString("Description");
			this.eventType = extractString("EventType");
			this.location = extractString("Location");
			this.title = extractString("Title");
		}
		
		// }}}
	}
	
	// }}}
}
