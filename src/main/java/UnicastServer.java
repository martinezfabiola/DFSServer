import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UnicastServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String greeting = in.readLine();
                System.out.println(greeting);
                out.println("ack");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
 
    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Introduzca el puerto del servidor: ");
        String portStr = s.next().trim();
        Integer port = Integer.parseInt(portStr);
        UnicastServer server = new UnicastServer();
        server.start(port);
    }
}