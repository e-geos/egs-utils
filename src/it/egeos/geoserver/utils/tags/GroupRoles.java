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
 * Class who wrap groupRoles tag
 * 
 */

public class GroupRoles extends Base {
	public static String TAG="groupRoles";
	
	/*
	 * Static method to find 'groupRoles' tags as child of 'groupList' tag, filtered 
	 */
	public static GroupRoles findOrCreate(GroupList gl,Map<String,String> filters) throws Exception{
		return gl.getElementByTagAndFilters(TAG,GroupRoles.class,filters);		
	}

	/*
	 * Static method to find 'groupRoles' tags as child of 'groupList' tag 
	 */
	public static List<GroupRoles> list(GroupList gl) throws Exception{
		return gl.getElementsByTagName(TAG, GroupRoles.class);
	}
	
	/*
	 * Simple constructor
	 */
	public GroupRoles(Element el, Document doc) {
		super(el, doc);
	}

	/*
	 * Simple constructor
	 */
	public GroupRoles(Node nd, Document doc) throws Exception {
		super(nd, doc);
	}	
}
