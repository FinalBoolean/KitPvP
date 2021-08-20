package cf.strafe.kit.abilities.archer;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Ability;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.atomic.AtomicInteger;

public class MachineGun extends Ability {
    public MachineGun(ItemStack item) {
        super(item);
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
            if (isHoldingItem(event)) {
                if (data.getAbilityCD().hasCooldown(15)) {
                    data.getPlayer().getWorld().playSound(data.getPlayer().getLocation(), Sound.VILLAGER_NO, 1, 1);
                    data.getPlayer().sendMessage(ChatColor.GREEN + "(!) You used Machine Gun!");
                    AtomicInteger count = new AtomicInteger();
                    final int[] id = {0};
                    id[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KitPvP.INSTANCE.getPlugin(), () -> {
                        count.getAndIncrement();
                        if (count.get() < 4) {
                            Player player = data.getPlayer();
                            event.setCancelled(true);
                            Arrow arrow = player.launchProjectile(Arrow.class);
                            arrow.setShooter(player);
                            arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                            arrow.setCustomName("crazy arrow bro");
                            player.getWorld().playSound(player.getLocation(), Sound.SHOOT_ARROW, 1, 1);
                            arrow.setCritical(true);
                            arrow.setCustomNameVisible(false);
                        } else {
                            Bukkit.getScheduler().cancelTask(id[0]);
                        }
                    }, 0, 5);
                } else {
                    data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        event.getEntity().remove();
    }

}