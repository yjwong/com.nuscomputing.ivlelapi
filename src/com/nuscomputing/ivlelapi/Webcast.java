package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

/**
 * Represents a webcast.
 * @author Wong Yong Jie
 */

public class Webcast extends IVLEObject {
	// {{{ properties
	
	/** Badge tool */
	public final Integer badgeTool;
	
	/** Creator */
	public final User creator;
	
	/** Is the webcast published? */
	public final Boolean published;
	
	/** Title */
	public final String title;
	
	// }}}
	// {{{ methods
	
	Webcast(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.badgeTool = extractInt("BadgeTool");
		this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
		this.ID = extractString("ID");
		this.published = extractBool("Published");
		this.title = extractString("Title");
	}
	
	/**
	 * Method: getItemGroups
	 * Gets the item groups for the webcasts.
	 */
	public Webcast.ItemGroup[] getItemGroups() throws Exception {
		List<?> itemGroupList = (List<?>) this.map.get("ItemGroups");
		ItemGroup[] itemGroups = new ItemGroup[itemGroupList.size()];
		for (int i = 0; i < itemGroupList.size(); i++) {
			itemGroups[i] = new ItemGroup(this.ivle, (Map<?, ?>) itemGroupList.get(i));
		}
		
		return itemGroups;
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a webcast item group.
	 * @author Wong Yong Jie
	 */
	public class ItemGroup extends IVLEObject {
		// {{{ properties
		
		/** Item group title */
		public final String itemGroupTitle;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		ItemGroup(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.itemGroupTitle = extractString("ItemGroupTitle");
		}
		
		/**
		 * Method: getFiles
		 * Gets the files associated with this item group.
		 */
		public Webcast.File[] getFiles() throws Exception {
			List<?> fileList = (List<?>) this.map.get("Files");
			File[] files = new File[fileList.size()];
			for (int i = 0; i < fileList.size(); i++) {
				files[i] = new File(this.ivle, (Map<?, ?>) fileList.get(i));
			}
			
			return files;
		}
		
		/**
		 * Method: getItemGroups
		 * Gets the item groups for the webcasts.
		 */
		public Webcast.ItemGroup[] getItemGroups() throws Exception {
			List<?> itemGroupList = (List<?>) this.map.get("ItemGroups");
			ItemGroup[] itemGroups = new ItemGroup[itemGroupList.size()];
			for (int i = 0; i < itemGroupList.size(); i++) {
				itemGroups[i] = new ItemGroup(this.ivle, (Map<?, ?>) itemGroupList.get(i));
			}
			
			return itemGroups;
		}
		
		// }}}
	}
	
	/**
	 * Represents a webcast file.
	 * @author Wong Yong Jie
	 */
	public class File extends IVLEObject {
		// {{{ properties
		
		/** Bank item ID */
		public final String bankItemID;
		
		/** Creation date */
		public final DateTime createDate;
		
		/** Creator */
		public final User creator;
		
		/** File description */
		public final String fileDescription;
		
		/** File name */
		public final String fileName;
		
		/** File title */
		public final String fileTitle;
		
		/** MP3 */
		public final String MP3;
		
		/** MP4 */
		public final String MP4;
		
		/** Media format */
		public final String mediaFormat;
		
		/** Has this file been read? */
		public final Boolean isRead;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		File(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.bankItemID = extractString("BankItemID");
			this.createDate = extractDateTime("CreateDate");
			this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
			this.fileDescription = extractString("FileDescription");
			this.fileName = extractString("FileName");
			this.fileTitle = extractString("FileTitle");
			this.ID = extractString("ID");
			this.MP3 = extractString("MP3");
			this.MP4 = extractString("MP4");
			this.mediaFormat = extractString("MediaFormat");
			this.isRead = extractBool("isRead");
		}
		
		// }}}
	}
	
	// }}}
}
