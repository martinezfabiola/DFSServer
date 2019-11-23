import java.rmi.*;
 
public interface ServicesInterface extends Remote{

	public String getName() throws RemoteException;

	public void send(String msg) throws RemoteException;

	public void setClient(ServicesInterface c)throws RemoteException;

	public ServicesInterface getClient() throws RemoteException;
}