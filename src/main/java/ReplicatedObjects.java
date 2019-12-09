import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.json.simple.parser.JSONParser;

public class ReplicatedObjects implements Runnable {
    InfoTable tabla;
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];

    public ReplicatedObjects(InfoTable table) {
        this.tabla = table;
    }
    
    public void run() {
        try{
            socket = new MulticastSocket(5051);
            InetAddress group = InetAddress.getByName("239.5.5.6");
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                String name = received.substring(0, received.indexOf(" "));
                String object = received.substring(received.indexOf(" ") + 1);

                tabla.saveObject(name, object);
                
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