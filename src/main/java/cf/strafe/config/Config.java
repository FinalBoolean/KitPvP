package cf.strafe.config;

import cf.strafe.KitPvP;
import org.bukkit.configuration.file.FileConfiguration;

public enum Config {

    INSTANCE;

    public static String KILL_MESSAGE, RECEIVED_KIT;
    public static int broadcasts;
    public static long delay;
    public static long CHAT_CD;

    public void loadConfig() {
        FileConfiguration config = KitPvP.INSTANCE.getPlugin().getConfig();
        KILL_MESSAGE = config.getString("killMessage");
        RECEIVED_KIT = config.getString("receiveKit");
        delay = config.getLong("broadcast-delay");
        CHAT_CD = config.getLong("chat-delay");
        for (String key : config.getConfigurationSection("announcements").getKeys(false)) {
            broadcasts++;
        }
    }
}
