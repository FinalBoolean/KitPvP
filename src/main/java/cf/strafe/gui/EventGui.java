package cf.strafe.gui;

import cf.strafe.data.PlayerData;
import cf.strafe.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class EventGui implements Listener {

    public PlayerData data;
    public Inventory inv;

    public EventGui(PlayerData player) {
        this.data = player;
        inv = Bukkit.createInventory(null, 9, "Event Selector");
        loadEvents();
    }

    public void openGui(HumanEntity entity) {
        entity.openInventory(inv);
    }

    public void loadEvents() {
        inv.addItem(createGuiItem(Material.LEGACY_LEASH, "&eSumo Event &f(Click)"));
        inv.addItem(createGuiItem(Material.DIAMOND_SWORD, "&eFFA Event &f(Click)"));
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(ColorUtil.translate(name));

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}