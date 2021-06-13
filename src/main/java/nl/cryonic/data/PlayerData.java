package nl.cryonic.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.cryonic.KitPvP;
import nl.cryonic.kit.Kit;
import nl.cryonic.utils.Cooldown;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Setter(AccessLevel.NONE)
    private Player player;
    private Kit kit, lastKit;

    private int kills, level, killStreak;
    private double xp, neededXp;

    private Cooldown abilityCD = new Cooldown();

    public PlayerData(Player p) {
        player = p;
        uuid = p.getUniqueId();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void loadData() {
        final File dir = new File(KitPvP.INSTANCE.getPlugin().getDataFolder() + File.separator + "Data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File player = new File(dir, getPlayer().getUniqueId().toString() + ".yml");
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
        final File player = new File(dir, getPlayer().getUniqueId().toString() + ".yml");
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
