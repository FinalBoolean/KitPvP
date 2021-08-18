package cf.strafe.listener;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        KitPvP.INSTANCE.getDataManager().inject(event.getPlayer());

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
            KitPvP.INSTANCE.getEventManager().getEvent().removePlayer(data);
        }
        //KitPvP.INSTANCE.getScoreboardManager().remove(event.getPlayer());
        if (data.isVanished()) {
            KitPvP.INSTANCE.getTeamManager().getTeam("vanish").removePlayer(data.getPlayer());
        }
        KitPvP.INSTANCE.getDataManager().uninject(event.getPlayer());

    }

}
