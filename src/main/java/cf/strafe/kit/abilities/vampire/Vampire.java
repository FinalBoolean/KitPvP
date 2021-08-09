package cf.strafe.kit.abilities.vampire;

import cf.strafe.kit.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Vampire extends Ability {
    public Vampire(ItemStack itemName) {
        super(itemName);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player && !event.isCancelled()) {
            Player player = (Player) event.getDamager();
            if(isHoldingItem(player)) {
                PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 20, 1);
                player.addPotionEffect(regen);
            }
        }
    }
}
