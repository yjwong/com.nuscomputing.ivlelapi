package com.nuscomputing.ivlelapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents an API request.
 * @author Wong Yong Jie
 */

class Request {
	// {{{ properties
	
	/** The URL to be requested */
	private URL m_url;
	
	/** Request states */
	enum State {
		READY,
		PROCESSING,
		COMPLETE,
		ERROR
	}
	
	/** Request state */
	private Request.State m_state;
	
	/** Response */
	public Response response;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 * @param url
	 * @throws MalformedURLException 
	 */
	public Request(URL url) {
		// Sanity check.
		if (url == null) {
			this.m_state = Request.State.ERROR;
			throw new IllegalArgumentException("URL was null");
		}
		
		this.m_url = url;
		this.m_state = Request.State.READY;
	}
	
	/**
	 * Method: execute
	 * Makes the request.
	 * Returns a Response object denoting the response on success, or null
	 * on failure.
	 * 
	 * @throws NetworkErrorException
	 * @throws FailedLoginException
	 * @throws JSONParserException 
	 */
	public Response execute() throws NetworkErrorException, 
			FailedLoginException, JSONParserException {
		// Connect over HTTPS.
		StopWatch sw = null;
		if (IVLE.DEBUG) {
			sw = new StopWatch();
			sw.start();
		}
		
		try {
			HttpsURLConnection urlConnection = (HttpsURLConnection) this.m_url.openConnection();
			// Read the response.
			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				this.response = new Request.Response(in);
			} finally {
				urlConnection.disconnect();
			}

			this.m_state = Request.State.COMPLETE;
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			throw new NetworkErrorException();
			
		} finally {
			// Print request timing.
			if (IVLE.DEBUG) {
				sw.stop();
				System.out.println("request = " + this.m_url.toString() + ", time = " + sw.getTime() + "s");
			}
		}
		
		return this.response;
	}

	/**
	 * Method: getState
	 * Getter method to return the state
	 */
	public Request.State getState() {
		return this.m_state;
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Response class.
	 * @author Wong Yong Jie
	 */
	class Response {
		// {{{ properties
		
		/** JSON data */
		public final Map<?, ?> data;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 * 
		 * @throws FailedLoginException
		 * @throws JSONParserException
		 */
		Response(InputStream is, boolean validateLogin) throws 
			FailedLoginException, JSONParserException {
			// Check the request state.
			if (is == null) {
				throw new IllegalStateException();
			}
			
			// Attempt to parse the response.
			ObjectMapper mapper = new ObjectMapper();
			try {
				// Read into string first.
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String json = "";
				String buf = "";
				while ((buf = br.readLine()) != null) {
					json = json.concat(buf);
				}
				br.close();
				System.out.println(json);
				
				// Map the JSON value to native objects.
				Map<?, ?> data = mapper.readValue(json, Map.class);
				
				// Check if we need to do login validation.
				if (validateLogin) {
					Boolean loginResult = (Boolean) data.get("Success");
					if (loginResult != null && !loginResult) {
						throw new FailedLoginException();
					}
				}
				
				this.data = data;
				is.close();
				
			} catch (JsonMappingException jme) {
				throw new JSONParserException(jme);
			} catch (JsonParseException jpe) {
				throw new JSONParserException(jpe);
			} catch (IOException ioe) {
				throw new JSONParserException(ioe);
			}
		}
		
		Response(InputStream is) throws FailedLoginException, JSONParserException {
			this(is, true);
		}
		
		// }}}
	}
	
	// }}}
}
