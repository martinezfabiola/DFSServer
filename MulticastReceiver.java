import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.DatagramPacket;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[256];
 
    public void run() {
        try{
            socket = new MulticastSocket(1234);
            InetAddress group = InetAddress.getByName("239.5.5.5");
            socket.joinGroup(group);
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
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

    public static void main(String[] args) {
        try{
            MulticastReceiver server = new MulticastReceiver();
           server.run();
        } catch (Exception e) {
            System.out.println("Server failed: " + e);
        }       
    }
}