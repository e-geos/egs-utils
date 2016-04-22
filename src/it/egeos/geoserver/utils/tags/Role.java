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
 * Class who wrap role tag
 * 
 */

public class Role extends Base {
	public static String TAG="role";
	
	/*
	 * Static method to find 'role' tags as child of 'roleList' tag, filtered 
	 */
	public static Role findOrCreate(RoleList rl,Map<String,String> filters) throws Exception{
		return rl.getElementByTagAndFilters(TAG,Role.class,filters);		
	}
	
	/*
	 * Static method to find 'role' tags as child of 'roleList' tag 
	 */
	public static List<Role> list(RoleList rl) throws Exception{
		return rl.getElementsByTagName(TAG, Role.class);
	}

	/*
	 * Simple constructor
	 */
	public Role(Element el, Document doc) {
		super(el, doc);
	}

	/*
	 * Simple constructor
	 */
	public Role(Node nd, Document doc) throws Exception {
		super(nd, doc);
	}
}
