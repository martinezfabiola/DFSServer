import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Client {

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
		String line1 = "Please, put the name, file name and the type of storage (replicated or normal) as follow: ";
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
			ServicesInterface client = new Services(name);

			ServicesInterface services = (ServicesInterface) Naming.lookup("rmi://localhost/ABC");
			String msg="["+client.getName()+"] got connected";
			services.send(msg);
			System.out.println("Client is ready:");
			services.setClient(client);

			String option;
			String args;
			Client clientInfo = new Client();

			// String x = services.createData("procesos.json");

			while(true){
				System.out.println(clientInfo.showMenu());
				option = s.nextLine().trim();

				switch (option) {
					case "1": {
						System.out.println(clientInfo.option1());
						services.setOption(option);
						args = s.nextLine().trim();
						services.setMsg(args);
						services.send(args);
					}
						break;
					case "2": System.out.println(clientInfo.option2());
						break;
					case "3": {
						System.out.println(clientInfo.option3());
						args = s.nextLine().trim();
						services.setMsg(args);
						services.send(args);
					}
						break;
					case "4": {
						System.out.println(clientInfo.option4());
						args = s.nextLine().trim();
						services.setMsg(args);
						services.send(args);
					}
						break;
					case "5": {
						System.out.println(clientInfo.option5());
						args = s.nextLine().trim();
						services.setMsg(args);
						services.send(args);
					}
						break;
					case "6": {
						System.out.println(clientInfo.option6());
						args = s.nextLine().trim();
						services.setMsg(args);
						services.send(args);
					}
						break;
					default: System.out.println("Invalid option");
				}
			}

		} catch (Exception e) {
			System.out.println("Server failed: " + e);
		}
	}
}