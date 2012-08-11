package com.nuscomputing.ivlelapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * Represents an announcement.
 * @author Wong Yong Jie
 */

public class Announcement extends IVLEObject {
	// {{{ properties
	
	/** Creation time */
	public final DateTime createdDate;
		
	/** Creator */
	public final User creator;
	
	/** Description */
	public final String description;
	
	/** Expiry date */
	public final DateTime expiryDate;
	
	/** Title */
	public final String title;
	
	/** URL */
	public final String url;
	
	/** Has this been read? */
	public final Boolean isRead;
	
	// }}}
	// {{{ methods

	Announcement(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.createdDate = extractDateTime("CreatedDate");
		this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
		this.description = extractString("Description");
		this.expiryDate = extractDateTime("ExpiryDate");
		this.ID = extractString("ID");
		this.title = extractString("Title");
		this.url = extractString("URL");
		this.isRead = extractBool("isRead");
	}
	
	/**
	 * Method: addLog
	 * <p>
	 * Adds log when user clicks to read announcements.
	 * Note: This usually marks the announcement as read.
	 */
	public void addLog() throws IOException, JSONParserException,
			FailedLoginException, NetworkErrorException {
		// Sanity check.
		if (this.ID == null || this.ID.length() == 0) {
			throw new IllegalStateException();
		}
		
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("APIKey", ivle.apiKey);
		gen.writeStringField("AuthToken", ivle.authToken);
		gen.writeStringField("AnnEventID", this.ID);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("Announcement_AddLog_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	/**
	 * Method: addLog
	 * <p>
	 * Adds log when user clicks to read announcements.
	 * Note: This usually marks the announcement as read.
	 */
	public static void addLog(IVLE ivle, String id) throws IOException,
			JSONParserException, FailedLoginException, NetworkErrorException {
		// Create the JSON generator.
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JsonFactory factory = new JsonFactory();
		JsonGenerator gen = factory.createJsonGenerator(out);
		
		// Generate the output.
		gen.writeStartObject();
		gen.writeStringField("APIKey", ivle.apiKey);
		gen.writeStringField("AuthToken", ivle.authToken);
		gen.writeStringField("AnnEventID", id);
		gen.writeEndObject();
		gen.close();
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("Announcement_AddLog_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	// }}}
}
