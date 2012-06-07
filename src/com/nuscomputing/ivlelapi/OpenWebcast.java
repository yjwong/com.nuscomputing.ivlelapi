package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents a webcast.
 * @author Wong Yong Jie
 */
public class OpenWebcast extends IVLEObject {
	// {{{ properties
	
	/** Course code */
	public final String courseCode;
	
	/** Course name */
	public final String courseName;
	
	/** Course academic year / semester */
	public final String courseAcadYearSem;
	
	/** Title */
	public final String title;
	
	/** Number of recordings */
	public final Integer recordings;
	
	/** Badge ID */
	public final Integer badgeTool;
	
	/** Creator */
	public final User creator;
	
	/** Published */
	public final Boolean published;
	
	/** Save the map, since we'll use it later */
	public final Map<?, ?> map;
	
	/** Flags */
	public static final int FLAG_TITLE_ONLY = 1; 
	
	// }}}
	// {{{ methods
	
	OpenWebcast(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = map;
		this.ID = extractString("ID", map);
		this.courseCode = extractString("CourseCode", map);
		this.courseName = extractString("CourseName", map);
		this.courseAcadYearSem = extractString("CourseAcadYearSem", map);
		this.title = extractString("Title", map);
		this.recordings = extractInt("Recordings", map);
		this.badgeTool = extractInt("BadgeTool", map);
		this.creator = new User(ivle, (Map<?, ?>) map.get("Creator"));
		this.published = extractBool("Published", map);
	}
	
	/** 
	 * Method: addLog
	 * <p>
	 * Marks the webcast as read.
	 * XXX: Not implemented yet.
	 */
	public void addLog(String mediaChannelId, String mediaChannelItemId) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	public void addLog(String mediaChannelId) {
		this.addLog(mediaChannelId, null);
	}
	
	public void addLog() {
		this.addLog(null, null);
	}
		
	/**
	 * Method: getItemGroups
	 * <p>
	 * Gets the list of item groups in this open webcast.
	 */
	public ItemGroup[] getItemGroups() {
		List<?> itemGroupList = (List<?>) this.map.get("ItemGroups");
		ItemGroup[] ig = new ItemGroup[itemGroupList.size()];
		
		// Create an ItemGroup array.
		for (int i = 0; i < itemGroupList.size(); i++) {
			ig[i] = new ItemGroup(this.ivle, (Map<?, ?>) itemGroupList.get(i));
		}

		return ig;
	}
	
	/**
	 * Method: getFiles
	 * <p>
	 * Gets the files in this item groups.
	 */
	public File[] getFiles() {
		List<?> fileList = (List<?>) this.map.get("Files");
		File[] f = new File[fileList.size()];
		
		// Create a Files array.
		for (int i = 0; i < fileList.size(); i++) {
			f[i] = new File(this.ivle, (Map<?, ?>) fileList.get(i));
		}
		
		return f;
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a group of webcast items.
	 * @author Wong Yong Jie
	 */
	public class ItemGroup extends IVLEObject {
		// {{{ properties
		
		/** Title of this itemgroup */
		public final String title;
		
		/** Save the map, since we'll use it later */
		public final Map<?, ?> map;
		
		// }}}
		// {{{ methods
		
		ItemGroup(IVLE ivle, Map<?, ?> map) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.map = map;
			this.title = IVLEObject.extractString("ItemGroupTitle", map);
		}
		
		/**
		 * Method: getItemGroups
		 * <p>
		 * Gets the list of item groups in this open webcast.
		 */
		public ItemGroup[] getItemGroups() {
			List<?> itemGroupList = (List<?>) this.map.get("ItemGroups");
			ItemGroup[] ig = new ItemGroup[itemGroupList.size()];
			
			// Create an ItemGroup array.
			for (int i = 0; i < itemGroupList.size(); i++) {
				ig[i] = new ItemGroup(this.ivle, (Map<?, ?>) itemGroupList.get(i));
			}

			return ig;
		}
		
		// }}}
	}
	
	/**
	 * Represents a webcast file.
	 * @author Wong Yong Jie
	 */
	public class File extends IVLEObject {
		// {{{ properties
		
		/** Location of the webcast */
		public final String fileName;
		
		/** MP4 file */
		public final String MP4;
		
		/** MP3 file */
		public final String MP3;
		
		/** Faculty */
		public final String faculty;
		
		/** Presenter */
		public final String presenter;
		
		/** Venue */
		public final String venue;
		
		/** Start date */
		public final DateTime startDate;
		
		/** File description */
		public final String fileDescription;
		
		/** File title */
		public final String fileTitle;
		
		/** Media format */
		public final String format;
		
		/** Create date */
		public final DateTime createDate;
		
		/** Bank item ID */
		public final String bankItemID;
		
		/** Creator */
		public final User creator;
		
		/** Has this been read? */
		public final Boolean isRead;
		
		// }}}
		// {{{ methods
		
		File(IVLE ivle, Map<?, ?> map) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.ID = extractString("ID", map);
			this.fileName = extractString("FileName", map);
			this.MP4 = extractString("MP4", map);
			this.MP3 = extractString("MP3", map);
			this.faculty = extractString("Faculty", map);
			this.presenter = extractString("Presenter", map);
			this.venue = extractString("Venue", map);
			this.startDate = extractDateTime("StartDate", map);
			this.fileDescription = extractString("FileDescription", map);
			this.fileTitle = extractString("FileTitle", map);
			this.format = extractString("Format", map);
			this.createDate = extractDateTime("CreateDate", map);
			this.bankItemID = extractString("BankItemID", map);
			this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
			this.isRead = extractBool("isRead", map);
		}
		
		// }}}
	}
	
	// }}}
}
