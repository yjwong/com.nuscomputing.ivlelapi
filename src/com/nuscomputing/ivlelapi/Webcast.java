package com.nuscomputing.ivlelapi;

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

	/** Save the map somewhere, because we'll use it */
	private final Map<?, ?> map;
	
	// }}}
	// {{{ methods
	
	Webcast(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = null;
		this.badgeTool = extractInt("BadgeTool", map);
		this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
		this.ID = extractString("ID", map);
		this.published = extractBool("Published", map);
		this.title = extractString("Title", map);
	}
	
	/**
	 * Method: getItemGroups
	 * Gets the item groups for the webcasts.
	 */
	public Webcast.ItemGroup[] getItemGroups() throws Exception {
		// Create a new XPath object.
//		XPath xpath = XPathFactory.newInstance().newXPath();
//		NodeList itemGroupList = (NodeList) xpath.evaluate("ItemGroups/Data_Webcast_ItemGroup", this.node, XPathConstants.NODESET);
//		
//		// Run through the moduleList, generating a new Module each iteration.
//		Webcast.ItemGroup[] m = new Webcast.ItemGroup[itemGroupList.getLength()];
//		for (int i = 0; i < itemGroupList.getLength(); i++) {
//			m[i] = new Webcast.ItemGroup(this.ivle, itemGroupList.item(i));
//		}
//		
//		return m;
		return null;
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

		/** Save the map somewhere, because we'll use it */
		private final Map<?, ?> map;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		ItemGroup() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		ItemGroup(IVLE ivle, Map<?, ?> map) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.map = map;
			this.itemGroupTitle = extractString("ItemGroupTitle", map);
		}
		
		/**
		 * Method: getFiles
		 * Gets the files associated with this item group.
		 */
		public Webcast.File[] getFiles() throws Exception {
			// Create a new XPath object.
//			XPath xpath = XPathFactory.newInstance().newXPath();
//			NodeList fileList = (NodeList) xpath.evaluate("Files/Data_Webcast_File", this.node, XPathConstants.NODESET);
//			
//			// Run through the moduleList, generating a new Module each iteration.
//			Webcast.File[] f = new Webcast.File[fileList.getLength()];
//			for (int i = 0; i < fileList.getLength(); i++) {
//				f[i] = new Webcast.File(this.ivle, fileList.item(i));
//			}
//			
//			return f;
			return null;
		}
		
		/**
		 * Method: getItemGroups
		 * Gets the item groups for the webcasts.
		 */
		public Webcast.ItemGroup[] getItemGroups() throws Exception {
			// Create a new XPath object.
//			XPath xpath = XPathFactory.newInstance().newXPath();
//			NodeList itemGroupList = (NodeList) xpath.evaluate("ItemGroups/Data_Webcast_ItemGroup", this.node, XPathConstants.NODESET);
//			
//			// Run through the moduleList, generating a new Module each iteration.
//			Webcast.ItemGroup[] m = new Webcast.ItemGroup[itemGroupList.getLength()];
//			for (int i = 0; i < itemGroupList.getLength(); i++) {
//				m[i] = new Webcast.ItemGroup(this.ivle, itemGroupList.item(i));
//			}
//			
//			return m;
			return null;
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
		File() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		File(IVLE ivle, Map<?, ?> map) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.bankItemID = extractString("BankItemID", map);
			this.createDate = extractDateTime("CreateDate", map);
			this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
			this.fileDescription = extractString("FileDescription", map);
			this.fileName = extractString("FileName", map);
			this.fileTitle = extractString("FileTitle", map);
			this.ID = extractString("ID", map);
			this.MP3 = extractString("MP3", map);
			this.MP4 = extractString("MP4", map);
			this.mediaFormat = extractString("MediaFormat", map);
			this.isRead = extractBool("isRead", map);
		}
		
		// }}}
	}
	
	// }}}
}
