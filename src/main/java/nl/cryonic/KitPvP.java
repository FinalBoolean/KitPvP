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
import nl.cryonic.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

@Getter
public enum KitPvP {
    INSTANCE;

    private KitManager kitManager;
    private DataManager dataManager;
    private ScoreboardManager scoreboardManager;
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
        this.dataManager = new DataManager();
        this.kitManager = new KitManager();
        this.scoreboardManager = new ScoreboardManager();
        scoreboard(plugin);
        handleBukkit(plugin);

    }

    public void scoreboard(Main plugin) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            dataManager.inject(p);
        }
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable(Main plugin) {
        Bukkit.getOnlinePlayers().forEach(player -> dataManager.uninject(player));
    }

    public void handleBukkit(Main plugin) {
        plugin.getServer().getPluginManager().registerEvents(new KitGui(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DataListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
        plugin.getCommand("kit").setExecutor(new KitCommand());
    }


}
