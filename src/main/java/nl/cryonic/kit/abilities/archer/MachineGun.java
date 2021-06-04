package nl.cryonic.kit.abilities.archer;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import nl.cryonic.kit.Ability;
import nl.cryonic.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MachineGun extends Ability {
    public MachineGun(ItemStack itemName) {
        super(itemName);
    }


    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR) {

            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
            if(isHoldingItem(event)) {
                if (data.getAbilityCD().hasCooldown(30)) {
                    data.getPlayer().getWorld().playSound(data.getPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    data.getPlayer().sendMessage(ChatColor.RED + "Machine gun initialized");
                }
            }
            if(data.getPlayer().getInventory().getItemInMainHand().getType() == Material.BOW) {
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
            }
        }
    }
}
