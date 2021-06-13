package nl.cryonic;

import lombok.Getter;
import nl.cryonic.command.KitCommand;
import nl.cryonic.config.Config;
import nl.cryonic.data.DataManager;
import nl.cryonic.data.PlayerData;
import nl.cryonic.gui.KitGui;
import nl.cryonic.kit.KitManager;
import nl.cryonic.listener.DataListener;
import nl.cryonic.listener.PlayerListener;
import nl.cryonic.utils.ScoreHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public enum KitPvP  {
    INSTANCE;

    private KitManager kitManager;
    private DataManager dataManager;
    private Main plugin;

    /**
     * Called when the plugin is loaded
     */
    public void onLoad(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when the plugin is enabled
     */
    public void onEnable(Main plugin) {
        Config.INSTANCE.loadConfig();
        handleBukkit();
        this.dataManager = new DataManager();
        this.kitManager = new KitManager();

        scoreboard(plugin);


    }

    public void scoreboard(Main plugin) {
        new BukkitRunnable() {

            @Override
            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }

            }

        }.runTaskTimer(plugin, 20L, 20L);

        for (Player p : Bukkit.getOnlinePlayers()) {
            dataManager.inject(p);
            createScoreboard(p);
        }
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable(Main plugin) {

    }

    public void handleBukkit() {
        plugin.getPluginLoader().createRegisteredListeners(new KitGui(), plugin);
        plugin.getPluginLoader().createRegisteredListeners(new DataListener(), plugin);
        plugin.getPluginLoader().createRegisteredListeners(new PlayerListener(), plugin);
        plugin.getCommand("kit").setExecutor(new KitCommand());
    }

    public void createScoreboard(Player player) {
        ScoreHelper helper = ScoreHelper.createScore(player);
        PlayerData pd = dataManager.getPlayer(player.getUniqueId());

        helper.setTitle("&4&lWar&8&lAC");
        helper.setSlot(8, "&7&m--------------------------------");
        helper.setSlot(7, "&aPlayer&f: " + player.getName());
        helper.setSlot(6, "&aLevel&f: " + pd.getLevel());
        helper.setSlot(5, "&aProgress&f: " + pd.getXp() + "/" + pd.getNeededXp());
        helper.setSlot(4, "&aKill Streak&f: " + pd.getKillStreak());
        helper.setSlot(1, "&7&m--------------------------------");
    }

    public void updateScoreboard(Player player) {
        if (ScoreHelper.hasScore(player)) {
            ScoreHelper helper = ScoreHelper.getByPlayer(player);
            PlayerData pd = dataManager.getPlayer(player.getUniqueId());
            if (pd != null) {
                helper.setSlot(6, "&aLevel&f: " + pd.getLevel());
                helper.setSlot(5, "&aProgress&f: " + pd.getXp() + "/" + pd.getNeededXp());
                helper.setSlot(4, "&aKill Streak&f: " + pd.getKillStreak());

            }
        }
    }




}
