import java.util.Scanner;

public class ServerWorker {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter Your name and press Enter:");
        String name = s.nextLine().trim();

        InfoTable tabla = new InfoTable(name);

        TableReceiver tableReceiver = new TableReceiver(tabla);
        Thread threadActualizacion = new Thread(tableReceiver);
        threadActualizacion.start();

        TablePublisher tablePublisher = new TablePublisher(tabla);
        Thread threadPublicador = new Thread(tablePublisher);
        threadPublicador.start();
    }
}