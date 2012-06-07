package com.nuscomputing.ivlelapi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Base class for IVLE LAPI.
 * @author Wong Yong Jie
 */

public class IVLE {
	// {{{ properties
	
	/** IVLE LAPI endpoint */
	public static String ENDPOINT = "https://ivle.nus.edu.sg/api/Lapi.svc/";
	
	/** Are we in debug mode? */
	public static boolean DEBUG = false;
	
	/** The API key */
	public String apiKey = null;
	
	/** The authentication token */
	public String authToken = null;
	
	// }}}
	// {{{ methods
	
	/**
	 * Empty class constructor.
	 * <p>
	 * Before using methods in this class, one must set the class variables
	 * apiKey and authToken.
	 */
	public IVLE() { }
	
	/**
	 * Class constructor.
	 * <p>
	 * Instantiates a new IVLE object with the supplied apiKey and authToken.
	 * 
	 * @param apiKey	The API key obtained from IVLE.
	 * @param authToken	The authentication token generated by the login page.
	 */
	public IVLE(String apiKey, String authToken) {
		this.apiKey = apiKey;
		this.authToken = authToken;
	}
	
	/**
	 * Class constructor.
	 * <p>
	 * Instantiates a new IVLE object with the supplied apiKey, username and
	 * password. This constructor will validate the given credentials. If the
	 * credentials supplied are not valid, a FailedLoginException is thrown.
	 * 
	 * @param apiKey	The API key obtained from IVLE.
	 * @param username	NUSNET ID.
	 * @param password	The password.
	 * 
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @throws JSONParserException
	 */
	public IVLE(String apiKey, String username, String password) throws 
		FailedLoginException, NetworkErrorException, JSONParserException {
		// Set up the CookieManager.
		CookieManager cm = new CookieManager();
		CookieHandler.setDefault(cm);
		
		// Set up the URL.
		try {
			String query = "userid=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
			query += "&__VIEWSTATE=/wEPDwULLTEzODMyMDQxNjEPZBYCAgEPZBYEAgEPD2QWAh4Gb25ibHVyBQ91c2VySWRUb1VwcGVyKClkAgkPD2QWBB4Lb25tb3VzZW92ZXIFNWRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCdsb2dpbmltZzEnKS5zcmM9b2ZmaW1nLnNyYzE7Hgpvbm1vdXNlb3V0BTRkb2N1bWVudC5nZXRFbGVtZW50QnlJZCgnbG9naW5pbWcxJykuc3JjPW9uaW1nLnNyYzE7ZBgBBR5fX0NvbnRyb2xzUmVxdWlyZVBvc3RCYWNrS2V5X18WAQUJbG9naW5pbWcx";
			
			try {
				URL url = new URL("https://ivle.nus.edu.sg/api/login/?apikey=" + apiKey);
				HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
				conn.setInstanceFollowRedirects(false);
				
				// We want a POST request.
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setDoInput(true);
				conn.setDoOutput(true);
				
				// Send request.
				DataOutputStream out = new DataOutputStream(conn.getOutputStream());
				out.writeBytes(query);
				out.flush();
				out.close();
				
				// Read response.
				String redirect = conn.getHeaderField("Location");
				if (redirect == null) {
					throw new FailedLoginException();
				}
				
				// Follow the redirect.
				url = new URL("https://ivle.nus.edu.sg" + redirect);
				conn = (HttpsURLConnection) url.openConnection();
				conn.setInstanceFollowRedirects(false);
				
				// A GET request will suffice this time.
				conn.setDoInput(true);
				conn.setDoOutput(false);
				
				// Read response.
				String authToken = "";
				String buf = null;
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((buf = in.readLine()) != null) {
					authToken = authToken.concat(buf);
				}
				in.close();
				
				// Authentication token successfully obtained.
				authToken = authToken.trim();
				this.apiKey = apiKey;
				this.authToken = authToken;
				
				// Validate the token.
				this.validateLogin();
				
			} catch (MalformedURLException mue) {
				throw new IllegalArgumentException("Invalid apiKey. ");
			} catch (IOException ioe) {
				throw new NetworkErrorException();
			}
			
		} catch (UnsupportedEncodingException uee) {
			throw new IllegalArgumentException("Invalid username or password. ");
		}
	}
	
	/**
	 * Method: getConsultationSlots
	 * <p>
	 * Gets consultation slots created by the specified module facilitator.
	 * 
	 * @param facilitatorId The module facilitator ID.
	 * @param slotType      Filters the slots that are returned.
	 *                       S - Current and future (for staff) or signed up
	 *                       (for student) consultation slots.
	 *                       A - Available consultation slots.
	 * 
	 * @throws JSONParserException
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @return ConsultationSlot[]
	 */
	public ConsultationSlot[] getConsultationSlots(String facilitatorId, 
			Character slotType) throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Check if the slotType is valid.
		if (slotType != 'S' && slotType != 'A') {
			throw new IllegalArgumentException("Invalid slot type");
		}
		
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("LecID", facilitatorId);
		params.put("SlotType", Character.toString(slotType));
		URL url = IVLE.prepareURL(this, "ConsultationSlots", params);
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = (Map<?, ?>) request.execute().data;
		List<?> slotList = (List<?>) data.get("Results");
		
		// Feed the slotList into a conventional array.
		ConsultationSlot[] cs = new ConsultationSlot[slotList.size()];
		for (int i = 0; i < slotList.size(); i++) {
			cs[i] = new ConsultationSlot(this, (Map<?, ?>) slotList.get(i));
		}
		
		return cs;
	}
	
	/**
	 * Method: getCurrentAcadYrSem
	 * <p>
	 * Gets the current academic year and semester.
	 * WARNING: This is an undocumented API.
	 * 
	 * @return AcadYearSem
	 */
	public AcadYearSem getCurrentAcadYearSem() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Prepare the request.
		URL url = IVLE.prepareURL(this, "AcadYrSem_Current", null);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> acadYearSemData = (Map<?, ?>) request.execute().data.get("Results");
		return new AcadYearSem(this, acadYearSemData);
	}
	
	/**
	 * Method: getForum
	 * <p>
	 * Gets forum information for past [duration] minutes. Set duration to 0
	 * to show all.
	 * 
	 * @param forumID  The forum ID returned from the {@link getForums}
	 *                  function.
	 * @param duration Limit the changes to the duration, in minutes.
	 * 
	 * @return Forum
	 */
	public Forum getForum(String forumID, int duration) 
			throws JSONParserException, FailedLoginException, 
			NetworkErrorException, NoSuchForumException {
		// Check forumID.
		if (forumID == null || forumID.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("ForumID", forumID);
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("IncludeThreads", "true");
		URL url = IVLE.prepareURL(this, "Forum", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> forumList = ((List<?>) data.get("Results"));
		
		// If there is nothing in forumList, then this forum is likely
		// not found.
		if (forumList.size() < 1) {
			throw new NoSuchForumException();
		}
		
		Forum forum = new Forum(this, (Map<?, ?>) forumList.get(0));
		return forum;
	}
	
	/**
	 * Method: getModule
	 * <p>
	 * Shows the latest changes to the information or tools tied to the
	 * particular Module within the last [duration] minutes. Set duration to 0
	 * to show all.
	 * 
	 * @param courseID	The module ID returned from the {@link getModules}
	 * 					function.
	 * @param duration	Limit the changes to the duration, in minutes.
	 * @param titleOnly	Set to true to display only basic information for the
	 * 					Module's tools.
	 * 
	 * @throws Exception
	 * @throws NoSuchModuleException
	 * 
	 * @return Module
	 */
	public Module getModule(String courseID, int duration, boolean titleOnly)
			throws NoSuchModuleException, JSONParserException,
			NetworkErrorException, FailedLoginException {
		// Check courseID.
		if (courseID == null || courseID.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("CourseID", courseID);
		urlParams.put("Duration", Integer.toString(duration));
		urlParams.put("TitleOnly", titleOnly ? "true" : "false");
		URL url = IVLE.prepareURL(this, "Module", urlParams);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> moduleList = ((List <?>) data.get("Results"));
		
		// If there is nothing in moduleList, then this module is likely
		// not found.
		if (moduleList.size() < 1) {
			throw new NoSuchModuleException();
		}
		
		Module module = new Module(this, (Map<?, ?>) moduleList.get(0));
		return module;
	}
	
	public Module getModule(String courseID) throws NoSuchModuleException, 
			JSONParserException, FailedLoginException, NetworkErrorException  {
		return this.getModule(courseID, 0, false);
	}
	
	public Module getModule(String courseID, int duration)
			throws NoSuchModuleException, JSONParserException, 
			FailedLoginException, NetworkErrorException  {
		return this.getModule(courseID, duration, false);
	}
	
	public Module getModule(String courseID, boolean titleOnly)
			throws NoSuchModuleException, JSONParserException, 
			FailedLoginException, NetworkErrorException {
		return this.getModule(courseID, 0, titleOnly);
	}
	
	/**
	 * Method: getModules
	 * Shows the latest changes to information or tools tied to the Modules
	 * within the last [duration] minutes. Set duration to 0 to show all.
	 * 
	 * @param duration
	 * @param type
	 * @throws Exception 
	 * @return Module[]
	 */
	public Module[] getModules(int duration, Module.Type type)
			throws JSONParserException, FailedLoginException, 
			NetworkErrorException {
		// Prepare the request.
		URL url;
		switch (type) {
			case STAFF:
				url = IVLE.prepareURL(this, "Modules_Staff", null);
				break;
			case STUDENT:
				url = IVLE.prepareURL(this, "Modules_Student", null);
				break;
			default:
				url = IVLE.prepareURL(this, "Modules", null);
		}
		
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("Duration", Integer.toString(duration));
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> moduleList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Module[] m = new Module[moduleList.size()];
		for (int i = 0; i < moduleList.size(); i++) {
			m[i] = new Module(this, (Map<?, ?>) moduleList.get(i));
		}

		return m;
	}
	
	public Module[] getModules(int duration) throws JSONParserException, 
			FailedLoginException, NetworkErrorException {
		return this.getModules(duration, Module.Type.ALL);
	}
	
	public Module[] getModules() throws JSONParserException, 
			FailedLoginException, NetworkErrorException {
		return this.getModules(0);
	}
	
	/**
	 * Method: getModulesStaff
	 * Shows the latest changes to information or tools tied to the Staff
	 * Modules within the last [duration] minutes. Set duration to 0 to show
	 * all.
	 * 
	 * @throws Exception
	 * @param duration
	 * @return Module[]
	 */
	public Module[] getModulesStaff(int duration) throws NetworkErrorException, 
			FailedLoginException, JSONParserException, MalformedURLException {
		return this.getModules(duration, Module.Type.STAFF);
	}
	
	public Module[] getModulesStaff() throws NetworkErrorException, 
			FailedLoginException, JSONParserException, MalformedURLException {
		return this.getModulesStaff(0);
	}
	
	/**
	 * Method: getModulesStudent
	 * <p>
	 * Shows the latest changes to information or tools tied to the Student
	 * Modules within the last [duration] minutes. Set duration to 0 to show
	 * all.
	 * 
	 * @param duration
	 * @return Module[]
	 */
	public Module[] getModulesStudent(int duration) throws NetworkErrorException, 
			FailedLoginException, JSONParserException, MalformedURLException {
		return this.getModules(duration, Module.Type.STUDENT);
	}
	
	public Module[] getModulesStudent() throws NetworkErrorException, 
			FailedLoginException, JSONParserException, MalformedURLException {
		return this.getModulesStudent(0);
	}
	
	/**
	 * Method: getOpenWebcasts
	 * <p>
	 * Gets the list of webcasts accessible to all students.
	 * 
	 * @param titleOnly      Whether or not to return the title only. If
	 *                        titleOnly is true, then each returned OpenWebcast
	 *                        will contain the FLAG_TITLE_ONLY flag.
	 * @param mediaChannelId Filters the result by media channel ID.
	 * @param acadYear       Filters the result by academic year, i.e.
	 *                        2010/2011
	 * @param semester       Filters the result by semester, i.e. 1 = 
	 *                        Semester 1, 2 = Semester 2, etc. 
	 * @return OpenWebcast[]
	 * @see OpenWebcast
	 */
	public OpenWebcast[] getOpenWebcasts(boolean titleOnly,
			String mediaChannelId, String acadYear, Integer semester) throws
			FailedLoginException, NetworkErrorException, JSONParserException {
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("TitleOnly", Boolean.toString(titleOnly));
		params.put("MediaChannelID", mediaChannelId);
		params.put("AcadYear", acadYear);
		params.put("Semester", Integer.toString(semester));
		
		// Create the request.
		URL url = IVLE.prepareURL(this, "OpenWebcasts", params);
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> openWebcastList = (List<?>) data.get("Results");
		
		// Run through the openWebcastList, generating a new OpenWebcast
		// each iteration.
		OpenWebcast[] ow = new OpenWebcast[openWebcastList.size()];
		for (int i = 0; i < openWebcastList.size(); i++) {
			ow[i] = new OpenWebcast(this, (Map<?, ?>) openWebcastList.get(i));
			ow[i].setFlag(OpenWebcast.FLAG_TITLE_ONLY);
		}
		
		return ow;
	}
	
	public OpenWebcast[] getOpenWebcasts(String mediaChannelId,
			String acadYear, Integer semester) throws FailedLoginException,
			NetworkErrorException, JSONParserException {
		return this.getOpenWebcasts(false, mediaChannelId, acadYear, semester);
	}
	
	public OpenWebcast[] getOpenWebcasts(boolean titleOnly) throws
			FailedLoginException, NetworkErrorException, JSONParserException {
		return this.getOpenWebcasts(titleOnly, null, null, null);
	}
	
	public OpenWebcast[] getOpenWebcasts() throws FailedLoginException,
			NetworkErrorException, JSONParserException {
		return this.getOpenWebcasts(false, null, null, null);
	}
	
	/**
	 * Method: getPublicNews
	 * Gets the latest public IVLE news.
	 */
	public PublicNews[] getPublicNews() throws FailedLoginException,
			NetworkErrorException, JSONParserException {
		// Since this is public, we can choose to omit the authToken.
		URL url = null;
		if (this.authToken == null) {
			url = IVLE.prepareURL("PublicNews", this.apiKey, "", null);
		} else {
			url = IVLE.prepareURL(this, "PublicNews", null);
		}
		
		// Create the request.
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> publicNewsList = (List<?>) data.get("Results");
		
		// Run through the publicNewsList, generating a new PublicNews
		// each iteration.
		PublicNews[] pn = new PublicNews[publicNewsList.size()];
		for (int i = 0; i < publicNewsList.size(); i++) {
			pn[i] = new PublicNews(this, (Map<?, ?>) publicNewsList.get(i));
		}
		
		return pn;
	}
	
	/**
	 * Method: getStudentEvents
	 * <p>
	 * Gets the list of student events.
	 * XXX: Untested. To be tested.
	 * 
	 * @param titleOnly     Whether to return the title.
	 * 
	 * @throws JSONParserException
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @return StudentEvent[]
	 */
	public StudentEvent[] getStudentEvents(boolean titleOnly) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("TitleOnly", Boolean.toString(titleOnly));
		URL url = IVLE.prepareURL(this, "StudentEvents", urlParams);
		
		// Create the request.
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> studentEventList = (List<?>) data.get("Results");
		
		// Run through the publicNewsList, generating a new PublicNews
		// each iteration.
		StudentEvent[] se = new StudentEvent[studentEventList.size()];
		for (int i = 0; i < studentEventList.size(); i++) {
			se[i] = new StudentEvent(this, (Map<?, ?>) studentEventList.get(i));
			if (titleOnly) { se[i].setFlag(StudentEvent.FLAG_TITLE_ONLY); }
		}
		
		return se;
	}
	
	public StudentEvent[] getStudentEvents() throws JSONParserException,
			FailedLoginException, NetworkErrorException {
		return this.getStudentEvents(false);
	}
	
	/**
	 * Method: getStudentEventsCategories
	 * <p>
	 * Gets the list of categories for student events.
	 * XXX: Untested. To be tested.
	 * 
	 * @param includeEvents Whether to include events in the result.
	 * @param titleOnly     Whether to return the title.
	 * 
	 * @throws JSONParserException
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @return StudentEvent[]
	 */
	public StudentEventCategory[] getStudentEventsCategories(boolean includeEvents,
			boolean titleOnly) throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("IncludeEvents", Boolean.toString(includeEvents));
		urlParams.put("TitleOnly", Boolean.toString(titleOnly));
		URL url = IVLE.prepareURL(this, "StudentEvents_Categories", urlParams);
		
		// Create the request.
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> categoryList = (List<?>) data.get("Results");
		
		// Run through the publicNewsList, generating a new PublicNews
		// each iteration.
		StudentEventCategory[] c = new StudentEventCategory[categoryList.size()];
		for (int i = 0; i < categoryList.size(); i++) {
			c[i] = new StudentEventCategory(this, (Map<?, ?>) categoryList.get(i));
			if (includeEvents) { c[i].setFlag(StudentEventCategory.FLAG_INCLUDE_EVENTS); }
			if (titleOnly) { c[i].setFlag(StudentEventCategory.FLAG_TITLE_ONLY); }
		}
		
		return c;
	}
	
	public StudentEventCategory[] getStudentEventsCategories(boolean includeEvents)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		return this.getStudentEventsCategories(includeEvents, false);
	}
	
	public StudentEventCategory[] getStudentEventsCategories() throws
		JSONParserException, FailedLoginException, NetworkErrorException {
		return this.getStudentEventsCategories(true, true);
	}
	
	/**
	 * Method: getStudentEventsByCategory
	 * <p>
	 * Gets the list of student events by category.
	 * 
	 * @param categoryId The ID of the category.
	 * @param titleOnly  Whether to return only the title.
	 * 
	 * @throws JSONParserException
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @return StudentEvent[]
	 */
	public StudentEvent[] getStudentEventsByCategory(String categoryId,
			boolean titleOnly) throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Sanity check.
		if (categoryId == null) {
			throw new IllegalArgumentException("Category ID must be specified");
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("TitleOnly", Boolean.toString(titleOnly));
		urlParams.put("CategoryID", categoryId);
		URL url = IVLE.prepareURL(this, "StudentEvents_Category", urlParams);
		
		// Create the request.
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> studentEventList = (List<?>) data.get("Results");
		
		// Run through the publicNewsList, generating a new PublicNews
		// each iteration.
		StudentEvent[] se = new StudentEvent[studentEventList.size()];
		for (int i = 0; i < studentEventList.size(); i++) {
			se[i] = new StudentEvent(this, (Map<?, ?>) studentEventList.get(i));
			if (titleOnly) { se[i].setFlag(StudentEvent.FLAG_TITLE_ONLY); }
		}
		
		return se;
	}
	
	public StudentEvent[] getStudentEventsByCategory(String categoryId) throws
			JSONParserException, FailedLoginException, NetworkErrorException {
		return this.getStudentEventsByCategory(categoryId, false);
	}
	
	/**
	 * Method: getStudentEventsByCommittee
	 * <p>
	 * Gets the list of student events by committee.
	 * 
	 * @param committeeId The ID of the committee to get.
	 * @param titleOnly   Whether to return only the title. 
	 * 
	 * @throws JSONParserException
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @return StudentEvent[]
	 */
	public StudentEvent[] getStudentEventsByCommittee(String committeeId,
			boolean titleOnly) throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Sanity check.
		if (committeeId == null) {
			throw new IllegalArgumentException("Committee ID must be specified");
		}
		
		// Prepare the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("TitleOnly", Boolean.toString(titleOnly));
		urlParams.put("CommitteeID", committeeId);
		URL url = IVLE.prepareURL(this, "StudentEvents_Committee", urlParams);
		
		// Create the request.
		Request request = new Request(url);
		
		// Execute the request.
		Map<?, ?> data = request.execute().data;
		List<?> studentEventList = (List<?>) data.get("Results");
		
		// Run through the publicNewsList, generating a new PublicNews
		// each iteration.
		StudentEvent[] se = new StudentEvent[studentEventList.size()];
		for (int i = 0; i < studentEventList.size(); i++) {
			se[i] = new StudentEvent(this, (Map<?, ?>) studentEventList.get(i));
			if (titleOnly) { se[i].setFlag(StudentEvent.FLAG_TITLE_ONLY); }
		}
		
		return se;
	}
	
	public StudentEvent[] getStudentEventsByCommittee(String committeeId)
			throws JSONParserException, FailedLoginException,
			NetworkErrorException {
		return this.getStudentEventsByCommittee(committeeId, false);
	}
	
	/**
	 * Method: getTimetableStudent
	 * <p>
	 * Gets the timetable for the current user.
	 * 
	 * @param acadYear Filters the timetable by an academic year, i.e.
	 *                  2010/2011
	 * @param semester Filters the timetable by a semester, i.e. 1 for
	 *                  Semester 1, 2 for Semester 2, etc.
	 * 
	 * @throws JSONParserException
	 * @throws NetworkErrorException
	 * @throws FailedLoginException
	 * @return Timetable
	 */
	public Timetable getTimetableStudent(String acadYear, Integer semester)
			throws JSONParserException, NetworkErrorException,
			FailedLoginException {
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("AcadYear", acadYear);
		params.put("Semester", Integer.toString(semester));
		
		// Execute the request.
		URL url = IVLE.prepareURL(this, "Timetable_Student", params);
		Request request = new Request(url);
		Map<?, ?> data = request.execute().data;
		
		// Parse the response.
		List<?> timetableSlotList = (List<?>) data.get("Results");
		return new Timetable(this, timetableSlotList);
	}
	
	public Timetable getTimetableStudent(String acadYear) throws
			JSONParserException, NetworkErrorException, FailedLoginException {
		return this.getTimetableStudent(acadYear, null);
	}
	
	public Timetable getTimetableStudent(Integer semester) throws
			JSONParserException, NetworkErrorException, FailedLoginException {
		return this.getTimetableStudent(null, semester);
	}
	
	public Timetable getTimetableStudent() throws JSONParserException,
			NetworkErrorException, FailedLoginException {
		return this.getTimetableStudent(null, null);
	}
	
	/**
	 * Method: getWorkbin
	 * <p>
	 * Gets a single workbin, given the workbin ID.
	 * 
	 * @param workbinId The ID of the workbin.
	 * @param duration  The duration to include.
	 * @param titleOnly Whether to return the title only.
	 * 
	 * @throws JSONParserException
	 * @throws NetworkErrorException
	 * @throws FailedLoginException
	 * @throws NoSuchWorkbinException
	 * @return Workbin
	 */
	public Workbin getWorkbin(String workbinId, int duration,
			boolean titleOnly) throws 	JSONParserException, 
			NetworkErrorException, FailedLoginException, 
			NoSuchWorkbinException {
		// Prepare the request.
		Map<String, String> params = new HashMap<String, String>();
		params.put("TitleOnly", Boolean.toString(titleOnly));
		params.put("Duration", Integer.toString(duration));
		params.put("WorkbinID", workbinId);
		URL url = IVLE.prepareURL(this, "Workbins", params);
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = request.execute().data;
		List<?> workbinList = (List<?>) data.get("Results");
		
		// If there is nothing in moduleList, then this module is likely
		// not found.
		if (workbinList.size() < 1) {
			throw new NoSuchWorkbinException();
		}
		
		Workbin workbin = new Workbin(this, (Map <?, ?>) workbinList.get(0));
		return workbin;
	}
	
	public Workbin getWorkbin(String workbinId, boolean titleOnly) 
			throws JSONParserException, NetworkErrorException, 
			FailedLoginException, NoSuchWorkbinException {
		return this.getWorkbin(workbinId, 0, titleOnly);
	}
	
	public Workbin getWorkbin(String workbinId, int duration) 
			throws JSONParserException, NetworkErrorException, 
			FailedLoginException, NoSuchWorkbinException {
		return this.getWorkbin(workbinId, duration, false);
	}
	
	public Workbin getWorkbin(String workbinId)
			throws JSONParserException, NetworkErrorException, 
			FailedLoginException, NoSuchWorkbinException {
		return this.getWorkbin(workbinId, 0, false);
	}
	
	/**
	 * Method: searchModules
	 * <p>
	 * Shows the list of modules and its information according to the search
	 * criteria. Supported search criteria: ModuleCode, ModuleTitle,
	 * LecturerName, Department, Semester, AcadYear, ModNameExact and
	 * LecNameExact. At least one must be supplied.
	 * <p>
	 * TODO: Support public searches.
	 * 
	 * @param criterion      A map containing the search criterion.
	 * @param includeAllInfo Whether or not to include all information.
	 * 
	 * @throws JSONParserException
	 * @throws FailedLoginException
	 * @throws NetworkErrorException
	 * @return Module[]
	 */
	public Module[] searchModules(Map<String, String> criterion, 
			boolean includeAllInfo) throws JSONParserException, 
			FailedLoginException, NetworkErrorException {
		// Check if criterion is null or empty.
		if (criterion == null || criterion.isEmpty()) {
			throw new IllegalArgumentException("criterion cannot be empty");
		}
		
		// Prepare the request.
		Map<String, String> param = new HashMap<String, String>();
		param.put("IncludeAllInfo", Boolean.toString(includeAllInfo));
		param.putAll(criterion);
		URL url = IVLE.prepareURL(this, "Modules_Search", criterion);
		
		// Execute the request.
		Request request = new Request(url);
		Map<?, ?> data = request.execute().data;
		List<?> moduleList = (List<?>) data.get("Results");
		
		// Run through the moduleList, generating a new Module each iteration.
		Module[] m = new Module[moduleList.size()];
		for (int i = 0; i < moduleList.size(); i++) {
			m[i] = new Module(this, (Map<?, ?>) moduleList.get(i));
			if (includeAllInfo) { m[i].setFlag(Module.FLAG_INCLUDE_ALL_INFO); }
		}

		return m;
	}
	
	public Module[] searchModules(Map<String, String> criterion) 
			throws JSONParserException, FailedLoginException, 
			NetworkErrorException {
		return this.searchModules(criterion, true);
	}
	
	/**
	 * Method: validateLogin
	 * Validates the authentication token and ensure that it is valid.
	 * Returns null if the login failed.
	 * Returns a token if the login succeeds.
	 * 
	 * @return String
	 */
	public String validateLogin() throws JSONParserException, 
			NetworkErrorException {
		URL url = IVLE.prepareURL(this, "Validate", null);
		Request request = new Request(url);
		
		// Execute the request.
		Map<String, String> urlParams = new HashMap<String, String>();
		urlParams.put("Token", this.authToken);
		try {
			// Execute the request.
			Map<?, ?> data = request.execute().data;
			Boolean loginSuccess = (Boolean) data.get("Success");
			if (!loginSuccess) {
				return null;
			}
			
			String loginToken = (String) data.get("Token");
			return loginToken;

		} catch (FailedLoginException fle) {
			return null;
		}
	}
	
	/**
	 * Method: prepareURL
	 * Prepares the URL for request, based on the specified action, API key,
	 * authentication token and additional parameters.
	 * 
	 * @param action
	 * @param apikey
	 * @param authtoken
	 * @param params
	 * @throws MalformedURLException 
	 */
	public static URL prepareURL(String action, String apiKey, String authToken, 
			Map<String, String> params) {
		// Sanity checks.
		if (action == null || apiKey == null || authToken == null) {
			throw new IllegalArgumentException("No action, API key or authToken supplied");
		}
		
		// If param is null, create an empty map.
		if (params == null) {
			params = new HashMap<String, String>();
		}
		
		// Throw the apikey and authtoken into params too.
		params.put("APIKey", apiKey);
		params.put("AuthToken", authToken);
		
		// We only accept JSON output.
		params.put("output", "json");
		
		// Prepares the URL.
		String res = IVLE.ENDPOINT.concat(action).concat("?");
		for (Map.Entry<String, String> param : params.entrySet()) {
			res = res.concat(param.getKey()).concat("=").concat(param.getValue()).concat("&");
		}
		
		// Although not crucial, produce URL with the remaining "&" stripped.
		res = res.substring(0, res.length() - 1);
		try {
			return new URL(res);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public static URL prepareURL(IVLE ivle, String action,
			Map<String, String> params) {
		// Do sanity checks for this one.
		if (ivle.apiKey == null || ivle.authToken == null) {
			throw new IllegalStateException("No API key or authToken supplied");
		}
		
		return IVLE.prepareURL(action, ivle.apiKey, ivle.authToken, params);
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * No such module exception.
	 * @author Wong Yong Jie
	 */
	class NoSuchModuleException extends Exception {
		// {{{ properties
		
		/** Serial version ID for this exception */
		private static final long serialVersionUID = 7399976978502453094L;
		
		// }}}
	}
	
	/**
	 * No such workbin exception.
	 * @author Wong Yong Jie
	 */
	class NoSuchWorkbinException extends Exception {
		// {{{ properties
		
		/** Serial version ID for this exception */
		private static final long serialVersionUID = -8565935994420317906L;
		
		// }}}
	}
	
	/**
	 * No such forum exception.
	 * @author Wong Yong Jie
	 */
	class NoSuchForumException extends Exception {
		// {{{ properties
		
		/** Serial version ID for this exception */
		private static final long serialVersionUID = 2500798696322544286L;

		// }}}
	}
	
	// }}}
}