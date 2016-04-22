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
 * Class who wrap Group tag
 * 
 */

public class Group extends Base  {
	public static String TAG="group";
	
	/*
	 * Static method to find 'group' tags as child of 'groups' tag, filtered 
	 */
	public static Group findOrCreate(Groups groups,Map<String,String> filters) throws Exception{
		return groups.getElementByTagAndFilters(TAG,Group.class,filters);		
	}

	/*
	 * Static method to find all 'group' tags as child of 'groups' tag
	 */
	public static List<Group> list(Groups groups) throws Exception{
		return groups.getElementsByTagName(TAG, Group.class);
	}

	/*
	 * Creates an enabled group
	 */
	public Group(Element el, Document doc) {
		super(el, doc);
		element.setAttribute("enabled", "true");
	}

	/*
	 * Creates an enabled group
	 */
	public Group(Node nd, Document doc) throws Exception {
		super(nd, doc);
		element.setAttribute("enabled", "true");
	}
}
