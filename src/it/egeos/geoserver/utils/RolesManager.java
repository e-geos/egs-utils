package it.egeos.geoserver.utils;

import it.egeos.geoserver.utils.Abstracts.XMLManager;
import it.egeos.geoserver.utils.tags.GroupList;
import it.egeos.geoserver.utils.tags.GroupRoles;
import it.egeos.geoserver.utils.tags.Role;
import it.egeos.geoserver.utils.tags.RoleList;
import it.egeos.geoserver.utils.tags.RoleRef;
import it.egeos.geoserver.utils.tags.RoleRegistry;
import it.egeos.geoserver.utils.tags.UserList;
import it.egeos.geoserver.utils.tags.UserRoles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Federico C. Guizzardi - cippinofg <at> gmail.com
 * 
 * This class implements a wrapper for Geoserver roles.xml file.
 * 
 */

public class RolesManager extends XMLManager{
	/*
	 * Constructor: fn is a filename, usually roles.xml
	 */
	public RolesManager(String fn) throws IOException {
		super(fn);
	}
	
	/**
	 * Returns the list of all roles 
	 */
	@SuppressWarnings("serial")
	public ArrayList<String> getRoles() throws Exception{
		return new ArrayList<String>(){{
			RoleRegistry rr = RoleRegistry.findOrCreate(doc);
			RoleList rl = RoleList.findOrCreate(rr);
			for(Role r:Role.list(rl))
				add(r.getAttribute("id"));
		}};
	}

	/**
	 * Returns the list of all userRoles
	 */
	@SuppressWarnings("serial")
	public ArrayList<String> getUserRoles() throws Exception{
		return new ArrayList<String>(){{
			RoleRegistry rr = RoleRegistry.findOrCreate(doc);
			UserList ul = UserList.findOrCreate(rr);
			for(UserRoles ur:UserRoles.list(ul))
				add(ur.getAttribute("username"));
		}};		
	}

	/**
	 * Returns the list of all groupRoles
	 */
	@SuppressWarnings("serial")
	public ArrayList<String> getGroupRoles() throws Exception{
		return new ArrayList<String>(){{
			RoleRegistry rr = RoleRegistry.findOrCreate(doc);
			GroupList gl= GroupList.findOrCreate(rr);
			for(final GroupRoles gr:GroupRoles.list(gl))
				add(gr.getAttribute("groupname"));
		}};		
	}
	
	/**
	 * Returns the list of all role ref divided by user 
	 */
	@SuppressWarnings("serial")
	public HashMap<String,ArrayList<String>> getUserRoleRef() throws Exception{
		return new HashMap<String,ArrayList<String>>(){{
			RoleRegistry rr = RoleRegistry.findOrCreate(doc);
			UserList ul = UserList.findOrCreate(rr);
			for(final UserRoles ur:UserRoles.list(ul))
				put(ur.getAttribute("username"),new ArrayList<String>(){{
					for(RoleRef rr:RoleRef.list(ur))
						add(rr.getAttribute("roleID"));
				}});						
		}};
	}

	/**
	 * Returns the list of all role ref divided by group 
	 */
	@SuppressWarnings("serial")
	public HashMap<String,ArrayList<String>> getGroupRoleRef() throws Exception{
		return new HashMap<String,ArrayList<String>>(){{
			RoleRegistry rr = RoleRegistry.findOrCreate(doc);
			GroupList gl= GroupList.findOrCreate(rr);
			for(final GroupRoles gr:GroupRoles.list(gl))
				put(gr.getAttribute("groupname"),new ArrayList<String>(){{
					for(RoleRef rr:RoleRef.list(gr))
						add(rr.getAttribute("roleID"));
				}});						
		}};
	}
	
	/**
	 * Add a tag <role/> in <roleList/> section. It makes all non-existings intermediate nodes.
	 */
	public Role addRole(final String name) throws Exception{
		return addRole(name,null);
	}

	/**
	 * Add a tag <role/> in <roleList/> section. It makes all non-existings intermediate nodes.
	 */
	@SuppressWarnings("serial")
	public Role addRole(final String name,final String parent) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		RoleList rl = RoleList.findOrCreate(rr);
		return Role.findOrCreate(rl, new HashMap<String, String>(){{
			put("id",name);
			if (parent !=null && !parent.trim().isEmpty())
				put("parentID",parent);
		}});		
	}
	
	/**
	 * Add a tag <userRoles/> in <userList/> section. It makes all non-existings intermediate nodes.
	 */	
	@SuppressWarnings("serial")
	public UserRoles addUserRoles(final String name) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		UserList ul = UserList.findOrCreate(rr);
		return UserRoles.findOrCreate(ul,new HashMap<String, String>(){{
			put("username",name);
		}});
	}

	/**
	 * Add a tag <groupRoles/> in <groupList/> section. It makes all non-existings intermediate nodes.
	 */	
	@SuppressWarnings("serial")
	public GroupRoles addGroupRoles(final String name) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		GroupList gl = GroupList.findOrCreate(rr);
		return GroupRoles.findOrCreate(gl,new HashMap<String, String>(){{
			put("groupname",name);
		}});
	}
	
	/**
	 * Add a tag <RoleRef/> in <userRoles/> section. It makes all non-existings intermediate nodes.
	 */	
	@SuppressWarnings("serial")
	public RoleRef addUserRoleRef(final String name,final String role) throws Exception{		
		UserRoles ur=addUserRoles(name);
		return RoleRef.findOrCreate(ur, new HashMap<String, String>(){{
			put("roleID",role);
		}});
	}

	/**
	 * Add a tag <RoleRef/> in <groupRoles/> section. It makes all non-existings intermediate nodes.
	 */	
	@SuppressWarnings("serial")
	public RoleRef addGroupRoleRef(final String name,final String role) throws Exception{		
		GroupRoles gr=addGroupRoles(name);
		return RoleRef.findOrCreate(gr, new HashMap<String, String>(){{
			put("roleID",role);
		}});
	}
	
	/**
	 * Shortcut to create a role and a userRoles with role as a roleRef
	 */	
	public void addRoleToUser(String role,String user) throws Exception{
		addRole(role);
		addUserRoleRef(user, role);
	}

	/**
	 * Shortcut to create a role and a groupRoles with role as a roleRef
	 */	
	public void addRoleToGroup(String role,String group) throws Exception{
		addRole(role);
		addGroupRoleRef(group, role);
	}
		
	/**
	 * Remove a tag <role id='..'/> from <roleList/> and every roleRef tag
	 */
	@SuppressWarnings("serial")
	public void delRole(final String name) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		RoleList rl = RoleList.findOrCreate(rr);		
		rl.deleteChildren(Role.TAG,new HashMap<String,String>(){{
			put("id",name);
		}});
		delAllRoleRef(name);
	}
	
	/**
	 * Remove a tag <userRoles username='..'/> from <userList/> 
	 */
	@SuppressWarnings("serial")
	public void delUserRole(final String name) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		UserList ul= UserList.findOrCreate(rr);
		ul.deleteChildren(UserRoles.TAG,new HashMap<String,String>(){{
			put("username",name);
		}});
	}

	/**
	 * Remove a tag <groupRoles username='..'/> from <groupList/> 
	 */
	@SuppressWarnings("serial")
	public void delGroupRole(final String name) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		GroupList gl= GroupList.findOrCreate(rr);
		gl.deleteChildren(GroupRoles.TAG,new HashMap<String,String>(){{
			put("groupname",name);
		}});
	}

	/**
	 * Remove a tag <roleRef roleID='..'/> from <userRoles/> 
	 */
	@SuppressWarnings("serial")
	public void delRoleRefFromUser(final String role,String user) throws Exception{
		UserRoles ur=addUserRoles(user);
		ur.deleteChildren(RoleRef.TAG, new HashMap<String, String>(){{
			put("roleID",role);
		}});
	}
	
	/**
	 * Remove a tag <roleRef roleID='..'/> from <userRoles/> 
	 */
	@SuppressWarnings("serial")
	public void delRoleRefFromGroup(final String role,String group) throws Exception{
		GroupRoles gr=addGroupRoles(group);
		gr.deleteChildren(RoleRef.TAG, new HashMap<String, String>(){{
			put("roleID",role);
		}});
	}

	/**
	 * Remove a tag <roleRef roleID='..'/> from every <userRoles/>  and <groupRoles/>
	 */	
	@SuppressWarnings("serial")
	public void delAllRoleRef(final String role) throws Exception{
		RoleRegistry rr = RoleRegistry.findOrCreate(doc);
		UserList ul= UserList.findOrCreate(rr);		
		for(UserRoles ur:ul.getElementsByTagName(UserRoles.TAG, UserRoles.class))
			ur.deleteChildren(RoleRef.TAG, new HashMap<String, String>(){{
				put("roleID",role);
			}});
		
		GroupList gl= GroupList.findOrCreate(rr);		
		for(GroupRoles gr:gl.getElementsByTagName(GroupRoles.TAG, GroupRoles.class))
			gr.deleteChildren(RoleRef.TAG, new HashMap<String, String>(){{
				put("roleID",role);
			}});
	}
}
