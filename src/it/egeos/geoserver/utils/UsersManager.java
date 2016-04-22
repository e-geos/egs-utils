package it.egeos.geoserver.utils;

import it.egeos.geoserver.utils.Abstracts.XMLManager;
import it.egeos.geoserver.utils.tags.Group;
import it.egeos.geoserver.utils.tags.Groups;
import it.egeos.geoserver.utils.tags.Member;
import it.egeos.geoserver.utils.tags.User;
import it.egeos.geoserver.utils.tags.UserRegistry;
import it.egeos.geoserver.utils.tags.Users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class implements a wrapper for Geoserver users.xml file.
 * 
 */

public class UsersManager extends XMLManager{     	
	/*
	 * Constructor, fn is the data filename, usually users.xml
	 */
	public UsersManager(String fn) throws IOException {
		super(fn);
	}
	
	/**
	 * Returns the list of all users 
	 */
	@SuppressWarnings("serial")
	public ArrayList<String> getUsers() throws Exception{
		return new ArrayList<String>(){{
			UserRegistry ur = UserRegistry.findOrCreate(doc);
			Users users = Users.findOrCreate(ur);
			for(User u:User.list(users))
				add(u.getAttribute("name"));
		}};
	}

	/**
	 * Returns the list of all groups 
	 */
	@SuppressWarnings("serial")
	public ArrayList<String> getGroups() throws Exception{
		return new ArrayList<String>(){{
			UserRegistry ur = UserRegistry.findOrCreate(doc);
			Groups groups = Groups.findOrCreate(ur);
			for(Group g:Group.list(groups))
				add(g.getAttribute("name"));
		}};
	}
	
	/**
	 * Returns the list of all members divided by group 
	 */
	@SuppressWarnings("serial")
	public HashMap<String,ArrayList<String>> getMembers() throws Exception{
		return new HashMap<String,ArrayList<String>>(){{
			UserRegistry ur = UserRegistry.findOrCreate(doc);
			Groups groups = Groups.findOrCreate(ur);
			for(Group g:Group.list(groups)){
				final Group tmp=g;
				put(g.getAttribute("name"),new ArrayList<String>(){{
					for (Member m:Member.list(tmp))
						add(m.getAttribute("username"));				
				}});
			}
		}};
	}
	
	/**
	 * Add a tag <user/> in <users/> section. It makes all non-existings intermediate nodes.
	 */
	@SuppressWarnings("serial")
	public User addUser(final String name,final String passwd) throws Exception{
		UserRegistry ur = UserRegistry.findOrCreate(doc);
		Users users = Users.findOrCreate(ur);
		User usr =User.findOrCreate(users,
				new HashMap<String, String>(){{
					put("name",name);
				}});
		usr.setAttributes(new HashMap<String, String>(){{
			put("password",PasswordEncoder.encoder(passwd));
		}});
		return usr;
	}
	
	/**
	 * Add a tag <group/> in <groups/> section. It makes all non-existings intermediate nodes.
	 */	
	@SuppressWarnings("serial")
	public Group addGroup(final String name) throws Exception{
		UserRegistry ur = UserRegistry.findOrCreate(doc);
		Groups groups = Groups.findOrCreate(ur);
		return Group.findOrCreate(groups,
				new HashMap<String, String>(){{
					put("name",name);
				}});	
	}

	/**
	 * Add a tag <member/> in <group/> section. It makes all non-existings intermediate nodes.
	 */	
	@SuppressWarnings("serial")
	public Member addMember(final String name,final String group,Boolean force_unique) throws Exception{
		if(force_unique)
			delAllMember(name);
		Group grp=addGroup(group);		
		return Member.findOrCreate(grp,new HashMap<String, String>(){{
			put("username",name);
		}});				
	}
	
	/**
	 * Shortcut to create a user and a group with user as member
	 */	
	public void addUserGroup(final String name,final String passwd,String group) throws Exception{
		addUser(name, passwd);
		addMember(name, group,false);
	}
	
	/**
	 * Remove a tag <user name='..'/> from <users/> and every member tag
	 */
	@SuppressWarnings("serial")
	public void delUser(final String name) throws Exception{
		UserRegistry ur = UserRegistry.findOrCreate(doc);
		Users users = Users.findOrCreate(ur);
		users.deleteChildren(User.TAG,new HashMap<String,String>(){{
			put("name",name);
		}});
		delAllMember(name);
	}

	/**
	 * Remove a tag <group name='..'/> from <groups/> 
	 */
	@SuppressWarnings("serial")
	public void delGroup(final String name) throws Exception{
		UserRegistry ur = UserRegistry.findOrCreate(doc);
		Groups groups = Groups.findOrCreate(ur);
		groups.deleteChildren(Group.TAG,new HashMap<String,String>(){{
			put("name",name);
		}});
	}

	/**
	 * Remove a tag <member username='..'/> from <group/> 
	 */	
	@SuppressWarnings("serial")
	public void delMember(final String name,String group) throws Exception{
		Group grp=addGroup(group);
		grp.deleteChildren(Member.TAG, new HashMap<String, String>(){{
			put("username",name);
		}});
	}
	
	/**
	 * Remove a tag <member username='..'/> from every <group/> 
	 */	
	@SuppressWarnings("serial")
	public void delAllMember(final String name) throws Exception{
		UserRegistry ur = UserRegistry.findOrCreate(doc);
		Groups groups = Groups.findOrCreate(ur);		
		for (Group group:groups.getElementsByTagName(Group.TAG,Group.class))
			group.deleteChildren(Member.TAG, new HashMap<String, String>(){{
				put("username",name);
			}});
	}
}
