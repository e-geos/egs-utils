package it.egeos.geoserver.sample.GeoserverUserManage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.relation.RoleNotFoundException;

import it.egeos.geoserver.utils.RulesManager;
import it.egeos.geoserver.utils.exceptions.BadAccessModeException;
import it.egeos.geoserver.utils.exceptions.NoRoleException;
import it.egeos.geoserver.utils.exceptions.RuleExistsException;
import it.egeos.geoserver.utils.exceptions.RuleNotExistsException;

public class TestRuleManager {

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		System.err.println("Start");
		try {			
			RulesManager rm=new RulesManager("/tmp/layers.properties");
			
			System.out.println("Test001: add rule aworkspace.alayer.a=role001,role002,role003");
			try {
				rm.createRule("aworkspace","alayer", RulesManager.ADMIN,new ArrayList<String>(){{
					add("role001");
					add("role002");
					add("role003");
				}});
				System.out.println("Well done: rule created.");
			} 
			catch (RuleExistsException e) {
				System.err.println("Bad fault: this rule esists");
			} 
			catch (NoRoleException e) {
				System.err.println("Bad fault: roles are empty");
			}

			System.out.println("Test002: add rule that exists aworkspace.alayer.a=role001,role002,role003");
			try {
				rm.createRule("aworkspace","alayer", RulesManager.ADMIN,new ArrayList<String>(){{
					add("role001");
					add("role002");
					add("role003");
				}});
				System.out.println("Bad fault: the rule we try to create exists in file");
			} 
			catch (RuleExistsException e) {
				System.err.println("Well done: this rule exists so exception is thrown");
			} 
			catch (NoRoleException e) {
				System.err.println("Bad fault: roles are empty");
			}
			
			System.out.println("Test003: add rule with no roles aworkspace1.alayer.a=");
			try {
				rm.createRule("aworkspace1","alayer", RulesManager.ADMIN,new ArrayList<String>());
				System.out.println("Bad fault: roles is empty");
			} 
			catch (RuleExistsException e) {
				System.err.println("Bad fault: rules exists");				
			} 
			catch (NoRoleException e) {
				System.err.println("Well done: role is empty so exception is thrown");
			}
			
			System.out.println("Test004: append role to a rule that exists aworkspace.alayer.a");		
			try {
				rm.addToRule("aworkspace", "alayer", RulesManager.ADMIN, new ArrayList<String>(){{
						add("role004");
					}});
				System.out.println("Well done: updated");
			} 
			catch (RuleNotExistsException e1) {			
				System.err.println("Bad fault: this rule esists");
			} 
			catch (NoRoleException e1) {
				System.err.println("Bad fault: roles is empty");				
			}
			
			System.out.println("Test005: remove role002 role to a rule that exists aworkspace.alayer.a");		
			try {
				rm.delToRule("aworkspace", "alayer", RulesManager.ADMIN, new ArrayList<String>(){{
						add("role002");
					}},false);
				System.out.println("Well done: updated");
			} 
			catch (RuleNotExistsException e1) {			
				System.err.println("Bad fault: this rule esists");
			} 
			catch (NoRoleException e1) {
				System.err.println("Bad fault: roles is empty");				
			} catch (RoleNotFoundException e) {
				System.err.println("Bad fault: role doesn't exist");
			}
			
			rm.save();
			
			System.out.println("Listing rules");
			HashMap<String, List<String>> rules = rm.getRules();
			for(String k:rules.keySet()){
				System.out.println("\t"+k);
				for(String v:rules.get(k))
					System.out.println("\t\t"+v);
			}
			System.out.println("end.");
		} 
		catch (IOException e) {			
			e.printStackTrace();
		} 
		catch (BadAccessModeException e) {			
			e.printStackTrace();
		}  
		
		System.out.println("end.");
	}

}
