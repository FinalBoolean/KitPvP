package cf.strafe.kit.abilities.archer;

import cf.strafe.kit.Ability;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

public class ArcherTag extends Ability {
    public ArcherTag(ItemStack item) {
        super(item);
    }

    @EventHandler
    public void shootBow(EntityShootBowEvent event) {
        double BowPullback = Math.floor(event.getForce());

    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        event.getEntity().remove();
        if (event.getHitEntity() != null) {

        }
    }

}
