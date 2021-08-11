package cf.strafe.kit.abilities.switcher;

import cf.strafe.KitPvP;
import cf.strafe.kit.Kit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Switcher implements Listener {

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent e) {
        Entity victim = (Player)e.getEntity();
        if (e.getEntity() instanceof Player) {
            if (e.getDamager() instanceof Snowball) {
                Snowball snowball = (Snowball)e.getDamager();
                if (snowball.getShooter() instanceof Player) {
                    Player shooter = (Player) snowball.getShooter();
                    if (KitPvP.INSTANCE.getDataManager().getPlayer(shooter.getUniqueId()).getKit().getName().equalsIgnoreCase("Switcher")) {
                        Location shooterLoc = shooter.getLocation();
                        Location victimLoc = victim.getLocation();

                        shooter.teleport(victimLoc);
                        victim.teleport(shooterLoc);
                    }
                }
            }
        }
    }

}
