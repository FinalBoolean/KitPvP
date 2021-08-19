package cf.strafe.event.map.skywars;


import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ChestLocation {
    private ItemStack[] inventory;
    private Location location;


    public ChestLocation(Location location, List<ItemStack> inventory) {
        this.location = location;
        this.inventory = new ItemStack[inventory.size()];
        inventory.toArray(this.inventory);
    }
}
