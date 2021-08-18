package cf.strafe.kit.abilities.knight;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BattleStrength extends Ability {
    public BattleStrength(ItemStack item) {
        super(item);
    }

    @EventHandler
    public void rightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (isHoldingItem(event)) {
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
                if (data.getAbilityCD().hasCooldown(20)) {
                    data.getPlayer().getWorld().playSound(data.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    PotionEffect strength = new PotionEffect(PotionEffectType.SPEED, 200, 1);
                    data.getPlayer().addPotionEffect(strength);
                    data.getPlayer().sendMessage(ChatColor.GREEN + "(!) You used battle strength!");
                } else {
                    data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }
}
