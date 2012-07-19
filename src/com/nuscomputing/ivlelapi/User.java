package com.nuscomputing.ivlelapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
	 * <p>
	 * Obtains the display photo of this user.
	 * <p>
	 * WARNING: due to IVLE LAPI's failure to provide an API that gives you
	 * the image data directly, this method uses blocking calls and cannot
	 * be used in critical event loops (like UI).
	 * 
	 * @param courseID
	 */
	public URL getDisplayPhotoURL(String courseID) throws
			MalformedURLException, IOException {
		// Check for sanity.
		if (this.ivle.apiKey == null || this.ivle.authToken == null) {
			throw new IllegalStateException();
		}
		
		if (courseID == null) {
			courseID = "";
		}
		
		// Craft the URL.
		StringBuilder builder = new StringBuilder()
			.append("https://ivle.nus.edu.sg/api/DisplayPhoto.ashx?")
			.append("APIKey=").append(this.ivle.apiKey)
			.append("&AuthToken=").append(this.ivle.authToken)
			.append("&CourseID=").append(courseID)
			.append("&UserID=").append(this.userID);
		String urlStr = builder.toString();
		
		// Make the request.
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		
		// Read the response into a string first.
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		StringBuilder response = new StringBuilder();
		String buf = null;
		while ((buf = br.readLine()) != null) {
			response.append(buf);
		}
		br.close();
		
		// Parse the response.
		Document doc = Jsoup.parse(response.toString());
		Element img = doc.select("img").first();
		String imgUrlStr = img.attr("src");
		URL imgUrl = new URL(imgUrlStr);
		return imgUrl;
	}
	
	/**
	 * Method: getDisplayPhoto
	 * Obtains the display photo of this user.
	 * Currently uses a hardcoded endpoint. Should be fine, since it's unlikely
	 * there will be an API breakage.
	 * 
	 * @param courseID
	 */
	public InputStream getDisplayPhoto(String courseID) throws
			MalformedURLException, IOException {
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
