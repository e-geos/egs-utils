package it.egeos.geoserver.sample.GeoserverUserManage;

import it.egeos.geoserver.utils.RolesManager;
import it.egeos.geoserver.utils.UsersManager;

public class TestUserRoleManager {
	static String users="/tmp/users.xml";
	static String roles="/tmp/roles.xml";
	
	public static void main(String[] args){
		System.out.println("Starting...");
		try {
			UsersManager um=new UsersManager(users);
			RolesManager rm=new RolesManager(roles);
			
			um.addUser("admin", "geoserver");
			um.addUserGroup("user001", "passwd", "group001");
						
			rm.addRoleToUser("ADMIN", "admin");
			rm.addRole("GROUP_ADMIN");
			rm.addRole("pippo");
			rm.addRoleToUser("role002", "user001");
			rm.addRoleToGroup("role001","group001");
			
			um.save();
			rm.save();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("end.");	
	}

}
