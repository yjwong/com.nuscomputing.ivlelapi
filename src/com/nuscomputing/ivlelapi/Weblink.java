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
	Weblink(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.description = extractString("Description");
		this.ID = extractString("ID");
		this.order = extractInt("Order");
		this.rating = extractInt("Rating");
		this.siteType = extractString("SiteType");
		this.url = extractString("URL");
	}
	
	// }}}
}
