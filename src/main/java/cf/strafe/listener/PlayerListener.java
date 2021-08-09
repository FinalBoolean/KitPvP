package cf.strafe.listener;

import cf.strafe.KitPvP;
import cf.strafe.config.Config;
import cf.strafe.data.PlayerData;
import cf.strafe.kit.Kit;
import cf.strafe.utils.ColorUtil;
import cf.strafe.utils.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ColorUtil.translate("&7&m-----------------------------------"));
        player.sendMessage(ColorUtil.translate("       &fWelcome to &6Strafed.US&f!"));
        player.sendMessage(ColorUtil.translate(""));
        player.sendMessage(ColorUtil.translate("&7» &6Server IP: &fstrafed.us"));
        player.sendMessage(ColorUtil.translate("&7» &6Website: &fwww.strafed.us"));
        player.sendMessage(ColorUtil.translate("&7» &6Store: &fstore.strafed.us"));
        player.sendMessage(ColorUtil.translate("&7» &6Discord: &fdiscord.strafed.us"));
        player.sendMessage(ColorUtil.translate("&7&m-----------------------------------"));

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
        if( !event.getPlayer().hasPermission("kitpvp.admin")) {
            if (!data.getChatCD().hasCooldown(Config.CHAT_CD)) {
                event.setCancelled(true);
            } else {
                player.sendMessage(ColorUtil.translate(String.format("&cThere is a &4%s &csecond chat delay!", data.getChatCD().getSeconds())));
            }
        }
        if(data.isStaffchat()) {
            event.setCancelled(true);
            Bukkit.broadcast(ColorUtil.translate( "&6[StaffChat] &7" + player.getName() + "»&e " + event.getMessage()), "kitpvp.staff");
        }
    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if(killer != null) {
            PlayerData killedUser = KitPvP.INSTANCE.getDataManager().getPlayer(killed.getUniqueId());
            PlayerData killerUser = KitPvP.INSTANCE.getDataManager().getPlayer(killer.getUniqueId());

            killedUser.setKillStreak(0);
            killedUser.setDeaths(killedUser.getDeaths() + 1);
            //Kill rewards
            killerUser.setKillStreak(killerUser.getKillStreak() + 1);
            if (killerUser.getKillStreak() > killerUser.getMaxKillStreak()) {
                killerUser.setMaxKillStreak(killerUser.getMaxKillStreak());
            }
            killerUser.setKills(killerUser.getKills() + 1);
            double xpAdd = (int) Math.abs(Math.ceil(Math.random() * killerUser.getLevel() - Math.ceil(Math.random() * killerUser.getLevel()))) + 1;
            killerUser.setXp(killerUser.getXp() + xpAdd);
            killer.sendMessage(ChatColor.GREEN + "" + xpAdd + "+");
            killerUser.getPlayer().getInventory().addItem(ItemUtil.createItem(Material.GOLDEN_APPLE));

            /*
            Level up
             */
            PotionEffect regen = PotionEffectType.REGENERATION.createEffect(100, 0);
            killerUser.getPlayer().addPotionEffect(regen);
            if (killerUser.getXp() >= killerUser.getNeededXp()) {
                killerUser.setXp(0);
                killerUser.setLevel(killerUser.getLevel() + 1);
                killer.playSound(killer.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                killerUser.setNeededXp((int) (2 * killerUser.getLevel() + Math.ceil(Math.random() * killerUser.getLevel())));
            } else {
                killer.playSound(killer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.INSTANCE.getPlugin(), () -> {
                killed.spigot().respawn();
            }, 5L);

            event.setDeathMessage(ColorUtil.translate(Config.KILL_MESSAGE.replace("%killer%", killer.getName()).replace("%victim%", killed.getName())));
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!event.getPlayer().hasPermission("kit.admin")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
        if (e.getView().getTitle().equalsIgnoreCase("Kit Selector")) {
            for (Kit kit : KitPvP.INSTANCE.getKitManager().getKits()) {

                if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(kit.getName())) {
                    if (data.getLevel() >= kit.getLevel()) {
                        data.giveKit(kit);
                        data.getPlayer().sendMessage(ColorUtil.translate(Config.RECEIVED_KIT.replace("%kit%", kit.getName())));
                        data.getPlayer().closeInventory();
                    } else {
                        data.getPlayer().sendMessage(ChatColor.RED + "You need to be level " + kit.getLevel() + " to use that kit!");
                    }

                }
            }
            e.setCancelled(true);
        }
    }

}
