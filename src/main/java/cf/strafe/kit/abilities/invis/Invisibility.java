package cf.strafe.kit.abilities.invis;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Ability;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Invisibility extends Ability {
    public Invisibility(ItemStack itemName) {
        super(itemName);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (isHoldingItem(event)){
                PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
                if (data.getAbilityCD().hasCooldown(20)){
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,20 * 15,1));
                }else{
                    event.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
                }
            }
        }
    }
}
