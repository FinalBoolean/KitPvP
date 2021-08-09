package cf.strafe.config;

import cf.strafe.KitPvP;
import org.bukkit.configuration.file.FileConfiguration;

public enum Config {

    INSTANCE;

    public static String KILL_MESSAGE, RECEIVED_KIT;

    public void loadConfig() {
        FileConfiguration config = KitPvP.INSTANCE.getPlugin().getConfig();
        KILL_MESSAGE = config.getString("killMessage");
        RECEIVED_KIT = config.getString("receiveKit");
    }
}
