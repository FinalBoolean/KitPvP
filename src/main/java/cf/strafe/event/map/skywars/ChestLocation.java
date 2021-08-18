package cf.strafe.event.map.skywars;


import com.sun.tools.javac.jvm.Items;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
