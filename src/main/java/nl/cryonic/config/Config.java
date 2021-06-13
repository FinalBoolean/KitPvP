package nl.cryonic.config;

import lombok.Getter;
import nl.cryonic.KitPvP;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public enum Config {

    INSTANCE;

    private String killMessage;

    public void loadConfig() {
        FileConfiguration config = KitPvP.INSTANCE.getPlugin().getConfig();
        killMessage = config.getString("killMessage");
    }
}
