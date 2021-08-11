package cf.strafe.kit.abilities.pyro;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FlameSword extends Ability {
    public FlameSword(ItemStack itemName) {
        super(itemName);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR) {
            if(isHoldingItem(event)) {
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
                if(data.getAbilityCD().hasCooldown(10)) {
                    if(event.getItem() != null) {
                        data.getPlayer().sendMessage(ChatColor.GREEN + "(!) You used FlameSword");
                        data.getPlayer().playSound(data.getPlayer().getLocation(), Sound.ENTITY_GHAST_SHOOT, 1, 1);
                        event.getItem().addEnchantment(Enchantment.FIRE_ASPECT, 2);
                        event.getItem().addEnchantment(Enchantment.DAMAGE_ALL, 2);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.INSTANCE.getPlugin(), () -> {
                            event.getItem().removeEnchantment(Enchantment.FIRE_ASPECT);
                            event.getItem().removeEnchantment(Enchantment.DAMAGE_ALL);
                            data.getPlayer().playSound(data.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 1);
                        }, 60);
                    }
                } else {
                    data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }
}
