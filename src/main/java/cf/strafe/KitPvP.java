package cf.strafe;

import cf.strafe.command.*;
import cf.strafe.data.DataManager;
import cf.strafe.kit.KitManager;
import cf.strafe.listener.DataListener;
import cf.strafe.managers.BroadcastManager;
import lombok.Getter;
import cf.strafe.config.Config;
import cf.strafe.gui.KitGui;
import cf.strafe.listener.PlayerListener;
import cf.strafe.managers.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public enum KitPvP {
    INSTANCE;


    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private KitManager kitManager;
    private BroadcastManager broadcastManager;
    private DataManager dataManager;
    private ScoreboardManager scoreboardManager;
    private Scoreboard teamManager;
    private Main plugin;

    /**
     * Called when the plugin is loaded
     */
    public void onLoad(Main plugin) {
        final File f = new File(plugin.getDataFolder(), "config.yml");
        if (!f.exists()) {
            plugin.saveResource("config.yml", true);

        }
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
        this.broadcastManager = new BroadcastManager();
        scoreboard(plugin);
        handleBukkit(plugin);
        Bukkit.getOnlinePlayers().forEach(player -> dataManager.inject(player));
        teamManager = Bukkit.getScoreboardManager().getMainScoreboard();
        registerHealthBar();
        registerNameTag();




    }

    public void registerNameTag() {
        if(teamManager.getTeam("vanish") != null) {
            teamManager.getTeam("vanish").unregister();
        }
        Team t = teamManager.registerNewTeam("vanish");
        t.setPrefix(ChatColor.GREEN + "[V] ");
    }

    public void registerHealthBar() {
        if(teamManager.getObjective("health") != null) {
            teamManager.getObjective("health").unregister();
        }
        Objective o = teamManager.registerNewObjective("health", "health");
        o.setDisplayName(ChatColor.RED + "❤");
        o.setDisplaySlot(DisplaySlot.BELOW_NAME);
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
        plugin.getCommand("discord").setExecutor(new DiscordCommand());
        plugin.getCommand("report").setExecutor(new ReportCommand());
        plugin.getCommand("vanish").setExecutor(new VanishCommand());
        plugin.getCommand("staffchat").setExecutor(new StaffChatCommand());
        plugin.getCommand("givelevel").setExecutor(new GiveLevelCommand());
        plugin.getCommand("stats").setExecutor(new StatsCommand());
    }


}
