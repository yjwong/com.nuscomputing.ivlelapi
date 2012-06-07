package com.nuscomputing.ivlelapi;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Represents a user.
 * @author Wong Yong Jie
 */
public class User extends IVLEObject {
	// {{{ properties
	
	/** Account type */
	public final String accountType;
	
	/** Email */
	public final String email;
	
	/** Name */
	public final String name;
	
	/** Title */
	public final String title;
	
	/** User ID */
	public final String userID;
	
	// }}}
	// {{{ methods
	
	public User(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		// Quirk: The creator can be null, so we consider this edge case.
		if (map == null) {
			this.ID = null;
			this.accountType = null;
			this.email = null;
			this.name = null;
			this.title = null;
			this.userID = null;
		} else {
			this.ID = extractString("UserGuid", map);
			this.accountType = extractString("AccountType", map);
			this.email = extractString("Email", map);
			this.name = extractString("Name", map);
			this.title = extractString("Title", map);
			this.userID = extractString("UserID", map);
		}
	}

	/**
	 * Method: getDisplayPhotoURL
	 * Obtains the display photo of this user.
	 * 
	 * @param courseID
	 */
	public URL getDisplayPhotoURL(String courseID) throws Exception {
		// Check for sanity.
		if (this.ivle.apiKey == null || this.ivle.authToken == null) {
			throw new IllegalStateException();
		}
		
		if (courseID == null) {
			courseID = "";
		}
		
		// Craft the URL.
		String dpurl_str = "https://ivle.nus.edu.sg/api/DisplayPhoto.ashx?";
		dpurl_str = dpurl_str.concat("APIKey=").concat(this.ivle.apiKey);
		dpurl_str = dpurl_str.concat("&AuthToken").concat(this.ivle.authToken);
		dpurl_str = dpurl_str.concat("&CourseID=").concat(courseID);
		dpurl_str = dpurl_str.concat("&UserID=").concat(this.userID);
		URL dpurl = new URL(dpurl_str);
		return dpurl;
	}
	
	/**
	 * Method: getDisplayPhoto
	 * Obtains the display photo of this user.
	 * Currently uses a hardcoded endpoint. Should be fine, since it's unlikely
	 * there will be an API breakage.
	 * 
	 * @param courseID
	 */
	public InputStream getDisplayPhoto(String courseID) throws Exception {
		// Get the URL.
		URL dpurl = this.getDisplayPhotoURL(courseID);
		
		// Create the connection.
		HttpsURLConnection urlConnection = (HttpsURLConnection) dpurl.openConnection();
		InputStream in = new BufferedInputStream(urlConnection.getInputStream());
		
		// Pass our input stream to the requester.
		return in;
	}
	
	// }}}
}
