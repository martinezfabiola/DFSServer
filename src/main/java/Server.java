import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
 
public class Server {
	public String showMenu(){
		String line1 = "1. Send";
		String line2 = "2. Receive";
		String line3 = "3. Put Data";
		String line4 = "4. Get Data";
		String line5 = "5. Remove Data";
		String line6 = "6. Wait For";

		return line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line5 + "\n" + line6;
	}

	public String option1(){
		String line1 = "Please, put the object name and host as follow: ";
		String line2 = "Ex: procesos.json host";
		return line1 + "\n" + line2;
	}

	public String option2(){
		return "...";
	}

	public String option3(){
		String line1 = "Please, put the file name as follow: ";
		String line2 = "Ex: name procesos.json normal";
		return line1 + "\n" + line2;
	}

	public String option4(){
		String line1 = "Please, put the file name as follow: ";
		String line2 = "Ex: name";
		return line1 + "\n" + line2;
	}

	public String option5(){
		String line1 = "Please, put the file name as follow: ";
		String line2 = "Ex: name";
		return line1 + "\n" + line2;
	}

	public String option6(){
		String line1 = "Please, put the host name as follow: ";
		String line2 = "Ex: host";
		return line1 + "\n" + line2;
	}
public static void main (String[] argv) {
    try {
	    	//System.setSecurityManager(new RMISecurityManager());
	    	Scanner s = new Scanner(System.in);
	    	System.out.println("Enter Your name and press Enter:");
	    	String name = s.nextLine().trim();
 
	    	Services services = new Services(name);
 
	    	Naming.rebind("rmi://localhost/ABC", services);
 
			System.out.println("Server is ready:");
			Server serverInfo = new Server();
			String option = "";
 
	    	while (true){
				// String msg = s.nextLine().trim();
				System.out.println("entre 1");
	    		if (services.getClient()!= null){
					System.out.println("entre 2");
					ServicesInterface client = services.getClient();
					client.send(serverInfo.showMenu());
	    			// msg = "["+client.getName()+"] "+msg;
					// client.send(msg);
					while (true){
						option = services.getMsg();
						if (option != "")
							break;
					}
					System.out.println("aquiiii"+ option);

					if (option != ""){
						switch (option) {
							case "1": client.send(serverInfo.option1());
									break;
							case "2": client.send(serverInfo.option2());
									break;
							case "3": client.send(serverInfo.option3());
									break;
							case "4": client.send(serverInfo.option4());
									break;
							case "5": client.send(serverInfo.option5());
									break;
							case "6": client.send(serverInfo.option6());
									break;
							default: client.send("Invalid option");
						}
					}
					option = "";	
					services.setMsg("");
	    		}	
	    	}
    	} catch (Exception e) {
    		System.out.println("Server failed: " + e);
    	}
	}
}