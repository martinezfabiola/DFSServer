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
            
            InfoTable tabla = new InfoTable(name);
            TableReceiver tableReceiver = new TableReceiver(tabla);
            Thread threadActualizacion = new Thread(tableReceiver);
            threadActualizacion.start();
 
            System.out.println("Server is ready:");
            String option = "";
            String[] args;
            String msg;
 
            while (true){				
                option = services.getOption();
                msg = services.getMsg();

                switch (option) {
                    case "1": {
                        if (msg != ""){
                            args = msg.split("\\s+");
                            String fileName = args[0];
                            String host = args[1];
                            services.setMsg("");
                        }
                    }
                        break;
                    case "2": {
            
                    }
                        break;
                    case "3": {
                        if (msg != ""){
                            args = msg.split("\\s+");
                            String fileName = args[0];
                            String fileNameFormat = args[1];
                            String typeStorage = args[2];
                            services.setMsg("");
                        }
                    }
                        break;
                    case "4": {
                        if (msg != ""){
                            args = msg.split("\\s+");
                            String fileName = args[0];
                            services.setMsg("");
                        }
                    }
                        break;
                    case "5": {
                        if (msg != ""){
                            args = msg.split("\\s+");
                            String fileName = args[0];
                            services.setMsg("");
                        }
                    }
                        break;
                    case "6": {
                        if (msg != ""){
                            args = msg.split("\\s+");
                            String host = args[1];
                            services.setMsg("");
                        }
                    }
                        break;
                    default: System.out.println("nada");
                }
            }
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        }
    }
}