package nl.cryonic.data;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.WeakHashMap;

public class DataManager {

    public WeakHashMap<UUID, PlayerData> users;

    public DataManager() {
        users = new WeakHashMap<>();

    }

    public WeakHashMap<UUID, PlayerData> getPlayers() {
        return users;
    }

    public void uninject(Player player) {

        PlayerData duelUser = new PlayerData(player);

        users.remove(player.getUniqueId(), duelUser);

    }

    public void inject(Player player) {

        PlayerData duelUser = new PlayerData(player);
        users.put(player.getUniqueId(), duelUser);

    }

    public PlayerData getPlayer(UUID id) {
        for (final UUID i : users.keySet()) {
            if (i.equals(id)) {
                return users.get(i);
            }
        }
        return null;
    }

}