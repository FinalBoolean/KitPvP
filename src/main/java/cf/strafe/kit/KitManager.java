package cf.strafe.kit;

import cf.strafe.KitPvP;
import cf.strafe.kit.abilities.archer.MachineGun;
import cf.strafe.kit.abilities.enderman.Recall;
import cf.strafe.kit.abilities.invis.Invisibility;
import cf.strafe.kit.abilities.knight.BattleStrength;
import cf.strafe.kit.abilities.ninja.Shuriken;
import cf.strafe.kit.abilities.pyro.FlameSword;
import cf.strafe.kit.abilities.switcher.Switcher;
import cf.strafe.kit.abilities.thumper.KnockOut;
import cf.strafe.kit.abilities.vampire.Vampire;
import cf.strafe.utils.ColorUtil;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitManager {

    @Getter
    private final ArrayList<Kit> kits = new ArrayList<>();

    public KitManager() {
        loadKnight();
        loadArcher();
        loadThumper();
        loadVampire();
        loadNinja();
        loadEnderman();
        loadPyro();
        loadInvis();
        loadSwitcher();
    }

    public void loadKit(Kit kit) {
        kits.add(kit);
    }

    public void loadKnight() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack SWORD = createItem(Material.IRON_SWORD, "&aKnight's Sword", "&aDecent sword for a fight.");
        items.add(SWORD);
        ItemStack ability = createItem(Material.BLAZE_POWDER, "&aBattle Strength", "&aGives THE POWER OF THE STEWIN for 5 seconds.");
        items.add(ability);
        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new BattleStrength(ability), KitPvP.INSTANCE.getPlugin());
        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.IRON_BOOTS));
        armor.add(createItem(Material.IRON_LEGGINGS));
        armor.add(createItem(Material.IRON_CHESTPLATE));
        armor.add(createItem(Material.IRON_HELMET));

        loadKit(new Kit(ColorUtil.translate("&aKnight"), Material.IRON_SWORD, 0, items, armor, ColorUtil.translate("&aSimple default kit")));

    }

    public void loadArcher() {
        List<ItemStack> items = new ArrayList<>();
        ItemStack sword = createItem(Material.STONE_SWORD, "&6Archer Sword", "&aEpik weapon for slicing foes.");
        ItemStack bow = createInfiniteBow(Material.BOW, "&6Archer Bow", "&1Bow");
        ItemStack arrow = createItem(Material.ARROW, "&aMachine Gun", "&aFire a barrage of arrows");
        items.add(sword);
        items.add(bow);
        items.add(arrow);
        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new MachineGun(arrow), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.IRON_BOOTS));
        armor.add(createItem(Material.IRON_LEGGINGS));
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE));
        armor.add(createItem(Material.IRON_HELMET));

        kits.add(new Kit(ColorUtil.translate("&aArcher"), Material.BOW, 0, items, armor, ColorUtil.translate("&aBecome a archer")));
    }

    public void loadThumper() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.GOLD_AXE, "&6Thumper Axe", "&aShift left click and hit an entity to knock out");
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        items.add(sword);
        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new KnockOut(sword), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.GOLD_BOOTS, "&aGold Boots"));

        armor.add(createItem(Material.GOLD_LEGGINGS));
        armor.add(createItem(Material.GOLD_CHESTPLATE));
        armor.add(createItem(Material.GOLD_HELMET));

        kits.add(new Kit(ColorUtil.translate("&6Thumper"), Material.GOLD_AXE, 10, items, armor, ColorUtil.translate("&6Knock people out")));

    }

    public void loadVampire() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.IRON_SWORD, "&cVampire Sword", "Has a chance to heal you when attacking");

        items.add(sword);
        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new Vampire(sword), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.IRON_BOOTS));
        armor.add(createItem(Material.IRON_LEGGINGS));
        armor.add(createItem(Material.IRON_CHESTPLATE));
        armor.add(createItem(Material.LEATHER_HELMET));

        kits.add(new Kit(ColorUtil.translate("&cVampire"), Material.GHAST_TEAR, 15, items, armor, ColorUtil.translate("&cHas a chance to heal you when attacking")));
    }

    public void loadNinja() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.IRON_SWORD, "&aNinja Sword");
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemStack ability = createItem(Material.NETHER_STAR, "&aShuriken", "&aRight click to throw shurikens!!");

        items.add(sword);
        items.add(ability);
        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new Shuriken(ability), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.IRON_BOOTS));
        armor.add(createItem(Material.IRON_LEGGINGS));
        armor.add(createItem(Material.IRON_CHESTPLATE));
        armor.add(createItem(Material.LEATHER_HELMET));

        kits.add(new Kit(ColorUtil.translate("&aNinja"), Material.NETHER_STAR, 20, items, armor, ColorUtil.translate("&bThrow ninja shurikens that knock people out")));
    }



    public void loadEnderman() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.IRON_AXE, "&aEnderman Sword");
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 2);
        ItemStack ability = createItem(Material.WATCH, "&dRecall", "&dTeleport to the last location you were");

        items.add(sword);
        items.add(ability);
        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new Recall(ability), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.CHAINMAIL_BOOTS, "&aChain Boots"));
        armor.add(createItem(Material.CHAINMAIL_LEGGINGS, "&aChain Leggings"));
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE, "&aChain Chestplate"));
        armor.add(createItem(Material.CHAINMAIL_HELMET, "&aChain Helmet"));

        kits.add(new Kit(ColorUtil.translate("&dEnderman"), Material.ENDER_PEARL, 25, items, armor, ColorUtil.translate("&dTeleport around places")));
    }

    public void loadInvis(){
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.GOLD_AXE,"&9&bInvisibility Axe","&c&bGives you power!");
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);
        ItemStack ability = createItem(Material.GHAST_TEAR,"&aActivate Invisibility","&a&bMakes you invisible!");

        items.add(sword);
        items.add(ability);

        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new Invisibility(ability),KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.CHAINMAIL_HELMET,"&aChain Helmet"));
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE,"&aChain Chestplate"));
        armor.add(createItem(Material.IRON_LEGGINGS,"&aIron Leggings"));
        armor.add(createItem(Material.GOLD_BOOTS,"&aGolden Boots"));
        kits.add(new Kit(ColorUtil.translate("&dInvisibility"), Material.GHAST_TEAR, 35, items, armor, ColorUtil.translate("&aHit people while being invisible")));
    }


    public void loadPyro() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.STONE_SWORD, "&cPyro Sword", "&4Transform your sword into a fire sword on right click!");
        items.add(sword);

        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new FlameSword(sword), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.LEATHER_BOOTS));
        armor.add(createItem(Material.CHAINMAIL_LEGGINGS));
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE));
        armor.add(createItem(Material.CHAINMAIL_HELMET));

        kits.add(new Kit(ColorUtil.translate("&cPyro"), Material.BLAZE_POWDER, 30, items, armor, ColorUtil.translate("&cHarness the power of &4FIRE!")));
    }

    public void loadSwitcher() {
        List<ItemStack> items = new ArrayList<>();

        ItemStack sword = createItem(Material.IRON_SWORD, "&eSwitcher Sword", "");
        items.add(sword);

        ItemStack ability = new ItemStack(Material.SNOW_BALL, 4);
        ItemMeta abilityMeta = ability.getItemMeta();
        abilityMeta.setDisplayName(ColorUtil.translate("&fSwitcher Ball"));
        ability.setItemMeta(abilityMeta);

        items.add(ability);

        KitPvP.INSTANCE.getPlugin().getServer().getPluginManager().registerEvents(new Switcher(), KitPvP.INSTANCE.getPlugin());

        List<ItemStack> armor = new ArrayList<>();
        armor.add(createItem(Material.DIAMOND_BOOTS, "&aChain Boots"));
        armor.add(createItem(Material.CHAINMAIL_LEGGINGS, "&aChain Leggings"));
        armor.add(createItem(Material.CHAINMAIL_CHESTPLATE, "&aChain Chestplate"));
        armor.add(createItem(Material.CHAINMAIL_HELMET, "&aChain Helmet"));

        kits.add(new Kit(ColorUtil.translate("&fSwitcher"), Material.SNOW_BALL, 35, items, armor, ColorUtil.translate("&fSwap Positions with your opponents")));
    }

    protected ItemStack createItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(ColorUtil.translate(name));

        // Set the lore of the item
        meta.spigot().setUnbreakable(true);
        meta.setLore(ColorUtil.translate(Arrays.asList(lore)));

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack createItem(final Material material) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;

        // Set the lore of the item
        meta.spigot().setUnbreakable(true);

        item.setItemMeta(meta);

        return item;
    }


    protected ItemStack createInfiniteBow(final Material material, final String name, String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(ColorUtil.translate(name));
        meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        // Set the lore of the item
        meta.spigot().setUnbreakable(true);
        meta.setLore(ColorUtil.translate(Arrays.asList(lore)));

        item.setItemMeta(meta);

        return item;
    }


}
