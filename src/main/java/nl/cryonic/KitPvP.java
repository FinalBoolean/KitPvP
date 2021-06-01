package nl.cryonic;

import lombok.Getter;
import nl.cryonic.command.KitCommand;
import nl.cryonic.data.DataManager;
import nl.cryonic.gui.KitGui;
import nl.cryonic.kit.KitManager;

@Getter
public enum KitPvP {
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
        handleBukkit();
        this.dataManager = new DataManager();
        this.kitManager = new KitManager();
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable(Main plugin) {

    }

    public void handleBukkit() {
        plugin.getPluginLoader().createRegisteredListeners(new KitGui(), plugin);
        plugin.getCommand("kit").setExecutor(new KitCommand());
    }

}
