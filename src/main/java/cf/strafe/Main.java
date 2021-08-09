package cf.strafe;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onLoad() {
        KitPvP.INSTANCE.onLoad(this);
        super.onLoad();
    }

    @Override
    public void onEnable() {
        KitPvP.INSTANCE.onEnable(this);
        super.onEnable();
    }

    @Override
    public void onDisable() {
        KitPvP.INSTANCE.onDisable(this);
        super.onDisable();
    }
}
