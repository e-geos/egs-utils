package it.egeos.geoserver.utils.tags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Class who wrap roleList tag
 * 
 */

public class RoleList extends Base {
	public static String TAG="roleList";
	
	/*
	 * Static method to find 'roleList' tags as child of 'roleRegistry tag, filtered 
	 */
	public static RoleList findOrCreate(RoleRegistry rr) throws Exception{
		return rr.getElementByTagName(TAG,RoleList.class);		
	}

	/*
	 * Simple constructor
	 */
	public RoleList(Element el,Document doc){
		super(el,doc);
	}

	/*
	 * Simple constructor
	 */
	public RoleList(Node nd,Document doc) throws Exception{
		super(nd,doc);
	}
}
