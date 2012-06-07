package com.nuscomputing.ivlelapi;

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
	
	// }}}
}
