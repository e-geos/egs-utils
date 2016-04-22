package it.egeos.geoserver.utils.tags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Class who wrap groups tag
 * 
 */

public class Groups extends Base {
	public static String TAG="groups";
	
	/*
	 * Static method to find 'groups' tags as child of 'userRegistry' tag, filtered 
	 */
	public static Groups findOrCreate(UserRegistry ur) throws Exception{
		return ur.getElementByTagName(TAG,Groups.class);		
	}

	/*
	 * Simple constructor
	 */
	public Groups(Element el,Document doc){
		super(el,doc);
	}

	/*
	 * Simple constructor
	 */
	public Groups(Node nd,Document doc) throws Exception{
		super(nd,doc);
	}
}
