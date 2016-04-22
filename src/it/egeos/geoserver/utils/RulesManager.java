package it.egeos.geoserver.utils;

import it.egeos.geoserver.utils.exceptions.BadAccessModeException;
import it.egeos.geoserver.utils.exceptions.NoRoleException;
import it.egeos.geoserver.utils.exceptions.RuleExistsException;
import it.egeos.geoserver.utils.exceptions.RuleNotExistsException;
import it.egeos.geoserver.utils.interfaces.Manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.management.relation.RoleNotFoundException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class implements a wrapper for Geoserver layers.properties file.
 * 
 */

public class RulesManager implements Manager{
	//Statics values for acl of geoserver
	public final static char ADMIN='a'; 
	public final static char READ='r';
	public final static char WRITE='w';
	
	//Separator for key,value file layers.properties
	public final static String SEPARATOR="=";
	
	//Separator for values
	public final static String VSEPARATOR=",";
	
	//Filename, usually layers.properties
	private String filename=null;
		
	//Map key,value read by layers.properties
	private Map<String,String> cfg=null;
	
	/*
	 * Constructor with filename, usually layers.properties
	 */
	public RulesManager(String filename) throws IOException{
		this.filename=filename;
		load(filename);
	}
	
	/*
	 * Load data from file
	 */
	@SuppressWarnings("serial")
	public void load(String filename) throws IOException{		
        try {
        	final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
            cfg=new HashMap<String,String>(){{
            	String[] parts;
            	String row=br.readLine();
            	while(row!=null){
            		if (!row.startsWith("#")){
	            		parts = splitRow(row);
	            		put(parts[0],parts[1]);	            		
            		}
            		row=br.readLine();
            	}            
            }};
            IOUtils.closeQuietly(br);
        } 
        catch (FileNotFoundException e){
        	cfg=null;
        	throw e;
        }
	}
	
	/*
	 * Reload data from file
	 */
	public void reload() throws IOException{
		load(filename);
	}

	/*
	 * Save data to file
	 */
	public void save() throws IOException, BadAccessModeException{
		final BufferedWriter br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
		for(String k:cfg.keySet())
			br.write(joinRow(k,cfg.get(k))+"\n");
		IOUtils.closeQuietly(br);
	}
		
	/*
	 * Get all rules from file
	 */
	@SuppressWarnings("serial")
	public HashMap<String,List<String>> getRules(){
		return new HashMap<String, List<String>>(){{
			for (String r:cfg.keySet()){				
				String x = cfg.get(r);
				String[] y = x.split(VSEPARATOR);
				List<String> z = Arrays.asList(y);
				put(r,z);
			}
		}};				
	}
	
	/*
	 * Create a new rule, if exists throws exception  
	 */
	public String createRule(String workspace,String layer,char access_mode, List<String> roles) throws BadAccessModeException, RuleExistsException, NoRoleException{
		String rulePath=buildRulePath(workspace, layer, access_mode);
		String r=StringUtils.join(roles,VSEPARATOR);
		if (cfg.containsKey(rulePath))
			throw new RuleExistsException("Rule '"+rulePath+"' yet exists ");
		else if(roles==null || roles.size()<1)
			throw new NoRoleException("You cannot update a rule with no roles ('roles' parameter list is empty)");
		else
			cfg.put(rulePath,r);		
		return joinRow(rulePath,r);
	}
	
	/*
	 * Delete a rule, if not exists throws exception
	 */
	public void deleteRule(String workspace,String layer,char access_mode) throws BadAccessModeException, RuleNotExistsException{
		deleteRule(buildRulePath(workspace, layer, access_mode));
	}

	/*
	 * Delete a rule, if not exists throws exception
	 */
	public void deleteRule(String rulePath) throws RuleNotExistsException{
		if (cfg.containsKey(rulePath))
			cfg.remove(rulePath);		
		else
			throw new RuleNotExistsException("Rule '"+rulePath+"' not exists");		
	}	
	
	/*
	 * Add a list of roles to an existing rule, if not exists throws exception
	 */
	public void addToRule(String workspace,String layer,char access_mode, List<String> roles) throws BadAccessModeException, RuleNotExistsException, NoRoleException{
		String rulePath=buildRulePath(workspace, layer, access_mode);
		String curr = cfg.get(rulePath);
		if(curr==null)
			throw new RuleNotExistsException("Rule '"+rulePath+"' not exists");
		else if(roles==null || roles.size()<1)
			throw new NoRoleException("You cannot update a rule with no roles ('roles' parameter list is empty)");
		else{
			//Adding currents to roles
			roles.addAll(Arrays.asList(curr.split(VSEPARATOR)));
			//Build unique list			
			cfg.put(rulePath,StringUtils.join(new HashSet<String>(roles),VSEPARATOR));
		}
	}
	
	/*
	 * Delete a list of roles form a rule, ignore_missing is used to avoid missing exception when role
	 * is not found
	 */
	public void delToRule(String workspace,String layer,char access_mode, List<String> roles,boolean ignore_missing) throws BadAccessModeException, RuleNotExistsException, NoRoleException, RoleNotFoundException{
		String rulePath=buildRulePath(workspace, layer, access_mode);
		String curr = cfg.get(rulePath);
		if(curr==null)
			throw new RuleNotExistsException("Rule '"+rulePath+"' not exists");
		else if(roles==null || roles.size()<1)
			throw new NoRoleException("You cannot update a rule with no roles ('roles' parameter list is empty)");
		else{		
			List<String> act = new ArrayList<String>(Arrays.asList(curr.split(VSEPARATOR)));		
			for (String role:roles)
				if (!act.remove(role) && !ignore_missing)
					throw new RoleNotFoundException("Role '"+role+"' is not found, so you can't remove it (set ignore missing as true if you want this exception)");
			if (act.size()>0)
				cfg.put(rulePath,StringUtils.join(act,VSEPARATOR));
			else 
				cfg.remove(rulePath);
		}
	}
	
	/*
	 * Split a string using SEPARATOR
	 */
	private String[] splitRow(String row){		
		return row.split(SEPARATOR);
	}

	/*
	 * Join two strings using SEPARATOR
	 */
	private String joinRow(String rulePath, String roles) throws BadAccessModeException{
		return rulePath+SEPARATOR+roles;
	}
		
	/*
	 * Build a rule path
	 */
	private String buildRulePath(String workspace,String layer,char access_mode) throws BadAccessModeException{
		String res="";
		switch (access_mode) {
		case ADMIN:
		case READ:
		case WRITE:			
			res=formatWSName(ifNull(workspace))+"."+ifNull(layer)+"."+access_mode;
			break;
		default:			
			throw new BadAccessModeException("The mode '"+access_mode+"' is not allowed, use RulesManager.ADMIN, RulesManager.READ or RulesManager.WRITE instead");
		}		
		return res;		
	}

	private String formatWSName(String ws){
		return ws.replaceAll("\\.", "\\\\\\\\.");
	}
	
	/*
	 * If s is null returns * 
	 */
	private String ifNull(String s){
		return (s==null || s.trim().isEmpty())?"*":s;		
	}
}
