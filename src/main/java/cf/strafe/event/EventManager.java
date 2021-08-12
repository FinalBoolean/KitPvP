package cf.strafe.event;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.events.FreeForAll;
import cf.strafe.event.events.Sumo;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.SumoMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class EventManager {

    public Event event;
    public Event.Type eventType;
    public int gameTime;
    public PlayerData host;

    public EventManager() {
        initialize();
    }

    public void createSumoEvent(Event.Type e, PlayerData host, SumoMap sumoMap) {
        this.host = host;
        if (e == Event.Type.SUMO) {
            event = new Sumo(sumoMap, host);
        }
    }


    public void createFFAEvent(Event.Type e, PlayerData host, FFAMap ffaMap) {
        this.host = host;
        if (e == Event.Type.FFA) {
            event = new FreeForAll(ffaMap, host);
        }
    }

    public void deleteEvent(String reason) {
        if (event != null) {
            if (event.getPlayers() != null) {
                for (PlayerData player : event.getPlayers()) {
                    event.removePlayer(player);
                }
                for(PlayerData players : event.getSpectators()) {
                    event.removePlayer(players);
                }
            }
        }
        Bukkit.broadcastMessage(ChatColor.RED + "The event was cancelled. Reason: " + reason);
        event = null;
        eventType = null;
        host = null;
    }

    public void deleteEvent() {
        if (event != null) {
            if (event.getPlayers() != null) {
                for (PlayerData player : event.getPlayers()) {
                    event.removePlayer(player);
                }
                for(PlayerData players : event.getSpectators()) {
                    event.removePlayer(players);
                }
            }
        }
        event = null;
        eventType = null;
        host = null;
    }

    private void initialize() {
        new BukkitRunnable() {
            public void run() {
                if (event != null) {
                    event.update();
                }
            }
        }.runTaskTimer(KitPvP.INSTANCE.getPlugin(), 0, 20);
    }

}
