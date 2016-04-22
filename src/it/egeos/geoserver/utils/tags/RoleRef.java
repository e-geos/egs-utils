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
 * Class who wrap roleRef tag
 * 
 */

public class RoleRef extends Base {
	public static String TAG="roleRef";
	
	/*
	 * Static method to find 'roleRef' tags as child of 'userRoles' tag, filtered 
	 */
	public static RoleRef findOrCreate(UserRoles ur,Map<String,String> filters) throws Exception{
		return ur.getElementByTagAndFilters(TAG,RoleRef.class,filters);		
	}

	/*
	 * Static method to find 'roleRef' tags as child of 'groupRoles' tag, filtered 
	 */
	public static RoleRef findOrCreate(GroupRoles gr,Map<String,String> filters) throws Exception{
		return gr.getElementByTagAndFilters(TAG,RoleRef.class,filters);		
	}
	
	/*
	 * Static method to find 'roleRef' tags as child of 'userRoles' tag 
	 */
	public static List<RoleRef> list(UserRoles ur) throws Exception{
		return ur.getElementsByTagName(TAG, RoleRef.class);
	}
	
	/*
	 * Static method to find 'roleRef' tags as child of 'groupRoles' tag 
	 */
	public static List<RoleRef> list(GroupRoles gr) throws Exception{
		return gr.getElementsByTagName(TAG, RoleRef.class);
	}

	/*	
	 * Simple constructor
	 */
	public RoleRef(Element el, Document doc) {
		super(el, doc);
	}

	/*	
	 * Simple constructor
	 */
	public RoleRef(Node nd, Document doc) throws Exception {
		super(nd, doc);
	}
}
