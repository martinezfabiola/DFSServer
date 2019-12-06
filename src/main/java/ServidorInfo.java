import java.util.ArrayList;
import java.util.List;

public class ServidorInfo {
    String nombre;
    Boolean libre;
    List<String> objetosAlmacenados;

    ServidorInfo(String nombre, Boolean libre, List<String> objetosAlmacenados) {
        this.nombre = nombre;
        this.libre = libre;
        this.objetosAlmacenados = objetosAlmacenados;
    }

    ServidorInfo(String nombre) {
        this.nombre = nombre;
        this.libre = true;
        this.objetosAlmacenados = new ArrayList<>();
    }
}