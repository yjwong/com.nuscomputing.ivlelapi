package com.nuscomputing.ivlelapi;

/**
 * JSON parser exception.
 * @author Wong Yong Jie
 */
public class JSONParserException extends Exception {
	// {{{ properties
	
	/** Serial version ID for this exception */
	private static final long serialVersionUID = -845135436373724948L;
	
	/** Original exception */
	private Exception mOriginalException;
	
	// }}}
	// {{{ methods
	
	/**
	 * Exception constructor.
	 * Since this exception abstracts away much of the XML parser details,
	 * we must still provide some way to figure out what went wrong.
	 */
	JSONParserException(Exception e) {
		mOriginalException = e;
	}
	
	/**
	 * Method: getOriginalException
	 * Gets the original exception abstracted away by this exception.
	 */
	public Exception getOriginalException() {
		return mOriginalException;
	}
	
	// }}}
}
