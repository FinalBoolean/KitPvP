package nl.cryonic.listener;

import nl.cryonic.KitPvP;
import nl.cryonic.config.Config;
import nl.cryonic.data.PlayerData;
import nl.cryonic.gui.KitGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if(killer != null) {
            PlayerData killedUser = KitPvP.INSTANCE.getDataManager().getPlayer(killed.getUniqueId());
            PlayerData killerUser = KitPvP.INSTANCE.getDataManager().getPlayer(killer.getUniqueId());

            killedUser.setKillStreak(0);
            //Kill rewards
            killerUser.setKillStreak(killerUser.getKillStreak() + 1);
            killerUser.setKills(killerUser.getKills() + 1);
            double xpAdd = (int) Math.abs(Math.ceil(Math.random() * killerUser.getLevel() - Math.ceil(Math.random() * killerUser.getLevel()))) + 1;
            killerUser.setXp(killerUser.getXp() + xpAdd);
            killer.sendMessage(ChatColor.GREEN + "" + xpAdd + "+");
            killerUser.getPlayer().setHealth(20);
            /*
            Level up
             */
            if(killerUser.getXp() >= killerUser.getNeededXp()) {
                killerUser.setXp(0);
                killerUser.setLevel(killerUser.getLevel() + 1);
                killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            } else {
                killer.playSound(killer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1,1);
            }

            event.setDeathMessage(Config.KILL_MESSAGE.replace("%killer%", killer.getName()).replace("%victim%", killed.getName()));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        player.getInventory().clear();
        org.bukkit.inventory.ItemStack air2 = new org.bukkit.inventory.ItemStack(Material.AIR, 1);
        player.getInventory().setHelmet(air2);
        player.getInventory().setChestplate(air2);
        player.getInventory().setLeggings(air2);
        player.getInventory().setBoots(air2);
        KitGui kitGui = new KitGui(data);
        kitGui.openGui(player);
        data.giveKit(data.getLastKit());
    }

}
