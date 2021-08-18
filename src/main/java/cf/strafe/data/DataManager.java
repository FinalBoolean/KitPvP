package cf.strafe.data;

import cf.strafe.KitPvP;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class DataManager {

    private final ConcurrentHashMap<UUID, PlayerData> users = new ConcurrentHashMap<>();

    public void uninject(Player player) {
        KitPvP.INSTANCE.getExecutor().execute(() -> {
            getPlayer(player.getUniqueId()).saveData();
            users.remove(player.getUniqueId());
        });


    }

    public void inject(Player player) {
        for (PlayerData data : users.values()) {
            if (data.isVanished() && !player.hasPermission("kitpvp.staff")) {
                player.hidePlayer(data.getPlayer());
            }
            player.setPlayerListName(player.getDisplayName());
        }
        KitPvP.INSTANCE.getExecutor().execute(() -> {
            PlayerData duelUser = new PlayerData(player);
            users.put(player.getUniqueId(), duelUser);
            duelUser.loadData();
            KitPvP.INSTANCE.getScoreboardManager().create(player);
        });


    }

    public PlayerData getPlayer(UUID id) {
        return users.get(id);
    }
}