package nl.cryonic.kit.abilities.knight;

import nl.cryonic.KitPvP;
import nl.cryonic.data.PlayerData;
import nl.cryonic.kit.Ability;
import nl.cryonic.utils.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BattleStrength extends Ability {
    public BattleStrength(ItemStack item) {
        super(item);
    }


    @EventHandler
    public void rightClick(PlayerInteractEvent event) {
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if(isHoldingItem(event)) {
            if(data.getAbilityCD().hasCooldown(10)) {
                PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1);
                PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1);
                data.getPlayer().addPotionEffect(resistance);
                data.getPlayer().addPotionEffect(strength);
            } else {
                data.getPlayer().sendMessage(ChatColor.RED + "Cooldown: " + data.getAbilityCD().getSeconds());
            }
        }
    }
}
