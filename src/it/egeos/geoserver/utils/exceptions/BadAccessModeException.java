package it.egeos.geoserver.utils.exceptions;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class wraps Bad Access Mode: rules in geoserver accepts only R,W,A 
 * 
 */

public class BadAccessModeException extends Exception {
	private static final long serialVersionUID = 6093267190699724822L;

	public BadAccessModeException(String msg) {
		super(msg);
	}	
}
