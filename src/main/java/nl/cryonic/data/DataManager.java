package nl.cryonic.data;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class DataManager {

    private final Map<UUID, PlayerData> users = new HashMap<>();

    public void uninject(Player player) {
        users.remove(player.getUniqueId());
    }

    public void inject(Player player) {
        PlayerData duelUser = new PlayerData(player);
        users.put(player.getUniqueId(), duelUser);
    }

    public PlayerData getPlayer(UUID id) {
        return users.get(id);
    }
}