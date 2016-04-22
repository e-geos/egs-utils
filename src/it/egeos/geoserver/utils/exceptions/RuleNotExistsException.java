package it.egeos.geoserver.utils.exceptions;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class wraps Rule Not Exists Exception: probably some one try to delete again a rule  
 * 
 */

public class RuleNotExistsException extends Exception {
	private static final long serialVersionUID = 6355439230002086856L;

	public RuleNotExistsException(String message) {
		super(message);
	}
}
