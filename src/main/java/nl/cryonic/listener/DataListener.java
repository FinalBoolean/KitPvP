package nl.cryonic.listener;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        KitPvP.INSTANCE.getDataManager().inject(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        KitPvP.INSTANCE.getDataManager().uninject(event.getPlayer());

    }
}
