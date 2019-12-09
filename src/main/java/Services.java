import java.rmi.*;
import java.rmi.server.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class Services extends UnicastRemoteObject implements ServicesInterface {
 
	public volatile String name;
	public ServicesInterface client = null;
	public String message;
	public String option;
 
	public Services(String n) throws RemoteException { 
		this.name = n;   
		this.message = "";
		this.option = "";
	}
	public String getName() throws RemoteException {
		return this.name;
	}
 
	public void setClient(ServicesInterface c){
		client = c;
	}

	public synchronized void setMsg(String msg){
		message = msg;
	}

	public synchronized void setOption(String userOption){
		option = userOption;
	}
 
	public ServicesInterface getClient(){
		return client;
	}
 
	public synchronized void send(String s) throws RemoteException{
		System.out.println(s);
		System.out.println("");
	}

	public synchronized String getMsg() throws RemoteException{
		return message;
	}
	
	public synchronized String getOption() throws RemoteException{
		return option;
	}

	public String createData(String name) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(name))
        {
            //Leer archivo JSON
            Object obj = jsonParser.parse(reader);

            JSONArray listaProcesos = (JSONArray) obj;
            //System.out.println(listaProcesos);

            return listaProcesos.toJSONString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "Malo";
    }
}