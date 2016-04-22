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
 * Class who wrap userRegistry tag
 * 
 */

public class UserRegistry extends Base{
	public static String TAG="userRegistry";

	/*
	 * Static method to find 'userRegistry' tags as child of document, filtered 
	 */
	public static UserRegistry findOrCreate(Document doc) throws Exception{
		NodeList nl = doc.getElementsByTagName(TAG);
		UserRegistry res=null;
		if (nl!=null && nl.getLength()>0){
			Node node = nl.item(0);
			res=new UserRegistry(node,doc);
		}
		else{
			Element el = doc.createElement(TAG);
			doc.appendChild(el);
			res=new UserRegistry(el,doc);
		}
		return res;		
	}
	
	/*
	 * Simple constructor with xml attrs 
	 */
	@SuppressWarnings("serial")
	public UserRegistry(Element el,Document doc){
		super(el,doc);
		setAttributes(new HashMap<String,String>(){{
			put("xmlns","http://www.geoserver.org/security/users"); 
			put("version","1.0");
		}});
	}

	/*
	 * Simple constructor with xml attrs 
	 */	
	@SuppressWarnings("serial")
	public UserRegistry(Node nd,Document doc) throws Exception{
		super(nd,doc);
		setAttributes(new HashMap<String,String>(){{
			put("xmlns","http://www.geoserver.org/security/users"); 
			put("version","1.0");
		}});	
	}
}
