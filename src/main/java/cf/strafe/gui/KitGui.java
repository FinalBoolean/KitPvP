package cf.strafe.gui;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Kit;
import cf.strafe.utils.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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