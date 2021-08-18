package cf.strafe.kit;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class Kit {
    private final String name;
    private final String[] lore;
    private final ItemStack[] itemContents;
    private final ItemStack[] armorContents;
    private final Material icon;
    private final int level;

    public Kit(String name, Material icon, int level, List<ItemStack> itemContents, List<ItemStack> armorContents, String... lore) {

        this.name = name;
        this.itemContents = new ItemStack[itemContents.size()];
        itemContents.toArray(this.itemContents);
        this.icon = icon;
        this.armorContents = new ItemStack[armorContents.size()];
        armorContents.toArray(this.armorContents);
        this.lore = lore;
        this.level = level;
    }


}
