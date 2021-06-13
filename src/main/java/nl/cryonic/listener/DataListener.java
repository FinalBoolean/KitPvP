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
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if(data != null) {
            data.loadData();
            KitPvP.INSTANCE.createScoreboard(event.getPlayer());
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if(data != null) {
            data.saveData();
        }
        KitPvP.INSTANCE.getDataManager().uninject(event.getPlayer());
    }
}
