package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents a forum.
 * @author Wong Yong Jie
 */

class Forum extends IVLEObject {
	// {{{ properties

	/** Badge tool */
	public final Integer badgeTool;
	
	/** Expiry date */
	public final DateTime expiryDate;
	
	/** Headings */
	public final Forum.Heading[] headings;
	
	/** Sorting order */
	public final Integer sortingOrder;
	
	/** Title */
	public final String title;
	
	/** Welcome message */
	public final String welcomeMessage;
	
	/** Does this forum have items? */
	public final Boolean hasItems;
	
	// }}}
	// {{{ methods
	
	/**
	 * Class constructor.
	 */
	Forum(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.badgeTool = extractInt("BadgeTool");
		this.expiryDate = extractDateTime("ExpiryDate");
		this.hasItems = extractBool("hasItems");
		this.headings = null;
		this.ID = extractString("ID");
		this.welcomeMessage = extractString("WelcomeMessage");
		this.sortingOrder = extractInt("SortingOrder");
		this.title = extractString("Title");
	}
	
	/** 
	 * Method: getHeadings
	 * <p>
	 * Gets the related forum headings belonging to the particular forum.
	 */
	public Heading[] getHeadings() {
		List<?> headingList = (List<?>) this.map.get("Headings");
		Heading[] headings = new Heading[headingList.size()];
		for (int i = 0; i < headingList.size(); i++) {
			headings[i] = new Heading(this.ivle, (Map<?, ?>) headingList.get(i));
		}
		
		return headings;
	}
	
	/**
	 * Method: getThreads
	 * <p>
	 * Gets the threads and/or subthreads.
	 */
	public Thread[] getThreads() {
		List<?> threadList = (List<?>) this.map.get("Threads");
		Thread[] threads = new Thread[threadList.size()];
		for (int i = 0; i < threadList.size(); i++) {
			threads[i] = new Thread(this.ivle, (Map<?, ?>) threadList.get(i));
		}
		
		return threads;
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a forum heading.
	 * @author Wong Yong Jie
	 */
	class Heading extends IVLEObject {
		// {{{ properties
		
		/** Badge heading */
		public final int badgeHeading;
		
		/** Heading order */
		public final int headingOrder;
		
		/** Threads */
		public final Forum.Thread[] threads;
		
		/** title */
		public final String title;
		
		/** Is this the archive? */
		public final boolean isArchive;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Heading() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		Heading(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.badgeHeading = extractInt("BadgeHeading");
			this.headingOrder = extractInt("HeadingOrder");
			this.ID = extractString("ID");
			this.threads = null;
			this.title = extractString("Title");
			this.isArchive = extractBool("isArchive");
		}
		
		// }}}
	}
	
	/**
	 * Represents a forum thread.
	 * @author Wong Yong Jie
	 */
	class Thread extends IVLEObject {
		// {{{ properties
		
		/** Post attachments */
		public final String postAttachment;
		
		/** Post body */
		public final String postBody;
		
		/** Post date */
		public final DateTime postDate;
		
		/** Post title */
		public final String postTitle;
		
		/** Poster */
		public final User poster;
		
		/** Threads */
		public final Forum.Thread[] threads;
		
		/** Is this a new post? */
		public final boolean isNewPost;
		
		/** Is the poster a staff? */
		public final boolean isPosterStaff;
		
		/** Has the post been read? */
		public final boolean isRead;
		
		/** Is this a survey post? */
		public final boolean isSurveyPost;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Thread() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		Thread(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.ID = extractString("ID");
			this.postAttachment = extractString("PostAttachment");
			this.postBody = extractString("PostBody");
			this.postDate = extractDateTime("PostDate");
			this.postTitle = extractString("PostTitle");
			this.poster = new User(this.ivle, (Map<?, ?>) map.get("Poster"));
			this.threads = null;
			this.isNewPost = extractBool("isNewPost");
			this.isPosterStaff = extractBool("isPosterStaff");
			this.isRead = extractBool("isRead");
			this.isSurveyPost = extractBool("isSurveyPost");
		}
		
		/**
		 * Method: getThreads
		 * <p>
		 * Gets the threads and/or subthreads.
		 */
		public Thread[] getThreads() {
			List<?> threadList = (List<?>) this.map.get("Threads");
			Thread[] threads = new Thread[threadList.size()];
			for (int i = 0; i < threadList.size(); i++) {
				threads[i] = new Thread(this.ivle, (Map<?, ?>) threadList.get(i));
			}
			
			return threads;
		}
		
		// }}}
	}
	
	// }}}
}
