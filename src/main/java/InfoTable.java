import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class InfoTable {
    Map<String, ServidorInfo> tabla;
    ServidorInfo servidorInfo;

    public InfoTable(String nombre) {
        this.servidorInfo = new ServidorInfo(nombre);
        this.tabla = new Hashtable<>();
    }

    public synchronized void setEntry(ServidorInfo infoIn) {
        tabla.put(infoIn.nombre, infoIn);
    }

    public synchronized String hasObject(String object) {
        for (String nombre : this.tabla.keySet()) {
            ServidorInfo server = this.tabla.get(nombre);
            if (server.objetosAlmacenados.contains(object))
                return server.nombre;
        }

        return "The object was not found";
    }

    public synchronized Boolean getLibre() {
        return this.servidorInfo.libre;
    }

    public synchronized void setFree() {
        this.servidorInfo.libre = true;
    }

    public synchronized void setOccupied() {
        this.servidorInfo.libre = false;
    }

    public synchronized String leastObjects() {
        int tam = 100000;
        String nombre = "";
        for (String key : tabla.keySet()) {
            if (tabla.get(key).objetosAlmacenados.size() < tam) {
                tam = tabla.get(key).objetosAlmacenados.size();
                nombre = tabla.get(key).nombre;
            }
        }
        return nombre;
    }

    public synchronized void saveObject(String name, String object) {
        this.servidorInfo.objetosAlmacenados.add(name);
        this.createFile(name, object);
    }

    public void createFile(String name, String object){
        try {
            String fileName = name + ".json";
            File newFile = new File(fileName);
            if (newFile.createNewFile()) {
              // System.out.println("File created: " + myObj.getName());
              FileWriter myWriter = new FileWriter(fileName);
              myWriter.write(object);
              myWriter.close();
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}