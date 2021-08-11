package cf.strafe.event;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.events.Sumo;
import org.apache.commons.lang.StringUtils;
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

    public void createEvent(Event.Type e, PlayerData host) {
        this.host = host;
        if (e == Event.Type.SUMO) {
            event = new Sumo();
        }
    }

    public void deleteEvent() {
        if (event != null) {
            if (event.getPlayers() != null) {
                for (PlayerData player : event.getPlayers()) {
                    event.removePlayer(player);
                }
            }
        }
        event = null;
        eventType = null;
        host = null;
    }

    private void initialize() {
        BukkitTask task = new BukkitRunnable() {
            public void run() {
                if (event != null) {
                    event.update();
                }
            }
        }.runTaskTimer(KitPvP.INSTANCE.getPlugin(), 0, 20);
    }

}
