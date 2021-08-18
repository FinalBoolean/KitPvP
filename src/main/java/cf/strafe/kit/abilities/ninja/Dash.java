package cf.strafe.kit.abilities.ninja;

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


public class Dash extends Ability {
    public Dash(ItemStack itemName) {
        super(itemName);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (isHoldingItem(event.getPlayer())) {
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
                if (data.getAbilityCD().hasCooldown(10)) {
                    data.getPlayer().sendMessage(ChatColor.GREEN + "(!) You used Dash!");
                    PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY, 60, 1);
                    PotionEffect strength = new PotionEffect(PotionEffectType.SPEED, 60, 3);
                    data.getPlayer().playSound(data.getPlayer().getLocation(), Sound.ENTITY_HORSE_GALLOP, 1, 1);
                    data.getPlayer().addPotionEffect(invisibility);
                    data.getPlayer().addPotionEffect(strength);
                } else {
                    data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }
}
