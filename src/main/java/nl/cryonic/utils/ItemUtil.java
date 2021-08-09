package nl.cryonic.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@UtilityClass
public class ItemUtil {

    public ItemStack createItemWithName(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(ColorUtil.translate(name));

        // Set the lore of the item
        meta.setUnbreakable(true);
        meta.setLore(ColorUtil.translate(Arrays.asList(lore)));

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createItem(final Material material) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;

        // Set the lore of the item
        meta.setUnbreakable(true);

        item.setItemMeta(meta);

        return item;
    }

}
