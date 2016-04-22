package it.egeos.geoserver.utils.interfaces;

import it.egeos.geoserver.utils.exceptions.BadAccessModeException;

import java.io.IOException;

import javax.xml.transform.TransformerConfigurationException;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * Generic interface for managers based on files, so load, reload and save
 * are necessary methods
 * 
 */

public interface Manager {
	public void load(String fn) throws IOException;
	public void save() throws IOException, TransformerConfigurationException,BadAccessModeException;
	public void reload() throws IOException;
}
