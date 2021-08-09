package cf.strafe.data;

import lombok.Getter;
import cf.strafe.KitPvP;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class DataManager {

    private final Map<UUID, PlayerData> users = new HashMap<>();

    public void uninject(Player player) {
        KitPvP.INSTANCE.getExecutor().execute(() -> {
            getPlayer(player.getUniqueId()).saveData();
            users.remove(player.getUniqueId());
        });


    }

    public void inject(Player player) {
        KitPvP.INSTANCE.getExecutor().execute(() -> {
            PlayerData duelUser = new PlayerData(player);
            users.put(player.getUniqueId(), duelUser);
            duelUser.loadData();
            duelUser.giveKit(KitPvP.INSTANCE.getKitManager().getKits().get(0));
            KitPvP.INSTANCE.getScoreboardManager().create(player);
        });
        for(PlayerData data : users.values()) {
            if(data.isVanished() && !player.hasPermission("kitpvp.staff")) {
                player.hidePlayer(data.getPlayer());
            }
        }
    }

    public PlayerData getPlayer(UUID id) {
        return users.get(id);
    }
}