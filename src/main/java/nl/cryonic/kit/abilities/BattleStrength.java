package nl.cryonic.kit.abilities;

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
    private final Cooldown cooldown = new Cooldown();

    @EventHandler
    public void rightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(isHoldingItem(event)) {
            if(cooldown.hasCooldown(1000)) {
                PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1);
                PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1);
                player.addPotionEffect(resistance);
                player.addPotionEffect(strength);
            } else {
                player.sendMessage(ChatColor.RED + "Cooldown: " + cooldown.getSeconds());
            }
        }
    }
}
