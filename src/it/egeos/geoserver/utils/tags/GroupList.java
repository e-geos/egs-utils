package it.egeos.geoserver.utils.tags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Class who wrap groupList tag
 * 
 */

public class GroupList extends Base {
	public static String TAG="groupList";
	
	/*
	 * Static method to find 'groupList' tags as child of 'roleRegistry' tag, filtered 
	 */
	public static GroupList findOrCreate(RoleRegistry rr) throws Exception{
		return rr.getElementByTagName(TAG,GroupList.class);		
	}

	/*
	 * Simple contructor
	 */
	public GroupList(Element el,Document doc){
		super(el,doc);
	}

	/*
	 * Simple contructor
	 */
	public GroupList(Node nd,Document doc) throws Exception{
		super(nd,doc);
	}
}
