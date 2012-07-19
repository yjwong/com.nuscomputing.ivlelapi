package com.nuscomputing.ivlelapi;

import java.util.Map;

/**
 * Represents a weblink.
 * @author Wong Yong Jie
 */
public class Weblink extends IVLEObject {
	// {{{ properties
	
	/** Description */
	public final String description;
	
	/** Order */
	public final Integer order;
	
	/** Rating */
	public final Integer rating;
	
	/** Site type */
	public final String siteType;
	
	/** URL */
	public final String url;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	Weblink() {
		throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
	}
	
	Weblink(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.description = extractString("Description", map);
		this.ID = extractString("ID", map);
		this.order = extractInt("Order", map);
		this.rating = extractInt("Rating", map);
		this.siteType = extractString("SiteType", map);
		this.url = extractString("URL", map);
	}
	
	// }}}
}
