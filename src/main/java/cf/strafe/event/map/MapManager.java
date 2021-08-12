package cf.strafe.event.map;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;

@UtilityClass
public class MapManager {
    private final ArrayList<SumoMap> sumoMaps = new ArrayList<>();
    private final ArrayList<FFAMap> ffaMaps = new ArrayList<>();


    public static ArrayList<SumoMap> getSumoMaps() {
        return sumoMaps;
    }
    public static ArrayList<FFAMap> getFfaMaps() {
        return ffaMaps;
    }

    public SumoMap getSumoMap(String name) {
        for(SumoMap map :sumoMaps) {
            if(map.getMapName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }
    public FFAMap getFFAMap(String name) {
        for(FFAMap map :ffaMaps) {
            if(map.getMapName().equalsIgnoreCase(name)) {
                return map;
            }
        }
        return null;
    }
}
