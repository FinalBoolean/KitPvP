package cf.strafe.event.map;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class FFAMap {

    private String mapName;
    private Location spawnLocation;
    private Location fightLocation;


    public FFAMap(String mapName, Location spawnLocation, Location fightLocation) {
        this.mapName = mapName;
        this.spawnLocation = spawnLocation;
        this.fightLocation = fightLocation;
    }
}
