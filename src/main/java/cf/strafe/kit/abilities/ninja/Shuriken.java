package cf.strafe.kit.abilities.ninja;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Ability;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;


public class Shuriken extends Ability {
    public Shuriken(ItemStack itemName) {
        super(itemName);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (isHoldingItem2(event.getPlayer())) {
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
                data.getPlayer().playSound(data.getPlayer().getLocation(), Sound.SHOOT_ARROW, 1, 1);
                Player player = data.getPlayer();
                ItemStack itemStack = new ItemStack(event.getItem());
                itemStack.setAmount(1);
                player.getInventory().removeItem(new ItemStack(itemStack));
                event.setCancelled(true);
                Snowball arrow = player.launchProjectile(Snowball.class);
                arrow.setShooter(player);
                arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                arrow.setCustomName("crazy shuriken bro");
                player.getWorld().playSound(player.getLocation(), Sound.SHOOT_ARROW, 1, 1);
                arrow.setCustomNameVisible(false);

            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(!event.isCancelled()) {
            if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) event.getDamager();
                if (projectile.getCustomName().equalsIgnoreCase("crazy shuriken bro")) {
                    Entity e = event.getEntity();
                    if (e instanceof Player) {
                        Player entity = (Player) e;
                        PotionEffect blind = new PotionEffect(PotionEffectType.BLINDNESS, 60, 3);
                        PotionEffect poison = new PotionEffect(PotionEffectType.POISON, 60, 3);
                        PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 60, 3);
                        entity.addPotionEffect(blind);
                        entity.addPotionEffect(slowness);
                        entity.addPotionEffect(poison);

                    }
                }
            }
        }
    }
}
