package com.nuscomputing.ivlelapi;

import java.util.List;
import java.util.Map;

/**
 * Represents a gradebook.
 * @author Wong Yong Jie
 */

public class Gradebook extends IVLEObject {
	// {{{ properties
	
	/** Category title */
	public final String categoryTitle;
	
	/** Save the map somewhere, because we'll use it */
	private final Map<?, ?> map;
	
	// }}}
	// {{{ classes
	
	/**
	 * Class constructor.
	 */
	Gradebook(IVLE ivle, Map<?, ?> map) {
		// Set our IVLE object.
		this.ivle = ivle;
		
		// Read data from JSON.
		this.map = map;
		this.categoryTitle = extractString("CategoryTitle", map);
		this.ID = extractString("ID", map);
	}
	
	/**
	 * Method: getItems
	 * Gets the list of gradebook items for the module.
	 */
	public Item[] getItems() {
		List<?> itemList = (List<?>) this.map.get("Items");
		Item[] items = new Item[itemList.size()];
		for (int i = 0; i < itemList.size(); i++) {
			items[i] = new Item(this.ivle, (Map<?, ?>) itemList.get(i));
		}
		
		return items;
	}
	
	/**
	 * Represents a gradebook item.
	 * @author Wong Yong Jie
	 */
	public class Item extends IVLEObject {
		// {{{ properties
		
		/** Average/median marks */
		public final String averageMedianMarks;
		
		/** Date entered */
		public final String dateEntered;
		
		/** Highest/lowest marks */
		public final String highestLowestMarks;
		
		/** Item description */
		public final String itemDescription;
		
		/** Item name */
		public final String itemName;
		
		/** Marks obtained */
		public final String marksObtained;
		
		/** Maximum marks */
		public final Integer maxMarks;
		
		/** Percentile */
		public final String percentile;
		
		/** Remark */
		public final String remark;
		
		// }}}
		// {{{ methods
		
		/**
		 * Class constructor.
		 */
		Item() {
			throw new UnsupportedOperationException("This class should not be instantiated directly. Use IVLE instead. ");
		}
		
		Item(IVLE ivle, Map<?, ?> map) {
			// Set our IVLE object.
			this.ivle = ivle;
			
			// Read data from JSON.
			this.averageMedianMarks = extractString("AverageMedianMarks", map);
			this.dateEntered = extractString("DateEntered", map);
			this.highestLowestMarks = extractString("HighestLowestMarks", map);
			this.ID = extractString("ID", map);
			this.itemDescription = extractString("ItemDescription", map);
			this.itemName = extractString("ItemName", map);
			this.marksObtained = extractString("MarksObtained", map);
			this.maxMarks = extractInt("MaxMarks", map);
			this.percentile = extractString("Percentile", map);
			this.remark = extractString("Remark", map);
		}
		
		// }}}
	}
	
	// }}}
}
