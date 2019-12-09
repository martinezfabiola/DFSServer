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
}