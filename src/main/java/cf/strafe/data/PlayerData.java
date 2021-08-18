package cf.strafe.data;

import cf.strafe.KitPvP;
import cf.strafe.kit.Kit;
import cf.strafe.utils.Cooldown;
import cf.strafe.utils.ItemUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    public int recallTask, countDown;
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Setter(AccessLevel.NONE)
    private Player player;
    private Kit lastKit;
    private int kills, deaths, level, killStreak, maxKillStreak, credits;
    private double xp, neededXp;
    private boolean vanished, staffchat, spawn;
    private BukkitTask task;
    private Location recallLocation;
    private Cooldown abilityCD = new Cooldown();
    private Cooldown chatCD = new Cooldown();

    public PlayerData(Player p) {
        player = p;
        uuid = p.getUniqueId();
    }

    public void vanish(PlayerData data) {
        Player player = data.getPlayer();

        KitPvP.INSTANCE.getTeamManager().getTeam("vanish").addPlayer(player);
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (!players.hasPermission("kitpvp.staff")) {
                players.hidePlayer(player);
            }
        }
    }

    public void spawnPlayer() {
        spawn = true;
        player.getInventory().clear();
        player.getInventory().setItem(0, ItemUtil.createItemWithName(Material.BOOK, "&eSelect Kit &f(Right Click)"));
        player.getInventory().setItem(1, ItemUtil.createItemWithName(Material.GLOWSTONE_DUST, "&ePrevious Kit &f(Right Click)"));
        player.getInventory().setItem(8, ItemUtil.createItemWithName(Material.ENDER_EYE, "&eEvents &f(Right Click)"));
    }


    public void unvanish(PlayerData data) {
        Player player = data.getPlayer();
        KitPvP.INSTANCE.getTeamManager().getTeam("vanish").removePlayer(player);
        for (Player players : Bukkit.getOnlinePlayers()) {
            players.showPlayer(player);
        }

    }

    public void giveKit(Kit kit) {
        player.getInventory().setContents(kit.getItemContents());
        player.getInventory().setArmorContents(kit.getArmorContents());
        player.updateInventory();
        player.playSound(player.getLocation(), Sound.ENTITY_HORSE_SADDLE, 1, 1);

    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadData() {
        final File dir = new File(KitPvP.INSTANCE.getPlugin().getDataFolder() + File.separator + "Data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File player = new File(dir, getPlayer().getUniqueId() + ".yml");
        if (!player.exists()) {
            try {

                player.createNewFile();
                loadData();

            } catch (final Exception ignored) {
            }
        } else {
            final YamlConfiguration load = YamlConfiguration.loadConfiguration(player);
            PlayerData pd = this;

            pd.setXp(load.getInt("xp"));
            pd.setKills(load.getInt("kills"));
            pd.setDeaths(load.getInt("deaths"));
            pd.setKillStreak(load.getInt("killStreak"));
            pd.setLevel(load.getInt("level"));
            pd.setNeededXp(load.getInt("neededXp"));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveData() {
        File dir = new File(KitPvP.INSTANCE.getPlugin().getDataFolder() + File.separator + "Data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File player = new File(dir, getPlayer().getUniqueId() + ".yml");
        if (!player.exists()) {
            try {
                player.createNewFile();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        final YamlConfiguration plda = YamlConfiguration.loadConfiguration(player);
        PlayerData pd = this;
        plda.set("kills", pd.getKills());
        plda.set("deaths", pd.getDeaths());
        plda.set("killStreak", pd.getKillStreak());
        plda.set("level", pd.getLevel());
        plda.set("xp", pd.getXp());
        plda.set("neededXp", pd.neededXp);
        plda.set("xp", pd.getXp());

        try {
            plda.save(player);
        } catch (Exception ignored) {

        }
    }


}
