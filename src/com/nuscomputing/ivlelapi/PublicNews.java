package com.nuscomputing.ivlelapi;

import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents a public news item.
 * @author Wong Yong Jie
 */
public class PublicNews extends IVLEObject {
	// {{{ properties
	
	/** Title */
	public final String title;
	
	/** Description */
	public final String description;
	
	/** Date of creation */
	public final DateTime createdDate;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	PublicNews(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.title = extractString("Title", map);
		this.description = extractString("Description", map);
		this.ID = extractString("ID", map);
		this.createdDate = extractDateTime("CreatedDate", map);
	}
	
	// }}}
}
