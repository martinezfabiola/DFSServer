import java.rmi.*;

public interface ServicesInterface extends Remote{

	public String getName() throws RemoteException;

	public void send(String msg) throws RemoteException;

	public void setClient(ServicesInterface c)throws RemoteException;

	public ServicesInterface getClient() throws RemoteException;

	public String getMsg() throws RemoteException;

	public void setMsg(String msg)throws RemoteException;

	public String getOption() throws RemoteException;

	public void setOption(String userOption)throws RemoteException;

	public void puData(String nombre, String objeto, Boolean replicado) throws RemoteException;

	public String getData(String nombre) throws RemoteException;
}