package it.egeos.geoserver.utils.tags;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Class who wrap userRoles tag
 * 
 */

public class UserRoles extends Base {
	public static String TAG="userRoles";
	
	/*
	 * Static method to find 'userRoles' tags as child of 'userList' tag, filtered 
	 */
	public static UserRoles findOrCreate(UserList ul,Map<String,String> filters) throws Exception{
		return ul.getElementByTagAndFilters(TAG,UserRoles.class,filters);		
	}

	/*
	 * Static method to find 'userRoles' tags as child of 'userList' tag 
	 */
	public static List<UserRoles> list(UserList ul) throws Exception{
		return ul.getElementsByTagName(TAG, UserRoles.class);
	}

	/*
	 * Simple constructor
	 */
	public UserRoles(Element el, Document doc) {
		super(el, doc);
	}

	/*
	 * Simple constructor
	 */
	public UserRoles(Node nd, Document doc) throws Exception {
		super(nd, doc);
	}
}
