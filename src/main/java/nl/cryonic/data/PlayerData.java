package nl.cryonic.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nl.cryonic.kit.Kit;
import nl.cryonic.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class PlayerData {
    @Setter(AccessLevel.NONE)
    private UUID uuid;
    @Setter(AccessLevel.NONE)
    private Player player;
    private Kit kit, lastKit;

    private int kills, level;
    private double xp;

    private Cooldown abilityCD = new Cooldown();

    public PlayerData(Player p) {
        player = p;
        uuid = p.getUniqueId();
    }

}
