package com.nuscomputing.ivlelapi;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import org.joda.time.DateTime;

/**
 * Represents a workbin.
 * @author Wong Yong Jie
 */

public class Workbin extends IVLEObject {
	// {{{ properties
	
	/** Badge tool */
	public final Integer badgeTool;
	
	/** Creator */
	public final User creator;
	
	/** Published */
	public final Boolean published;
	
	/** Title */
	public final String title;

	/** Save the map somewhere, because we'll use it */
	private final Map<?, ?> map;
	
	/** Flags */
	public static final int FLAG_TITLE_ONLY = 1;
	
	// }}}
	// {{{ methods

	Workbin(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = map;
		this.ID = extractString("ID", map);
		this.badgeTool = extractInt("BadgeTool", map);
		this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
		this.published = extractBool("Published", map);
		this.title = extractString("Title", map);
	}
	
	/**
	 * Method: getFolders
	 */
	public Workbin.Folder[] getFolders() throws Exception {
		// Get the list of folders.
		List<?> folderList = (List<?>) this.map.get("Folders");
		Folder[] f = new Folder[folderList.size()];
		
		// Create an array of Workbin.Folder from the list.
		for (int i = 0; i < folderList.size(); i++) {
			f[i] = new Folder(this.ivle, (Map<?, ?>) folderList.get(i));
		}
		
		return f;
	}
	
	// }}}
	// {{{ classes
	
	/**
	 * Represents a workbin folder.
	 * @author Wong Yong Jie
	 */
	public class Folder extends IVLEObject {
		// {{{ properties
		
		/** Allow uploads? */
		public final Boolean allowUpload;
		
		/** Allow viewing? */
		public final Boolean allowView;
		
		/** Closing date */
		public final DateTime closeDate;
		
		/** Comment option */
		public final Character commentOption;
		
		/** File count */
		public final Integer fileCount;
		
		/** Folder name */
		public final String folderName;
		
		/** Folder order */
		public final Integer order;
		
		/** Open date */
		public final DateTime openDate;
		
		/** Sort files by */
		public final String sortFilesBy;
		
		/** Upload display option */
		public final String uploadDisplayOption;
		
		/** Save the map somewhere, because we'll use it */
		private final Map<?, ?> map;
		
		// }}}
		// {{{ methods
		
		Folder(IVLE ivle, Map<?, ?> map) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.map = map;
			this.allowUpload = extractBool("AllowUpload", map);
			this.allowView = extractBool("AllowView", map);
			this.closeDate = extractDateTime("CloseDate", map);
			this.commentOption = Character.forDigit(extractInt("CommentOption", map), 10);
			this.fileCount = extractInt("FileCount", map);
			this.folderName = extractString("FolderName", map);
			this.order = extractInt("Order", map);
			this.ID = extractString("ID", map);
			this.openDate = extractDateTime("OpenDate", map);
			this.sortFilesBy = extractString("SortFilesBy", map);
			this.uploadDisplayOption = extractString("UploadDisplayOption", map);
		}
		
		/**
		 * Method: getFiles
		 * Retrieves the files within this folder.
		 * 
		 * @return Workbin.File[]
		 */
		public Workbin.File[] getFiles() throws Exception {
			// Get the list of files.
			List<?> fileList = (List<?>) this.map.get("Files");
			File[] f = new File[fileList.size()];
			
			// Create an array of Workbin.Folder from the list.
			for (int i = 0; i < fileList.size(); i++) {
				f[i] = new File(this.ivle, (Map<?, ?>) fileList.get(i));
			}
			
			return f;
		}
		
		/**
		 * Method: getFolders
		 * Retrieves the folders within this folder.
		 * 
		 * @return Workbin.Folder[]
		 */
		public Workbin.Folder[] getFolders() throws Exception {
			// Get the list of folders.
			List<?> folderList = (List<?>) this.map.get("Folders");
			Folder[] f = new Folder[folderList.size()];
			
			// Create an array of Workbin.Folder from the list.
			for (int i = 0; i < folderList.size(); i++) {
				f[i] = new Folder(this.ivle, (Map<?, ?>) folderList.get(i));
			}
			
			return f;
		}
		
		// }}}
	}
	
	/**
	 * Represents a workbin file.
	 * @author Wong Yong Jie
	 */
	public class File extends IVLEObject {
		// {{{ properties
		
		/** Commenter */
		public final User commenter;
		
		/** Creator */
		public final User creator;
		
		/** File description */
		public final String fileDescription;
		
		/** File name */
		public final String fileName;
		
		/** File remarks */
		public final String fileRemarks;
		
		/** File remarks attachment */
		public final String fileRemarksAttachment;
		
		/** File size */
		/** This should be double... */
		public final Integer fileSize;
		
		/** File type */
		public final String fileType;
		
		/** Has this file been downloaded? */
		public final Boolean isDownloaded;
		
		// }}}
		// {{{ methods
		
		File(IVLE ivle, Map<?, ?> map) throws Exception {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.ID = extractString("ID", map);
			this.commenter = new User(this.ivle, (Map<?, ?>) map.get("Commenter"));
			this.creator = new User(this.ivle, (Map<?, ?>) map.get("Creator"));
			this.fileDescription = extractString("FileDescription", map);
			this.fileName = extractString("FileName", map);
			this.fileRemarks = extractString("FileRemarks", map);
			this.fileRemarksAttachment = extractString("FileRemarksAttachment", map);
			this.fileSize = extractInt("FileSize", map);
			this.fileType = extractString("FileType", map);
			this.isDownloaded = extractBool("isDownloaded", map);
		}
		
		/**
		 * Method: getDownloadURL
		 * Obtains the URL for file download.
		 * 
		 * @return URL
		 */
		public URL getDownloadURL() throws Exception {
			// Check for sanity.
			if (this.ivle.apiKey == null || this.ivle.authToken == null) {
				throw new IllegalStateException();
			}
			
			// Craft the URL.
			String wburl_str = "https://ivle.nus.edu.sg/api/downloadfile.ashx?";
			wburl_str = wburl_str.concat("APIKey=").concat(this.ivle.apiKey);
			wburl_str = wburl_str.concat("&AuthToken").concat(this.ivle.authToken);
			wburl_str = wburl_str.concat("&ID=").concat(this.ID);
			wburl_str = wburl_str.concat("&target=").concat("workbin");
			URL wburl = new URL(wburl_str);
			return wburl;
		}
		
		/**
		 * Method: download
		 * Downloads the specified file.
		 */
		public InputStream download() throws Exception {
			// Get the URL.
			URL wburl = this.getDownloadURL();
			
			// Create the connection.
			HttpsURLConnection urlConnection = (HttpsURLConnection) wburl.openConnection();
			InputStream in = new BufferedInputStream(urlConnection.getInputStream());
			
			// Pass our input stream to the requester.
			return in;
		}
		
		// }}}
	}
	
	// }}}
}
