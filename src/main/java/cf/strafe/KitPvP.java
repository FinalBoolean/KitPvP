package cf.strafe;

import cf.strafe.command.*;
import cf.strafe.config.Config;
import cf.strafe.data.DataManager;
import cf.strafe.event.EventManager;
import cf.strafe.event.map.MapManager;
import cf.strafe.event.map.SumoMap;
import cf.strafe.gui.KitGui;
import cf.strafe.kit.KitManager;
import cf.strafe.listener.DataListener;
import cf.strafe.listener.PlayerListener;
import cf.strafe.managers.BroadcastManager;
import cf.strafe.managers.ScoreboardManager;
import cf.strafe.utils.LocationUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
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
    private EventManager eventManager;
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
        this.eventManager = new EventManager();
        scoreboard(plugin);
        handleBukkit(plugin);
        loadArenas();
        Bukkit.getOnlinePlayers().forEach(player -> dataManager.inject(player));
        teamManager = Bukkit.getScoreboardManager().getMainScoreboard();
        registerHealthBar();
        registerNameTag();



    }
    public void loadArenas() {
        File file = new File(plugin.getDataFolder(), "SumoArenas.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            for (String key : config.getKeys(false)) {
                String arenaName = config.getString(key + ".name");
                Location spawnLocation = config.getLocation(key + ".spawnLocation");
                Location fight1 = config.getLocation(key + ".fight1");
                Location fight2 = config.getLocation(key + ".fight2");
                int fallLevel = config.getInt(key + ".fallLevel");
                MapManager.getSumoMaps().add(new SumoMap(arenaName, spawnLocation, fight1, fight2, fallLevel));
                System.out.println("Loading Arena " + arenaName);
            }
        }

    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveSumoArenas() {
        File file = new File(plugin.getDataFolder(), "SumoArenas.yml");

        if (!file.exists()) {


            try {
                file.createNewFile();
            } catch (IOException ignored) {

            }

        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        for (SumoMap arena : MapManager.getSumoMaps()) {
            yml.set(arena.getMapName() + ".spawnLocation", arena.getSpawnLocation());
            yml.set(arena.getMapName() + ".fight1", arena.getFightLocation1());
            yml.set(arena.getMapName() + ".fight2", arena.getFightLocation2());
            yml.set(arena.getMapName() + ".fallLevel", arena.getFallLevel());
            yml.set(arena.getMapName() + ".name", arena.getMapName());
        }

        try {
            yml.save(file);
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public void registerNameTag() {
        if (teamManager.getTeam("vanish") != null) {
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
        saveSumoArenas();
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
        plugin.getCommand("sumo").setExecutor(new SumoCommand());
        plugin.getCommand("event").setExecutor(new EventCommand());
        plugin.getCommand("ffa").setExecutor(new FFACommand());
    }


}
