import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerWorker {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Your name and press Enter:");
        String ip = s.nextLine().trim();

        InfoTable tabla = new InfoTable(ip);

        TableReceiver tableReceiver = new TableReceiver(tabla);
        Thread threadActualizacion = new Thread(tableReceiver);
        threadActualizacion.start();

        TablePublisher tablePublisher = new TablePublisher(tabla);
        Thread threadPublicador = new Thread(tablePublisher);
        threadPublicador.start();

        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(5050);
                Socket clientSocket = serverSocket.accept();
                DataInputStream dIn = new DataInputStream(clientSocket.getInputStream());
                String message = dIn.readUTF();

                String parts[] = message.split(" ", 2);

                String option = parts[0];
                message = parts[1];

                switch (option) {
                    case "1": {
                        System.out.println("El mensaje fue recibido \n" + message);
                    }
                    break;
                    case "3": {
                        String name = message.substring(0, message.indexOf(' '));
                        String object = message.substring(message.indexOf(' ') + 1);

                        tabla.saveObject(name, object);
                    }
                    break;
                    case "4": {
                        String data = tabla.returnData(message);
                        DataOutputStream dOut = new DataOutputStream(clientSocket.getOutputStream());
                        dOut.writeUTF(data);
                        dOut.close();
                    }
                    break;
                    case "5": {
                        tabla.deleteData(message);
                    }
                    break;
                    default: {
                        ;
                    }
                }

                dIn.close();
                serverSocket.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}