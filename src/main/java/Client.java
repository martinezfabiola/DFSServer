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

			ServicesInterface services = (ServicesInterface) Naming.lookup("rmi://localhost/ABC");
			String msg="["+client.getName()+"] got connected";
			services.send(msg);
			System.out.println("Client is ready:");
			services.setClient(client);

			String option;

			// String x = services.createData("procesos.json");

			while(true){
				option = s.nextLine().trim();
				services.setMsg(option);
				services.send(option);	
			}
		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}
}