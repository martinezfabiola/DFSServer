import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class UnicastClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        } 
    }
 
    public String sendMessage(String msg) {
        out.println(msg);
        try {
            String resp = in.readLine();
            return resp;
        } catch (Exception e) {
            return "Server failed: " + e;

        }
    }
 
    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) { 
            System.out.println("Server failed: " + e);
        }
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

    public void putData(String name, String data) {
        sendMessage(data);
    }

    public static void main(String[] args) {
        UnicastClient client = new UnicastClient();
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("Introduzca el puerto al que quiere enviar un mensaje: ");
            String portStr = s.next().trim();
            Integer port = Integer.parseInt(portStr);
            client.startConnection("127.0.0.1", port);
            //String response = client.sendMessage("hello server");
            client.putData("procesos", client.createData("procesos.json"));
            System.out.println("Llega aqui");
            client.stopConnection();
        }
    }
}