package cf.strafe.event.map;

import cf.strafe.event.map.skywars.ChestLocation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

import java.util.List;

@Getter
@Setter
public class SkywarsMap {

    private String mapName;
    private Location spectatorLocation;
    private List<ChestLocation> chestLocations;
    private List<Location> spawnLocations;

    public SkywarsMap(String mapName, Location spectatorLocation, List<ChestLocation> chestLocations, List<Location> spawnLocations) {
        this.mapName = mapName;
        this.spectatorLocation = spectatorLocation;
        this.chestLocations = chestLocations;
        this.spawnLocations = spawnLocations;
    }



}
