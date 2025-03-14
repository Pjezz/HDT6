import java.util.*;

public class MapFactory {
    public Map<String, Pokemon> createMap(int type) {
        switch (type) {
            case 1:
                return new HashMap<>();
            case 2:
                return new TreeMap<>();
            case 3:
                return new LinkedHashMap<>();
            default:
                throw new IllegalArgumentException("Opcion No valida");
        }
    }
}
