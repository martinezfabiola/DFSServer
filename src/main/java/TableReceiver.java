import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TableReceiver implements Runnable {
    InfoTable tabla;
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];

    TableReceiver(InfoTable tabla) {
        this.tabla = tabla;
    }

    public void run() {
        try{
            socket = new MulticastSocket(1234);
            InetAddress group = InetAddress.getByName("239.5.5.5");
            socket.joinGroup(group);
            JSONParser parser = new JSONParser();
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                try {
                    // TODO: Probar esta seccion
                    JSONObject receivedJson = (JSONObject) parser.parse(received);
                    String nombreIn = (String) receivedJson.get("nombre");
                    Boolean libreIn = (Boolean) receivedJson.get("libre");
                    List<String> objetosAlmacenadosIn = new ArrayList<>();
                    JSONArray objetos = (JSONArray) receivedJson.get("objetosAlmacenados");
                    for (int i=0; i < objetos.size(); i++) {
                        String obj = (String) objetos.get(i);
                        objetosAlmacenadosIn.add(obj);
                    }
                    ServidorInfo receivedInfo = new ServidorInfo(nombreIn, libreIn, objetosAlmacenadosIn);
                    this.tabla.setEntry(receivedInfo);
                    for ( String clave : this.tabla.tabla.keySet()) {
                        System.out.println(clave);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                
                
                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        } 
    }
}