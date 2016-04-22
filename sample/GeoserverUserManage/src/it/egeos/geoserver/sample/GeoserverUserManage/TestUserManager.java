package it.egeos.geoserver.sample.GeoserverUserManage;

import java.util.ArrayList;
import java.util.HashMap;

import it.egeos.geoserver.utils.UsersManager;

public class TestUserManager {
	static String filename="/tmp/users.xml";
	
	public static void main(String[] args) {
		System.out.println("Starting...");
		try {
			UsersManager um=new UsersManager(filename);
			
			System.out.println("Adding user user01 with nothing else..");
			um.addUser("user01", "passwd");
			
			System.out.println("Adding group group01..");
			um.addGroup("group01");
			
			System.out.println("Adding user user02 with group group02..");
			um.addUserGroup("user02", "passwd", "group02");
			
			System.out.println("Adding user user03 adding, step by step, to group group03..");
			um.addUser("user03", "passwd");
			um.addMember("user03", "group03");			
						
			System.out.println("Adding user user04 with nothing else to test remove..");
			um.addUser("user04", "passwd");
			System.out.println("Removing user user04..");
			um.delUser("user04");
			
			System.out.println("Adding group group04 to test remove..");
			um.addGroup("group04");
			System.out.println("Removing group group04..");
			um.delGroup("group04");
			
			System.out.println("Adding user user05 with group group05 to test remove of a member..");
			um.addUserGroup("user05", "passwd", "group05");
			System.out.println("Removing member user05 from group group05..");
			um.delMember("user05","group05");
			
			um.save();
			
			System.out.println("Listing users");
			for (String l:um.getUsers())
				System.out.println("\t"+l);
			System.out.println("end.");
			
			System.out.println("Listing groups");
			for (String g:um.getGroups())
				System.out.println("\t"+g);
			System.out.println("end.");
			
			System.out.println("Listing members");
			HashMap<String, ArrayList<String>> members = um.getMembers();
			for (String g:members.keySet()){
				System.out.println("\t"+g);
				for(String m:members.get(g))
					System.out.println("\t\t"+m);
			}
			System.out.println("end.");
			
			
		} 
		catch (Exception e1) {
			e1.printStackTrace();
		}
		System.err.println("end.");	
	}

}
