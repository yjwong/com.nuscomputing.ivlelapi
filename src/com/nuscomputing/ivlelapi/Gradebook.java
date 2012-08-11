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
	
	// }}}
	// {{{ classes
	
	/**
	 * Class constructor.
	 */
	Gradebook(IVLE ivle, Map<?, ?> map) {
		super(ivle, map);
		
		// Read data from JSON.
		this.categoryTitle = extractString("CategoryTitle");
		this.ID = extractString("ID");
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
		Item(IVLE ivle, Map<?, ?> map) {
			super(ivle, map);
			
			// Read data from JSON.
			this.averageMedianMarks = extractString("AverageMedianMarks");
			this.dateEntered = extractString("DateEntered");
			this.highestLowestMarks = extractString("HighestLowestMarks");
			this.ID = extractString("ID");
			this.itemDescription = extractString("ItemDescription");
			this.itemName = extractString("ItemName");
			this.marksObtained = extractString("MarksObtained");
			this.maxMarks = extractInt("MaxMarks");
			this.percentile = extractString("Percentile");
			this.remark = extractString("Remark");
		}
		
		// }}}
	}
	
	// }}}
}
