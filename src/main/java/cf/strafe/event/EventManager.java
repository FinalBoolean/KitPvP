package cf.strafe.event;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.events.FreeForAll;
import cf.strafe.event.events.Skywars;
import cf.strafe.event.events.Sumo;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.SkywarsMap;
import cf.strafe.event.map.SumoMap;
import cf.strafe.utils.ColorUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
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

    public void createSkyWarsEvent(Event.Type e, PlayerData host, SkywarsMap skywarsMap) {
        this.host = host;
        if (e == Event.Type.SKYWARS) {
            event = new Skywars(skywarsMap, host);
        }
    }

    public void deleteEvent(String reason) {
        if(event != null) {
            event.state = Event.State.END;
            Bukkit.broadcastMessage(ChatColor.RED + "The event was cancelled. Reason: " + reason);
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "Error please fix..");
        }
    }

    public void deleteEvent() {
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
