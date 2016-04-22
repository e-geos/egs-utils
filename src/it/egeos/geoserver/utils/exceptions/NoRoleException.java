package it.egeos.geoserver.utils.exceptions;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class wraps No Role Exception: no role is found  
 * 
 */

public class NoRoleException extends Exception {
	private static final long serialVersionUID = -3467529237710062105L;

	public NoRoleException(String message) {
		super(message);
	}
}
