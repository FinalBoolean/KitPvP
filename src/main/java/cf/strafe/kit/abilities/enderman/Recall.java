package cf.strafe.kit.abilities.enderman;

import cf.strafe.data.PlayerData;
import cf.strafe.kit.Ability;
import cf.strafe.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class Recall extends Ability {
    public Recall(ItemStack itemName) {
        super(itemName);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
            if (isHoldingItem(data.getPlayer())) {
                if (data.getAbilityCD().hasCooldown(40)) {
                    data.countDown = 5;
                    data.setRecallLocation(data.getPlayer().getLocation());
                    data.recallTask = Bukkit.getScheduler().runTaskTimer(KitPvP.INSTANCE.getPlugin(), () -> {
                        data.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Recalling in " + data.getCountDown());
                        data.getPlayer().playSound(data.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
                        if (data.countDown == 0) {
                            data.getPlayer().teleport(data.getRecallLocation());
                            data.setRecallLocation(null);
                            Bukkit.getScheduler().cancelTask(data.recallTask);
                        }
                        data.countDown--;
                    }, 0L, 20L).getTaskId();
                } else {
                    data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if (data.getRecallLocation() != null) {
            Bukkit.getScheduler().cancelTask(data.getRecallTask());
        }
    }
}
