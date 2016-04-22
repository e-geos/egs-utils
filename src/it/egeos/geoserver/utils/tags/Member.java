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
 * Class who wrap Member tag
 * 
 */

public class Member extends Base {
	public static String TAG="member";
	
	/*
	 * Static method to find 'member' tags as child of 'group' tag, filtered 
	 */
	public static Member findOrCreate(Group group,Map<String,String> filters) throws Exception{
		return group.getElementByTagAndFilters(TAG,Member.class,filters);		
	}

	/*
	 * Static method to find 'member' tags as child of 'group' tag
	 */
	public static List<Member> list(Group group) throws Exception{
		return group.getElementsByTagName(TAG, Member.class);
	}
	
	/*
	 * Simple constructor 
	 */
	public Member(Element el, Document doc) {
		super(el, doc);
	}

	/*
	 * Simple constructor 
	 */
	public Member(Node nd, Document doc) throws Exception {
		super(nd, doc);
	}
}
