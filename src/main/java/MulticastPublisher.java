import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.IOException;
import java.util.*;

// NOTA: el archivo debe correrse con el siguiente comando
//                  java -Djava.net.preferIPv4Stack=true MulticastPublisher

public class MulticastPublisher {
    private MulticastSocket socket;
    private InetAddress group;
    private byte[] buf;
 
    public void multicast(String multicastMessage) throws IOException {
        socket = new MulticastSocket();
        group = InetAddress.getByName("239.5.5.5");
        buf = multicastMessage.getBytes();
 
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 1234);
        socket.send(packet);
        socket.close();
    }

    public static void main(String[] args) {
        try{
            MulticastPublisher server = new MulticastPublisher();
            Scanner s = new Scanner(System.in);
            String msg;
            while (true) {
                msg = s.nextLine().trim();
                server.multicast(msg);
            }
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        }       
    }
}