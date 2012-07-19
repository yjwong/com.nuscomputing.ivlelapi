package com.nuscomputing.ivlelapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.joda.time.DateTime;

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
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.createdDate = extractDateTime("CreatedDate", map);
		this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
		this.description = extractString("Description", map);
		this.expiryDate = extractDateTime("ExpiryDate", map);
		this.ID = extractString("ID", map);
		this.title = extractString("Title", map);
		this.url = extractString("URL", map);
		this.isRead = extractBool("isRead", map);
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
		
		// Generate the output.
		String outStr = "";
		outStr = outStr.concat("APIKey=").concat(ivle.apiKey);
		outStr = outStr.concat("&AuthToken=").concat("wrongtoken");
		outStr = outStr.concat("&AnnEventID=").concat(this.ID);
		out.write(outStr.getBytes());
		
		// Prepare the request.
		URL url = IVLE.preparePostURL("Announcement_AddLog_JSON");
		Request request = new Request(url, Request.Type.POST, out);
		
		// Execute the request.
		request.execute();
	}
	
	// }}}
}
