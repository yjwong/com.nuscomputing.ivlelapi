package com.nuscomputing.ivlelapi;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * This class should not be instantiated directly.
 * Use the factory class IVLE instead.
 * 
 * @author Wong Yong Jie
 */

public class Module extends IVLEObject {
	// {{{ properties
	
	/** Badge */
	public final int badge;
	
	/** Badge announcement */
	public final int badgeAnnouncement;
	
	/** Course academic year */
	public final String courseAcadYear;
	
	/** Course closing date */
	public final DateTime courseCloseDate;
	
	/** Course code */
	public final String courseCode;
	
	/** Course department */
	public final String courseDepartment;
	
	/** Course level */
	/** Quirk: The IVLE Schema says this is a String... */
	public final Integer courseLevel;
	
	/** Number of modular credits (MCs) for the course */
	/** Quirk: The IVLE Schema says this is a String... */
	public final Integer courseMC;
	
	/** Name of the course */
	public final String courseName;
	
	/** Course opening date */
	public final DateTime courseOpenDate;
	
	/** Course semester */
	public final String courseSemester;
	
	/** Creator */
	public final User creator;
	
	/** Forums */
	public final Forum[] forums;
	
	/** Permission */
	public final String permission;

	/** Are there any announcement items? */
	public final Boolean hasAnnouncementItems;
	
	/** Are there any class groups for signing up? */
	public final Boolean hasClassGroupsForSignUp;
	
	/** Are there any class roster items? */
	public final Boolean hasClassRosterItems;
	
	/** Are there any consultation items? */
	public final Boolean hasConsultationItems;
	
	/** Are there any consultation slots for signing up? */
	public final Boolean hasConsultationSlotsForSignUp;
	
	/** Are there any description items? */
	public final Boolean hasDescriptionItems;
	
	/** Are there any gradebook items? */
	public final Boolean hasGradebookItems;
	
	/** Are there any group items? */
	public final Boolean hasGroupsItems;
	
	/** Are there any guest roster items? */
	public final Boolean hasGuestRosterItems;
	
	/** Are there any lecturer items? */
	public final Boolean hasLecturerItems;
	
	/** Are there any project group items? */
	public final Boolean hasProjectGroupItems;
	
	/** Are there any project groups for sign up? */
	public final Boolean hasProjectGroupsForSignUp;
	
	/** Are there any reading items? */
	public final Boolean hasReadingItems;
	
	/** Are there any timetable items? */
	public final Boolean hasTimetableItems;
	
	/** Are there any weblink items? */
	public final Boolean hasWeblinkItems;
	
	/** Is this module active? */
	public final String isActive;
	
	/** Module types */
	enum Type {
		STAFF,
		STUDENT,
		ALL
	}
	
	/** Flags */
	public static final int FLAG_INCLUDE_ALL_INFO = 1;
	
	// }}}
	// {{{ methods
	
	Module(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.badge = extractInt("Badge");
		this.badgeAnnouncement = extractInt("BadgeAnnouncement");
		this.courseAcadYear = extractString("CourseAcadYear");
		this.courseCloseDate = extractDateTime("CourseCloseDate");
		this.courseCode = extractString("CourseCode");
		this.courseDepartment = extractString("CourseDepartment");
		this.courseLevel = extractInt("CourseLevel");
		this.courseMC = extractInt("CourseMC");
		this.courseName = extractString("CourseName");
		this.courseOpenDate = extractDateTime("CourseOpenDate");
		this.courseSemester = extractString("CourseSemester");
		this.creator = new User(this.ivle, (Map <?, ?>) map.get("Creator"));
		this.forums = null;
		this.hasAnnouncementItems = extractBool("hasAnnouncementItems");
		this.hasClassGroupsForSignUp = extractBool("hasClassGroupsForSignUp");
		this.hasClassRosterItems = extractBool("hasClassRosterItems");
		this.hasConsultationItems = extractBool("hasConsultationItems");
		this.hasConsultationSlotsForSignUp = extractBool("hasConsultationSlotsForSignUp");
		this.hasDescriptionItems = extractBool("hasDescriptionItems");
		this.hasGradebookItems = extractBool("hasGradebookItems");
		this.hasGroupsItems = extractBool("hasGroupsItems");
		this.hasGuestRosterItems = extractBool("hasGuestRosterItems");
		this.hasLecturerItems = extractBool("hasLecturerItems");
		this.hasProjectGroupItems = extractBool("hasProjectGroupItems");
		this.hasProjectGroupsForSignUp = extractBool("hasProjectGroupsForSignUp");
		this.hasReadingItems = extractBool("hasReadingItems");
		this.hasTimetableItems = extractBool("hasTimetableItems");
		this.hasWeblinkItems = extractBool("hasWeblinkItems");
		this.ID = extractString("ID");
		this.isActive = extractString("isActive");
		this.permission = extractString("Permission");
	}
	
	// }}}
	// {{{ methods
	
	/**
	 * Method: getAnnouncements
	 * <p>
	 * Gets announcements accessible to the current user for this module
	 * for the past [duration] minutes, set 0 to show all.
	 * 
	 * @param	duration
	 * @param	titleOnly
	 * 
	 * @return Announcement[]
	 */
	public Announcement[] getAnnouncements(int duration, boolean titleOnly)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", this.ID);
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("TitleOnly", titleOnly ? "true" : "false");
		URL url = IVLE.prepareURL(this.ivle, "Announcements", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> announcementList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Announcement[] a = new Announcement[announcementList.size()];
		for (int i = 0; i < announcementList.size(); i++) {
			a[i] = new Announcement(this.ivle, (Map<?, ?>) announcementList.get(i));
		}

		return a;
	}
	
	public Announcement[] getAnnouncements() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getAnnouncements(0, false);
	}
	
	public Announcement[] getAnnouncements(int duration) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		return this.getAnnouncements(duration, false);
	}
	
	public Announcement[] getAnnouncements(boolean titleOnly) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		return this.getAnnouncements(0, titleOnly);
	}
	
	/**
	 * Method: getClassRoster
	 * <p>
	 * Gets the related class roster. The class roster is the list of users
	 * registered for this particular module.
	 * <p>
	 * A blank class roster may not imply that there is nobody registered for
	 * the module, but may mean that the roster is inaccessible. It appears,
	 * there is no way to tell for sure.
	 */
	public User[] getClassRoster() throws NetworkErrorException, 
			JSONParserException, FailedLoginException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Class_Roster", params);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> userList = (List<?>) data.get("Results");
		
		// Run through the userList, generating a new User each iteration.
		User[] u = new User[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			u[i] = new User(this.ivle, (Map<?, ?>) userList.get(i));
		}
		
		return u;
	}
	
	/**
	 * Method: getDescriptions
	 * <p>
	 * Gets the related course description sections created or updated within
	 * the last [duration] minutes for this module. Set duration to 0 to show
	 * all.
	 * 
	 * @param duration
	 * @return Module.Description[]
	 */
	public Description[] getDescriptions(int duration) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", this.ID);
		urlParams.put("Duration", Integer.toString(duration));
		URL url = IVLE.prepareURL(this.ivle, "Module_Information", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> descriptionList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Description[] d = new Description[descriptionList.size()];
		for (int i = 0; i < descriptionList.size(); i++) {
			d[i] = new Description(this.ivle, (Map<?, ?>) descriptionList.get(i));
		}
		
		return d;
	}
	
	public Description[] getDescriptions() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getDescriptions(0);
	}
	
	/**
	 * Method: getForums
	 * <p>
	 * Gets the related course description sections created or updated within
	 * the last [duration] minutes for this module. Set duration to 0 to show
	 * all.
	 * 
	 * @param duration
	 * @return Module.Description[]
	 */
	public Forum[] getForums(int duration) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", this.ID);
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("IncludeThreads", "true");
		URL url = IVLE.prepareURL(this.ivle, "Forums", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> forumList = (List<?>) data.get("Results");
		
		// Run through the forumList, generating a new Forum each iteration.
		Forum[] f = new Forum[forumList.size()];
		for (int i = 0; i < forumList.size(); i++) {
			f[i] = new Forum(this.ivle, (Map<?, ?>) forumList.get(i));
		}
		
		return f;
	}
	
	public Forum[] getForums() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getForums(0);
	}
	
	/**
	 * Method: getGradebooks
	 * <p>
	 * Gets the list of gradebook items for this module.
	 * 
	 * @return Gradebook[]
	 */
	public Gradebook[] getGradebooks() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Gradebook_ViewItems", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> gradebookList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Gradebook[] g = new Gradebook[gradebookList.size()];
		for (int i = 0; i < gradebookList.size(); i++) {
			g[i] = new Gradebook(this.ivle, (Map<?, ?>) gradebookList.get(i));
		}
		
		return g;
	}
	
	/**
	 * Method: getGuestRoster
	 * <p>
	 * Gets the related guest roster. The guest roster is the list of guests
	 * registered for this particular module.
	 * <p>
	 * A blank guest roster may not imply that there is nobody registered for
	 * the module, but may mean that the roster is inaccessible. It appears,
	 * there is no way to tell for sure.
	 */
	public User[] getGuestRoster() throws NetworkErrorException, 
			JSONParserException, FailedLoginException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Guest_Roster", params);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> userList = (List<?>) data.get("Results");
		
		// Run through the userList, generating a new User each iteration.
		User[] u = new User[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			u[i] = new User(this.ivle, (Map<?, ?>) userList.get(i));
		}
		
		return u;
	}
	
	/**
	 * Method: getLecturers
	 * <p>
	 * Gets the related lecturers created within the last [duration] minutes
	 * for this module. Set duration to 0 to show all.
	 * 
	 * @param duration
	 * @throws Exception
	 * @return Module.Lecturer[]
	 */
	public Lecturer[] getLecturers(int duration) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", this.ID);
		urlParams.put("Duration", Integer.toString(duration));
		URL url = IVLE.prepareURL(this.ivle, "Module_Lecturers", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> lecturerList = (List<?>) data.get("Results");
		
		// Run through the lecturerList, generating a new Lecturer each iteration.
		Lecturer[] l = new Lecturer[lecturerList.size()];
		for (int i = 0; i < lecturerList.size(); i++) {
			l[i] = new Lecturer(this.ivle, (Map<?, ?>) lecturerList.get(i));
		}
		
		return l;
	}
	
	public Lecturer[] getLecturers() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getLecturers(0);
	}
	
	public static Lecturer[] getLecturers(IVLE ivle, String courseID,
			int duration) throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", courseID);
		urlParams.put("Duration", Integer.toString(duration));
		URL url = IVLE.prepareURL(ivle, "Module_Lecturers", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> lecturerList = (List<?>) data.get("Results");
		
		// Run through the lecturerList, generating a new Lecturer each iteration.
		Lecturer[] l = new Lecturer[lecturerList.size()];
		for (int i = 0; i < lecturerList.size(); i++) {
			l[i] = new Lecturer(ivle, (Map<?, ?>) lecturerList.get(i));
		}
		
		return l;
	}
	
	public static Lecturer[] getLecturers(IVLE ivle, String courseID)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		return Module.getLecturers(ivle, courseID, 0);
	}
	
	/**
	 * Method: getPolls
	 * Get the related polls for this module.
	 * 
	 * @return Module.Weblink[]
	 */
	public Poll[] getPolls(boolean titleOnly) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("TitleOnly", titleOnly ? "true" : "false");
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Polls", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> pollList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Poll[] p = new Poll[pollList.size()];
		for (int i = 0; i < pollList.size(); i++) {
			p[i] = new Poll(this.ivle, (Map<?, ?>) pollList.get(i));
		}
		
		return p;
	}
	
	public Poll[] getPolls() throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		return this.getPolls(false);
	}
	
	/**
	 * Method: getReadings
	 * Gets related formatted and unformatted text and readings created or
	 * updated within the past [duration] modules for this module.
	 * 
	 * @param duration
	 * @return Module.Reading[]
	 */
	public Reading[] getReadings(int duration) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Module_Reading", urlParams);
		Request request = new Request(url);
	
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> readingList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Reading[] r = new Reading[readingList.size()];
		for (int i = 0; i < readingList.size(); i++) {
			r[i] = new Reading(this.ivle, (Map<?, ?>) readingList.get(i));
		}
		
		return r;
	}
	
	public Reading[] getReadings() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getReadings(0);
	}
	
	/**
	 * Method: getReadingsFormatted
	 * Gets related formatted text and readings created or updated within the
	 * past [duration] minutes for this module.
	 * 
	 * @param duration
	 * @return Module.Reading[]
	 */
	public Reading[] getReadingsFormatted(int duration) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Module_ReadingFormatted", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> readingList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Reading[] r = new Reading[readingList.size()];
		for (int i = 0; i < readingList.size(); i++) {
			r[i] = new Reading(this.ivle, (Map<?, ?>) readingList.get(i));
		}
		
		return r;
	}
	
	public Reading[] getReadingsFormatted() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getReadingsFormatted(0);
	}
	
	/**
	 * Method: getReadingsUnformatted
	 * Gets related unformatted text and readings created or updated within the
	 * past [duration] minutes for this module.
	 * 
	 * @param duration
	 * @return Module.Reading[]
	 */
	public Reading[] getReadingsUnformatted(int duration) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Module_ReadingUnformatted", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> readingList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Reading[] r = new Reading[readingList.size()];
		for (int i = 0; i < readingList.size(); i++) {
			r[i] = new Reading(this.ivle, (Map<?, ?>) readingList.get(i));
		}
		
		return r;
	}
	
	public Reading[] getReadingsUnformatted() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getReadingsUnformatted(0);
	}
	
	/**
	 * Method: getTimetable
	 * <p>
	 * Gets the timetable for this module.
	 * 
	 * @throws JSONParserException
	 * @throws NetworkErrorException
	 * @throws FailedLoginException
	 * @return Timetable
	 */
	public Timetable getTimetable() throws JSONParserException,
			NetworkErrorException, FailedLoginException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("CourseID", this.ID);
		
		// Execute the request.
		URL url = IVLE.prepareURL(this.ivle, "Timetable_Module", params);
		Request request = new Request(url);
		Map<?, ?> data = request.execute().data;
		
		// Parse the response.
		List<?> timetableSlotList = (List<?>) data.get("Results");
		return new Timetable(this.ivle, timetableSlotList);
	}
	
	/**
	 * Method: getTimetableExam
	 * <p>
	 * Get the exam timetable for this module.
	 * 
	 * @throws JSONParserException
	 * @throws NetworkErrorException
	 * @throws FailedLoginException
	 * @return TimetableExam
	 */
	public TimetableExam getTimetableExam() throws JSONParserException,
			NetworkErrorException, FailedLoginException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("CourseID", this.ID);
		
		// Execute the request.
		URL url = IVLE.prepareURL(this.ivle, "Timetable_ModuleExam", params);
		Request request = new Request(url);
		Map<?, ?> data = request.execute().data;
		
		// Parse the response.
		List<?> timetableSlotList = (List<?>) data.get("Results");
		return new TimetableExam(this.ivle, timetableSlotList);
	}
	
	/**
	 * Method: getWebcasts
	 * Get the related webcasts for this module for the past [duration]
	 * minutes. Set duration to 0 to show all.
	 * 
	 * @param duration
	 * @return Webcast[]
	 */
	public Webcast[] getWebcasts(int duration) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Webcasts", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> webcastList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Webcast[] w = new Webcast[webcastList.size()];
		for (int i = 0; i < webcastList.size(); i++) {
			w[i] = new Webcast(this.ivle, (Map<?, ?>) webcastList.get(i));
		}
		
		return w;
	}
	
	public Webcast[] getWebcasts() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getWebcasts(0);
	}
	
	/**
	 * Method: getWeblinks
	 * Get the related weblinks for this module.
	 * 
	 * @return Module.Weblink[]
	 */
	public Weblink[] getWeblinks() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Module_Weblinks", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> weblinkList = (List<?>) data.get("Results");
		
		// Run through the weblinkList, generating a new Weblink each iteration.
		Weblink[] w = new Weblink[weblinkList.size()];
		for (int i = 0; i < weblinkList.size(); i++) {
			w[i] = new Weblink(this.ivle, (Map<?, ?>) weblinkList.get(i));
		}
		
		return w;
	}
	
	public static Weblink[] getWeblinks(IVLE ivle, String courseId) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", courseId);
		URL url = IVLE.prepareURL(ivle, "Module_Weblinks", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> weblinkList = (List<?>) data.get("Results");
		
		// Run through the weblinkList, generating a new Weblink each iteration.
		Weblink[] w = new Weblink[weblinkList.size()];
		for (int i = 0; i < weblinkList.size(); i++) {
			w[i] = new Weblink(ivle, (Map<?, ?>) weblinkList.get(i));
		}
		
		return w;
	}
	
	/**
	 * Method: getWorkbins
	 * Get the related workbins for this module for the past [duration] minutes.
	 * Set duration to 0 to show all.
	 * 
	 * @param duration
	 * @param titleOnly
	 * @return Workbin[]
	 */
	public Workbin[] getWorkbins(int duration, boolean titleOnly) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Check courseID.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("TitleOnly", Boolean.toString(titleOnly));
		urlParams.put("CourseID", this.ID);
		URL url = IVLE.prepareURL(this.ivle, "Workbins", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> workbinList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Workbin[] w = new Workbin[workbinList.size()];
		for (int i = 0; i < workbinList.size(); i++) {
			w[i] = new Workbin(this.ivle, (Map<?, ?>) workbinList.get(i));
			if (titleOnly) { w[i].setFlag(Workbin.FLAG_TITLE_ONLY); }
		}
		
		return w;
	}
	
	public Workbin[] getWorkbins(boolean titleOnly) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getWorkbins(0, titleOnly);
	}
	
	public Workbin[] getWorkbins(int duration) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getWorkbins(duration, false);
	}
	
	public Workbin[] getWorkbins() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getWorkbins(0, false);
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a module description
	 * @author Wong Yong Jie
	 */
	public class Description extends IVLEObject {
		// {{{ properties
		
		/** Description */
		public final String description;
		
		/** Order */
		public final Integer order;
		
		/** Title */
		public final String title;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Description() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		Description(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.description = extractString("Description");
			this.ID = extractString("ID");
			this.order = extractInt("Order");
			this.title = extractString("Title");
		}
		
		// }}}
	}
	
	/**
	 * Represents a reading.
	 * @author Wong Yong Jie
	 */
	public class Reading extends IVLEObject {
		// {{{ properties
		
		/** Additional information */
		public final String additionalInformation;
		
		/** Author */
		public final String author;
		
		/** Book type */
		public final String bookType;
		
		/** Comp website */
		public final String compWebsite;
		
		/** Edition */
		public final String edition;
		
		/** ISBN */
		public final String ISBN;
		
		/** Order */
		public final Integer order;
		
		/** Published year */
		public final String pubYear;
		
		/** Publisher */
		public final String publisher;
		
		/** Title */
		public final String title;
		
		/** Is this formatted? */
		public final String isFormatted;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Reading() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		Reading(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.additionalInformation = extractString("AdditionalInformation");
			this.author = extractString("Author");
			this.bookType = extractString("BookType");
			this.compWebsite = extractString("CompWebsite");
			this.edition = extractString("Edition");
			this.ID = extractString("ID");
			this.ISBN = extractString("ISBN");
			this.order = extractInt("Order");
			this.publisher = extractString("Publisher");
			this.pubYear = extractString("PubYear");
			this.title = extractString("Title");
			this.isFormatted = extractString("isFormatted");
		}

		// }}}
	}
	
	// }}}
}
