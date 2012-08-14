package com.nuscomputing.ivlelapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

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
	
	/** Day */
	public final String day;
	
	/** Group name */
	public final String groupName;
	
	/** Group type */
	public final String groupType;
	
	/** Maximum enrollment */
	public final Integer maxEnrollment;
	
	/** The module code */
	public final String moduleCode;
	
	/** Self enrollment closing date */
	public final DateTime selfEnrolClosingDate;
	
	/** Self enrollment opening date */
	public final DateTime selfEnrolOpeningDate;
	
	/** The semester */
	public final String semester;
	
	/** The time */
	public final String time;
	
	/** The venue */
	public final String venue;
	
	/** The week */
	public final String week;
	
	/** Is self-enrollment available to the user? */
	public final Boolean isEnrolAvailableToUser;
	
	/** Is this exclusively self-enrolling? */
	public final Boolean isSelfEnrol;
	
	/** Is the current user in the group? */
	public final Boolean isUserInGroup;
	
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
		this.day = extractString("Day");
		this.groupName = extractString("GroupName");
		this.groupType = extractString("GroupType");
		this.isEnrolAvailableToUser = extractBool("isEnrolAvailableToUser");
		this.isSelfEnrol = extractBool("isSelfEnrol");
		this.isUserInGroup = extractBool("isUserInGroup");
		this.maxEnrollment = extractInt("MaxEnrollment");
		this.moduleCode = extractString("ModuleCode");
		this.selfEnrolClosingDate = extractDateTime("SelfEnrolClosingDate");
		this.selfEnrolOpeningDate = extractDateTime("SelfEnrolOpeningDate");
		this.semester = extractString("Semester");
		this.time = extractString("Time");
		this.venue = extractString("Venue");
		this.week = extractString("Week");
	}
	
	/**
	 * Method: signUp
	 * <p>
	 * Sign up for this group.
	 */
	public void signUp() throws IOException, JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("CourseID", this.courseId);
		gen.writeStringField("GroupID", this.ID);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("ClassGroupSignUp_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	public static void signUp(String courseID, String groupID) throws
			IOException, JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("CourseID", courseID);
		gen.writeStringField("GroupID", groupID);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("ClassGroupSignUp_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	/**
	 * Method: removeSignUp
	 * <p>
	 * Remove from class group.
	 */
	public void removeSignUp() throws IOException, JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("CourseID", this.courseId);
		gen.writeStringField("GroupID", this.ID);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("ClassGroupSignUpRemove_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	public static void removeSignUp(String courseID, String groupID) throws
			IOException, JSONParserException, FailedLoginException,
			NetworkErrorException {
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("CourseID", courseID);
		gen.writeStringField("GroupID", groupID);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("ClassGroupSignUpRemove_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	// }}}
}
