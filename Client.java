import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Client {
    public static void main (String[] argv) {
        try {
            //System.setSecurityManager(new RMISecurityManager());
            Scanner s = new Scanner(System.in);
            System.out.println("Enter Your name and press Enter:");
			String name = s.nextLine().trim();		    		    	
			ServicesInterface client = new Services(name);

			ServicesInterface server = (ServicesInterface)Naming.lookup("rmi://localhost/ABC");
			String msg="["+client.getName()+"] got connected";
			server.send(msg);
			System.out.println("Client is ready:");
			server.setClient(client);

			while(true){
				msg=s.nextLine().trim();
				msg="["+client.getName()+"] "+msg;		    		
				server.send(msg);
			}
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}
}