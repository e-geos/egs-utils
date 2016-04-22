package it.egeos.geoserver.utils.Abstracts;

import it.egeos.geoserver.utils.interfaces.Manager;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class is a generic wrapper for Geoserver xml file.
 * 
 */

public class XMLManager implements Manager {
	//Filename: /some/path/file.xml
	protected String filename=null;
	
	//Parsed xml document
	protected Document doc=null;

	public XMLManager(String fn) throws IOException{
		filename=fn;		
		load(fn);
	}

	/** 
	 * Load file using 'fn'
	 */
	public void load(String fn) throws IOException{
		if (fn!=null && !fn.trim().isEmpty()){
	        DocumentBuilder builder=null;
	        try {
	            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
	            builder = fac.newDocumentBuilder();
	        } 
	        catch (ParserConfigurationException e1) {
	            throw new IOException(e1);
	        }
            FileInputStream is = null;
            try {
                is = new FileInputStream(filename);
				doc = builder.parse(is);
				doc.getDocumentElement().normalize();
            } 
            catch (java.io.FileNotFoundException e){
            	doc = builder.newDocument();
            }
            catch (SAXException e) {
                throw new IOException(e);
            } 
            finally {
            	IOUtils.closeQuietly(is);
            }                       
		}
		else
			throw new FileNotFoundException("filename is not given"); 
	}

	/** 
	 * Save 'doc' in a file called 'fn'
	 */
	public void save() throws IOException, TransformerConfigurationException{
		if (filename!=null){
            Transformer tx;
			try {
				tx = TransformerFactory.newInstance().newTransformer();
	            tx.setOutputProperty(OutputKeys.METHOD, "XML");
	            tx.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tx.setOutputProperty(OutputKeys.INDENT, "yes");
	            OutputStream out = new BufferedOutputStream(new FileOutputStream(filename));
	            try {
	                 tx.transform(new DOMSource(doc), new StreamResult(out));
	                 out.flush();
	             }
	             finally {
	                 IOUtils.closeQuietly(out);
	             }
			} 
			catch (TransformerConfigurationException e) {
				throw e;
			} 
			catch (TransformerFactoryConfigurationError e) {
				throw e;
			}
			catch (Exception e) {
	            throw new IOException(e);
	        } 
		}
		else
			throw new FileNotFoundException("filename is not given");			
	}
	
	/*
	 * Reload function 
	 */
	public void reload() throws IOException{
		load(filename);
	}
}
