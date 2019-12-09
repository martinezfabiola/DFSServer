import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class TablePublisher implements Runnable {
    InfoTable tabla;
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];

    TablePublisher(InfoTable tabla) {
        this.tabla = tabla;
    }

    public void run() {
        try{
            socket = new MulticastSocket();
            InetAddress group = InetAddress.getByName("239.5.5.5");
            while (true) {
                try {
                    // TODO: Probar esta seccion
                    JSONObject toSend = new JSONObject();
                    toSend.put("nombre", tabla.servidorInfo.nombre);
                    toSend.put("libre", tabla.getLibre());
                    JSONArray objetos = new JSONArray();
                    
                    List<String> objetosAlmacenadosSalida = tabla.servidorInfo.objetosAlmacenados;
                    for (int i = 0; i < objetosAlmacenadosSalida.size(); i++) {
                        objetos.add(objetosAlmacenadosSalida.get(i));
                    }
                    toSend.put("objetosAlmacenados", objetos);

                    buf = toSend.toJSONString().getBytes();

                    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 1234);
                    socket.send(packet);
                } catch (Exception e) {
                    System.out.println(e);
                }
                
                
                if ("end".equals("NeverEnds")) {
                    break;
                }

                Thread.sleep(1000);
                for (String nombre : tabla.tabla.keySet()) {
                    System.out.println(nombre);
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        } 
    }
}