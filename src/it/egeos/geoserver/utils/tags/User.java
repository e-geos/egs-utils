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
 * Class who wrap user tag
 * 
 */

public class User extends Base {
	public static String TAG="user";
	
	/*
	 * Static method to find 'user' tags as child of 'users' tag, filtered 
	 */
	public static User findOrCreate(Users users,Map<String,String> filters) throws Exception{
		return users.getElementByTagAndFilters(TAG,User.class,filters);		
	}

	/*
	 * Static method to find 'user' tags as child of 'users' tag
	 */
	public static List<User> list(Users users) throws Exception{
		return users.getElementsByTagName(TAG, User.class);
	}
	
	/*
	 * Simple constructor
	 */
	public User(Element el, Document doc) {
		super(el, doc);
		element.setAttribute("enabled", "true");
	}

	/*
	 * Simple constructor
	 */
	public User(Node nd, Document doc) throws Exception {
		super(nd, doc);
		element.setAttribute("enabled", "true");
	}
}
