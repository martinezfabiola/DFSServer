import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.IOException;

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
            server.multicast("fabiola mercedes martinez perez");
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        }       
    }
}