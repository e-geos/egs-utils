package it.egeos.geoserver.utils.tags;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Generic class for xml tag of geoserver files
 * 
 */

abstract public class Base {
	public static String TAG="notdefine";
	protected Document document;
	protected Element element;
	
	/*
	 * Constructor: doc is the XML, el is the current tag as element 
	 */
	public Base(Element el,Document doc){
		element=el;
		document=doc;
	}

	/*
	 * Constructor: doc is the XML, nd is the current tag as node 
	 */
	public Base(Node nd,Document doc) throws Exception{
		element = castTo(nd);
		document=doc;
	}
	
	/*
	 * Cast utility
	 */
	public Element castTo(Node nd) throws Exception{
		if (nd.getNodeType() == Node.ELEMENT_NODE)
			return (Element)nd;
		else
			throw new Exception("node is not an ELEMENT_NODE");			
	}
	
	/*
	 * Filtering children by tag 
	 */
	@SuppressWarnings("serial")
	public <T> List<T> getElementsByTagName(String tag,Class<T> wrapper) throws Exception{
		final NodeList nl = element.getElementsByTagName(tag);		
		final Constructor<T> cst = wrapper.getConstructor(Node.class,Document.class);			

		return new ArrayList<T>(){{
			if (nl!=null)
				for(int i=0; i<nl.getLength();i++)
					add(cst.newInstance(nl.item(i),document));
		}};
	}

	/*
	 * Filtering children by tag, but takes only first 
	 */
	public <T> T getElementByTagName(String tag,Class<T> wrapper) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		NodeList nl = element.getElementsByTagName(tag);
		T res=null;
		if (nl!=null && nl.getLength()>0){
			Node node = nl.item(0);
			Constructor<T> constructor = wrapper.getConstructor(Node.class,Document.class);			
			res=constructor.newInstance(node,document);
		}
		else{
			Element el = document.createElement(tag);
			element.appendChild(el);
			Constructor<T> constructor = wrapper.getConstructor(Element.class,Document.class);			
			res=constructor.newInstance(el,document);
		}
		return res;		
	}

	/*
	 * Filtering children by tag and filters, but takes only first 
	 */	
	public <T> T getElementByTagAndFilters(String tag,Class<T> wrapper,Map<String,String> filters) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		NodeList nl = element.getElementsByTagName(tag);
		T res=null;
		Node found=null;
		if (nl!=null && nl.getLength()>0)
			for(int i=0; i<nl.getLength();i++)
				found=match(nl.item(i),filters,found);

		//fall back: if there's no items at all or equals to, we create a new element
		if (found!=null){
			Constructor<T> constructor = wrapper.getConstructor(Node.class,Document.class);			
			res=constructor.newInstance(found,document);
		}
		else{
			Element el = document.createElement(tag);
			element.appendChild(el);
			for(String k:filters.keySet())
				el.setAttribute(k,filters.get(k));			
			Constructor<T> constructor = wrapper.getConstructor(Element.class,Document.class);			
			res=constructor.newInstance(el,document);
		}
		return res;		
	}
	
	/*
	 * Delete every children where tag matchs
	 */
	public void deleteChildren(String tag){
		NodeList nl = element.getElementsByTagName(tag);
		if(nl!=null)
			for(int i=0; i<nl.getLength();i++)
				element.removeChild(nl.item(i));		
	}

	/*
	 * Delete every children where tag and filters matchs 
	 */
	public void deleteChildren(String tag,Map<String,String> filters){
		NodeList nl = element.getElementsByTagName(tag);
		if (nl!=null)
			for(int i=0; i<nl.getLength();i++)
				if (match(nl.item(i),filters,null)!=null)
					element.removeChild(nl.item(i));
	}
		
	/*
	 * Matching function with a tag and filters
	 */
	private Node match(Node item, Map<String, String> filters, Node found) {
		Node res=found;
		try {
			Element el=castTo(item);
			boolean match=true;
			for(String k:filters.keySet())
				match=match && (el.getAttribute(k).equals(filters.get(k)));					
			
			if (match)
				res=item;			
		}
		catch (Exception e) {
			//nothing happen: this means that is not possible cast item to Element
			//we can live without
		}
		return res;
	}	

	/*
	 * Wrapper for attributes
	 */
	public String getAttribute(String attr){
		return element.getAttribute(attr);
	}

	/*
	 * Wrapper for attributes
	 */
	public void setAttribute(String k,String v){
		element.setAttribute(k,v);
	}

	/*
	 * Wrapper for attributes
	 */
	public void setAttributes(Map<String,String> attrs){
		for (String k:attrs.keySet())
			element.setAttribute(k,attrs.get(k));	
	}
}
