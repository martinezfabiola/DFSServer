import java.rmi.*;
import java.rmi.server.*;
 
public class Services extends UnicastRemoteObject implements ServicesInterface {
 
	public String name;
	public ServicesInterface client = null;
 
	public Services(String n) throws RemoteException { 
		this.name = n;   
	}
	public String getName() throws RemoteException {
		return this.name;
	}
 
	public void setClient(ServicesInterface c){
		client = c;
	}
 
	public ServicesInterface getClient(){
		return client;
	}
 
	public void send(String s) throws RemoteException{
		System.out.println(s);
	}	
}