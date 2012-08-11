package com.nuscomputing.ivlelapi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	
	/** Request types */
	enum Type {
		GET,
		POST
	}
	
	/** Request state */
	private Request.State m_state;
	
	/** Request type */
	private Request.Type m_type;
	
	/** The output stream, for POST requests */
	private ByteArrayOutputStream m_out;
	
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
		this(url, Type.GET);
	}
	
	public Request(URL url, Type type) {
		this(url, type, null);
	}
	
	public Request(URL url, Type type, ByteArrayOutputStream out) {
		// Sanity check.
		if (url == null) {
			this.m_state = State.ERROR;
			throw new IllegalArgumentException("URL was null");
		}
		
		if (type == null) {
			this.m_state = State.ERROR;
			throw new IllegalArgumentException("type was null");
		}
		
		this.m_url = url;
		this.m_state = State.READY;
		this.m_type = type;
		this.m_out = out;
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
		
		// Print request URL.
		if (IVLE.DEBUG) {
			System.out.println("API REQUEST: " + this.m_url);
		}
		
		try {
			// Set up the connection.
			HttpsURLConnection conn = (HttpsURLConnection) this.m_url.openConnection();
			conn.setDoInput(true);
			
			// Set up the connection if request is of type POST.
			if (this.m_type == Type.POST) {
				if (m_out == null) {
					throw new IllegalArgumentException("Request is of type POST but no post data specified");
				}
				
				String postData = m_out.toString();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Length", String.valueOf(postData.length()));
				conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
				
				// Write the post data.
				if (IVLE.DEBUG) {
					System.out.println("API POSTDATA: " + postData);
				}
				OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream());
				outWriter.write(postData);
				outWriter.flush();
			}
			
			// Read the response.
			try {
				InputStream in = new BufferedInputStream(conn.getInputStream());
				this.response = new Request.Response(in);
			} catch (FileNotFoundException e) {
				InputStream in = new BufferedInputStream(conn.getErrorStream());
				this.response = new Request.Response(in);
			} finally {
				conn.disconnect();
			}

			this.m_state = State.COMPLETE;
			
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
	
	/**
	 * Method: getType
	 * Getter method to return the type.
	 */
	public Request.Type getType() {
		return this.m_type;
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
				StringBuilder response = new StringBuilder(); 
				String buf = null;
				while ((buf = br.readLine()) != null) {
					response.append(buf);
				}
				br.close();
				
				if (IVLE.DEBUG) {
					System.out.println("API RESPONSE: " + response);
				}
				
				// Some requests, especially POST ones, don't return any
				// validation result. Just assume the request has succeeded.
				if (response.toString().equals("null")) {
					this.data = null;
				} else {
					// Map the JSON value to native objects.
					Map<?, ?> data = mapper.readValue(response.toString(), Map.class);
					
					// Check if we need to do login validation.
					if (validateLogin) {
						Boolean loginResult = (Boolean) data.get("Success");
						if (loginResult != null && !loginResult) {
							throw new FailedLoginException();
						}
					}
					
					this.data = data;
				}
				
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
