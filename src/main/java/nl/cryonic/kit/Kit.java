package nl.cryonic.kit;

import lombok.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
@Getter
public class Kit {
    private final String name;
    private final String[] lore;
    private final ItemStack[] itemContents;
    private final ItemStack[] armorContents;
    private final Material icon;
    private final int level;

    public Kit(String name, Material icon, int level, ItemStack[] itemContents, ItemStack[] armorContents, String... lore) {
        this.name = name;
        this.itemContents = itemContents;
        this.icon = icon;
        this.armorContents = armorContents;
        this.lore = lore;
        this.level = level;
    }


}
