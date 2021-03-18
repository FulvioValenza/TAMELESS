package it.polito.dp2.TAMELESS.ass;

import java.util.Set;

/**
 * An interface for accessing the main information associated to a system threat.
 */

public interface ThreatReader {

	/**
	 * Gives the list of authors of this item (an array of strings that are the names of the authors).
	 * @return the list of authors of the item.
	 *
	public String[] getAuthors();

	/**
	 * Gives the title of this item (a string).
	 * @return the title of the item.
	 *
	public String getTitle();
	
	/**
	 * Gives the subtitle of this item (a string) or null if the item has no subtitle.
	 * @return the subtitle of the item.
	 *
	public String getSubtitle();
	
	/**
	 * Gives the items that cite this item
	 * @return a set of interfaces that can be used to read information about the items that cite this item
	 *
	public Set<ItemReader> getCitingItems();
	*/
}
