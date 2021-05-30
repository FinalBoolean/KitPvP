package nl.cryonic;

import lombok.Getter;
import nl.cryonic.kit.KitManager;

@Getter
public enum KitPvP {
    INSTANCE;

    private KitManager kitManager;
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
        this.kitManager = new KitManager();
    }

    /**
     * Called when the plugin is disabled
     */
    public void onDisable(Main plugin) {

    }

}
