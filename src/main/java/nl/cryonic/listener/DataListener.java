package nl.cryonic.listener;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import nl.cryonic.managers.ScoreboardManager;
import nl.cryonic.utils.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class DataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        KitPvP.INSTANCE.getDataManager().inject(event.getPlayer());
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        KitPvP.INSTANCE.getScoreboardManager().create(event.getPlayer());

        if (data != null) {
            data.loadData();
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        event.setQuitMessage("");
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        //KitPvP.INSTANCE.getScoreboardManager().remove(event.getPlayer());
        if (data != null) {
            data.saveData();
            KitPvP.INSTANCE.getDataManager().uninject(event.getPlayer());
        }
    }

}
