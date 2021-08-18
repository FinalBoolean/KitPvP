package cf.strafe.event;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.events.FreeForAll;
import cf.strafe.event.events.Sumo;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.SumoMap;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class EventManager {

    private Event event;
    private Event.Type eventType;
    private PlayerData host;

    public EventManager() {
        initialize();
    }

    public void createSumoEvent(Event.Type e, PlayerData host, SumoMap sumoMap) {
        this.host = host;
        if (e == Event.Type.SUMO) {
            event = new Sumo(sumoMap, host);
        }
    }

    public boolean noEvent(PlayerData player) {
        if (event != null) {
            return !event.getPlayers().contains(player) && !event.getSpectators().contains(player);
        } else {
            return true;
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
                for (PlayerData players : event.getSpectators()) {
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
                for (PlayerData players : event.getSpectators()) {
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
