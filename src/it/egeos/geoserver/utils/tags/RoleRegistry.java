package it.egeos.geoserver.utils.tags;

import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Class who wrap roleRegistry tag
 * 
 */

public class RoleRegistry extends Base{
	public static String TAG="roleRegistry";

	/*
	 * Static method to find 'roleRegistry' tags as child of document
	 */
	public static RoleRegistry findOrCreate(Document doc) throws Exception{
		NodeList nl = doc.getElementsByTagName(TAG);
		RoleRegistry res=null;
		if (nl!=null && nl.getLength()>0){
			Node node = nl.item(0);
			res=new RoleRegistry(node,doc);
		}
		else{
			Element el = doc.createElement(TAG);
			doc.appendChild(el);
			res=new RoleRegistry(el,doc);
		}
		return res;		
	}
	
	/*
	 * Simple constructor
	 */
	@SuppressWarnings("serial")
	public RoleRegistry(Element el,Document doc){
		super(el,doc);
		setAttributes(new HashMap<String,String>(){{
			put("xmlns","http://www.geoserver.org/security/roles"); 
			put("version","1.0");
		}});
	}
	
	/*
	 * Simple constructor
	 */
	@SuppressWarnings("serial")
	public RoleRegistry(Node nd,Document doc) throws Exception{
		super(nd,doc);
		setAttributes(new HashMap<String,String>(){{
			put("xmlns","http://www.geoserver.org/security/roles"); 
			put("version","1.0");
		}});	
	}
}
