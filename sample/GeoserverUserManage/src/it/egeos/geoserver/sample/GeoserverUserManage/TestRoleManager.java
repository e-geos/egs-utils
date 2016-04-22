package it.egeos.geoserver.sample.GeoserverUserManage;

import java.util.ArrayList;
import java.util.HashMap;

import it.egeos.geoserver.utils.RolesManager;

public class TestRoleManager {
	static String filename="/tmp/roles.xml";
	
	public static void main(String[] args) {
		System.out.println("Starting...");
		try {
			RolesManager rm=new RolesManager(filename);
			
			System.out.println("Add new role RoleAlone2");
			rm.addRole("RoleAlone2");
			
			System.out.println("Add new userRoles UserRoleAlone2");
			rm.addUserRoles("UserRoleAlone2");
			
			System.out.println("Add new roleRef between UserRoleNew and UnexistingRole");
			rm.addUserRoleRef("UserRoleNew", "UnexistingRole");
			
			System.out.println("Adding role WorkingRole2 linked to WorkingUser1 to test remove..");
			rm.addRoleToUser("WorkingRole2", "WorkingUser1");						
			rm.delRole("WorkingRole2");
						
			rm.save();
			
			System.out.println("Listing roles");
			for (String l:rm.getRoles())
				System.out.println("\t"+l);
			System.out.println("end.");
			
			System.out.println("Listing userRoles");
			for (String l:rm.getUserRoles())
				System.out.println("\t"+l);
			System.out.println("end.");
			
			System.out.println("Listing groupRoles");
			for (String l:rm.getGroupRoles())
				System.out.println("\t"+l);
			System.out.println("end.");

			System.out.println("Listing users ref");
			HashMap<String, ArrayList<String>> refs = rm.getUserRoleRef();
			for (String u:refs.keySet()){
				System.out.println("\t"+u);
				for(String r:refs.get(u))
					System.out.println("\t\t"+r);
			}
			System.out.println("end.");
			
			System.out.println("Listing groups ref");
			HashMap<String, ArrayList<String>> refs2 = rm.getGroupRoleRef();
			for (String u:refs2.keySet()){
				System.out.println("\t"+u);
				for(String r:refs2.get(u))
					System.out.println("\t\t"+r);
			}
			System.out.println("end.");

		} 
		catch (Exception e1) {
			e1.printStackTrace();
		}
		System.err.println("end.");	
	}

}
