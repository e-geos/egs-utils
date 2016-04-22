package it.egeos.geoserver.utils.tags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Class who wrap userList tag
 * 
 */

public class UserList extends Base {
	public static String TAG="userList";
	
	/*
	 * Static method to find 'userList' tags as child of 'roleRegistry' tag 
	 */
	public static UserList findOrCreate(RoleRegistry rr) throws Exception{
		return rr.getElementByTagName(TAG,UserList.class);		
	}

	/*
	 * Simple constructor
	 */
	public UserList(Element el,Document doc){
		super(el,doc);
	}

	/*
	 * Simple constructor
	 */
	public UserList(Node nd,Document doc) throws Exception{
		super(nd,doc);
	}
}
