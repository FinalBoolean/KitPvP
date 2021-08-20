package cf.strafe.utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@UtilityClass
public class WorldGuardUtils {
    /*
    Please don't change your api again world guard :)
     */
    public boolean isPvp(Player player) {
        final WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager()
                .getPlugin("WorldGuard");
        final RegionManager regionManager = wg.getRegionManager(player.getWorld());
        final ApplicableRegionSet set = regionManager.getApplicableRegions(player.getPlayer().getLocation());
        return set.allows(DefaultFlag.PVP);
    }

}
