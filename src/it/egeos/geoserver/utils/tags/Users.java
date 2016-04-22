package it.egeos.geoserver.utils.tags;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Users extends Base {
	public static String TAG="users";
	
	public static Users findOrCreate(UserRegistry ur) throws Exception{
		return ur.getElementByTagName(TAG,Users.class);		
	}

	public Users(Element el,Document doc){
		super(el,doc);
	}

	public Users(Node nd,Document doc) throws Exception{
		super(nd,doc);
	}
}
