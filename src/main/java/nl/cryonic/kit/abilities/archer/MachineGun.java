package nl.cryonic.kit.abilities.archer;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import nl.cryonic.kit.Ability;
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
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
            if (isHoldingItem(event)) {
                if (data.getAbilityCD().hasCooldown(30)) {
                    data.getPlayer().getWorld().playSound(data.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    data.getPlayer().sendMessage(ChatColor.GREEN + "(!) You used Machine Gun!");
                    AtomicInteger count = new AtomicInteger();
                    final int[] id  = {0};
                    id[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(KitPvP.INSTANCE.getPlugin(), () -> {
                        count.getAndIncrement();
                        if(count.get() < 10) {
                            boolean needMachine = data.getAbilityCD().getSeconds() > 25;
                            if (needMachine) {
                                Player player = data.getPlayer();
                                event.setCancelled(true);
                                Arrow arrow = player.launchProjectile(Arrow.class);
                                arrow.setShooter(player);
                                arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                                arrow.setCustomName("crazy arrow bro");
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
                                arrow.setCritical(true);
                                arrow.setCustomNameVisible(false);
                            }
                        } else {
                            Bukkit.getScheduler().cancelTask(id[0]);
                        }
                    },0, 5);
                } else {
                    data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }

    @EventHandler
    public void onArrowLand(ProjectileHitEvent event) {
        if (event.getEntity().getCustomName() != null) {
            if (event.getEntity().getCustomName().equalsIgnoreCase("crazy arrow bro")) {
                event.getEntity().remove();
            }
        }
    }

}
