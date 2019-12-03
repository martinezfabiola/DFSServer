import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
 
public class Server {
public static void main (String[] argv) {
    try {
	    	//System.setSecurityManager(new RMISecurityManager());
	    	Scanner s = new Scanner(System.in);
	    	System.out.println("Enter Your name and press Enter:");
	    	String name = s.nextLine().trim();
 
	    	Services services = new Services(name);
 
	    	Naming.rebind("rmi://localhost/ABC", services);
 
	    	System.out.println("Server is ready:");
 
	    	while (true){
	    		String msg = s.nextLine().trim();
	    		if (services.getClient()!= null){
	    			ServicesInterface client = services.getClient();
	    			msg = "["+services.getName()+"] "+msg;
					client.send(msg);
					Thread.sleep(3000);
	    		}	
	    	}
 
    	} catch (Exception e) {
    		System.out.println("Server failed: " + e);
    	}
	}
}