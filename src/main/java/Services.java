import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Services extends UnicastRemoteObject implements ServicesInterface {

    public volatile String name;
    public ServicesInterface client = null;
    public String message;
    public String option;
    public InfoTable tabla;

    public Services(String n) throws RemoteException {
        this.name = n;
        this.message = "";
        this.option = "";
        this.tabla = new InfoTable(n);
    }

    public Services(String n, InfoTable tabla) throws RemoteException {
        this.name = n;
        this.message = "";
        this.option = "";
        this.tabla = tabla;
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public void setClient(ServicesInterface c) {
        client = c;
    }

    public synchronized void setMsg(String msg) {
        message = msg;
    }

    public synchronized void setOption(String userOption) {
        option = userOption;
    }

    public ServicesInterface getClient() {
        return client;
    }

    public synchronized void send(String s) throws RemoteException {
        System.out.println(s);
        System.out.println("");
    }

    public synchronized String getMsg() throws RemoteException {
        return message;
    }

    public synchronized String getOption() throws RemoteException {
        return option;
    }

    public void sendtoHost(String message, String ip) {
        try {
            Socket soc = new Socket(ip, 5050);
            DataOutputStream dOut = new DataOutputStream(soc.getOutputStream());

            dOut.writeUTF("1 " + message);
            dOut.flush();

            dOut.close();
            soc.close();
        } catch (Exception e) {
            System.out.println("Host not found " + e);
        }
    }

    public void puData(String nombre, String objeto, Boolean replicado) {
        if (replicado) {
            // Hacer multicast para guardar objeto
            MulticastSocket socket;
            try {
                socket = new MulticastSocket();
                InetAddress group = InetAddress.getByName("239.5.5.6");
                byte[] buf = (nombre + " " + objeto).getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 1234);
                socket.send(packet);
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    
        } else {
            // Hacer unicast a algun server para guardar objeto
            String ip = tabla.leastObjects();
            try {
                Socket soc = new Socket(ip, 5050);
                DataOutputStream dOut = new DataOutputStream(soc.getOutputStream());

                dOut.writeUTF("3 " + nombre + " " + objeto);
                dOut.flush();

                dOut.close();
                soc.close();
            } catch (Exception e) {
                System.out.println("Host not found " + e);
            }
        }
    }

    public String getData(String nombre) {
        String ip = tabla.hasObject(nombre);
        if (!ip.equals("")) {
            try {
                Socket soc = new Socket(ip, 5050);
                DataOutputStream dOut = new DataOutputStream(soc.getOutputStream());
                DataInputStream dIn = new DataInputStream(soc.getInputStream());

                dOut.writeUTF("4 " + nombre);
                dOut.flush();

                String objeto = dIn.readUTF();

                dIn.close();
                dOut.close();
                soc.close();

                return objeto;
            } catch (Exception e) {
                System.out.println("Host not found " + e);
            }

        }
        return "";
    }

    public void deleteData(String name) {
        String ip = tabla.hasObject(name);
        if (!ip.equals("")) {
            try {
                Socket soc = new Socket(ip, 5050);
                DataOutputStream dOut = new DataOutputStream(soc.getOutputStream());

                dOut.writeUTF("5 " + name);
                dOut.flush();

                dOut.close();
                soc.close();
            } catch (Exception e) {
                System.out.println("Host not found " + e);
            }
        }
    }
}