package it.egeos.geoserver.utils.exceptions;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class wraps Rule Exists Exception: probably some one try to creater again a rule  
 * 
 */

public class RuleExistsException extends Exception {
	private static final long serialVersionUID = -7419284451290347672L;

	public RuleExistsException(String message) {
		super(message);
	}
}
