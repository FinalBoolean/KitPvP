package cf.strafe.event.map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

@RequiredArgsConstructor
@Setter
@Getter
public class SumoMap {
    private String mapName;
    private Location spawnLocation;
    private Location fightLocation1, fightLocation2;
    private double fallLevel;

    public SumoMap(String mapName, Location spawnLocation, Location fightLocation1, Location fightLocation2, int fallLevel) {
        this.mapName = mapName;
        this.spawnLocation = spawnLocation;
        this.fightLocation1 = fightLocation1;
        this.fightLocation2 = fightLocation2;
        this.fallLevel = fallLevel;
    }

}
