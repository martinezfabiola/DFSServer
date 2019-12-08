import java.rmi.*;

public interface ServicesInterface extends Remote{

	public String getName() throws RemoteException;

	public void send(String msg) throws RemoteException;

	public void setClient(ServicesInterface c)throws RemoteException;

	public ServicesInterface getClient() throws RemoteException;

	public String createData(String name) throws RemoteException;

	public String getMsg() throws RemoteException;

	public void setMsg(String msg)throws RemoteException;
}