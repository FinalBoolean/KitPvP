package cf.strafe.kit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
@Getter
public abstract class Ability implements Listener {
    private final ItemStack itemName;
    public boolean isHoldingItem(PlayerEvent event) {
        return event.getPlayer().getInventory().getItemInMainHand().equals(itemName);
    }

    public boolean isHoldingItem(Player player) {
        return player.getInventory().getItemInMainHand().equals(itemName);
    }

}