package nl.cryonic.gui;

import nl.cryonic.KitPvP;
import nl.cryonic.config.Config;
import nl.cryonic.data.PlayerData;
import nl.cryonic.kit.Kit;
import nl.cryonic.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.swing.plaf.ColorUIResource;
import java.util.Arrays;

public class KitGui implements Listener {

    public PlayerData data;
    public Inventory inv;

    public KitGui() {

    }

    public KitGui(PlayerData player) {
        this.data = player;
        int m = KitPvP.INSTANCE.getKitManager().getKits().size() / 9 + ((KitPvP.INSTANCE.getKitManager().getKits().size() % 9 == 0) ? 0 : 1);
        inv = Bukkit.createInventory(null, 9 * m, "Kit Selector");
        loadKits();
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();

        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = 167;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }

        return new String(b);
    }

    public static int ceil(int a) {
        return (int) StrictMath.ceil(a); // default impl. delegates to StrictMath
    }

    public void openGui(HumanEntity entity) {
        entity.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("Kit Selector")) {
            for (Kit kit : KitPvP.INSTANCE.getKitManager().getKits()) {
                if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(kit.getName())) {
                    if(kit.getLevel() <= data.getLevel()) {
                        data.giveKit(kit);
                        data.getPlayer().sendMessage(ColorUtil.translate(Config.RECEIVED_KIT));
                        data.getPlayer().closeInventory();
                    } else {
                        data.getPlayer().sendMessage(ChatColor.RED + "You need to be level " + kit.getLevel() + " to use that kit!");
                    }
                }
            }
            e.setCancelled(true);
        }

    }

    public void loadKits() {
        for (Kit kit : KitPvP.INSTANCE.getKitManager().getKits()) {

            inv.addItem(createGuiItem(kit.getIcon(), kit.getName(), kit.getLore()[0], ColorUtil.translate("&cRequires level " + kit.getLevel())));
        }
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}