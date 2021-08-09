package nl.cryonic.kit.abilities.thumper;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import nl.cryonic.kit.Ability;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KnockOut extends Ability {
    public KnockOut(ItemStack itemName) {
        super(itemName);
    }
    @EventHandler
    public void onRightClick(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && !event.isCancelled()) {
            Player entity = (Player) event.getEntity();
            Player player = (Player) event.getDamager();
            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
            if(isHoldingItem(player) && player.isSneaking()) {
                if(data.getAbilityCD().hasCooldown(20)) {
                    data.getPlayer().getWorld().playSound(data.getPlayer().getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
                    player.sendMessage(ChatColor.GREEN + "(!) You used Knock Out!");
                    entity.sendMessage(ChatColor.RED + "You have been knocked out by " + player.getName());
                    PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 60, 3);
                    PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 60, 3);
                    entity.addPotionEffect(blind);
                    entity.addPotionEffect(slowness);
                } else {
                    player.sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }
}
