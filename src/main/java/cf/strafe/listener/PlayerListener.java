package cf.strafe.listener;

import cf.strafe.KitPvP;
import cf.strafe.config.Config;
import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;
import cf.strafe.event.map.FFAMap;
import cf.strafe.event.map.MapManager;
import cf.strafe.event.map.SumoMap;
import cf.strafe.gui.EventGui;
import cf.strafe.gui.KitGui;
import cf.strafe.kit.Kit;
import cf.strafe.utils.ColorUtil;
import cf.strafe.utils.ItemUtil;
import cf.strafe.utils.WorldGuardUtils;
import com.sk89q.worldguard.bukkit.event.block.BreakBlockEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

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

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
            if (KitPvP.INSTANCE.getEventManager().getEvent().getPlayers().contains(data)) {
                KitPvP.INSTANCE.getEventManager().getEvent().onBlockPlace(event);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
            KitPvP.INSTANCE.getEventManager().getEvent().onBlockBreak(event);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
        if (!event.getPlayer().hasPermission("kitpvp.admin")) {
            if (!data.getChatCD().hasCooldown(Config.CHAT_CD)) {
                player.sendMessage(ColorUtil.translate(String.format("&cThere is a &4%s &csecond chat delay!", data.getChatCD().getSeconds())));
                event.setCancelled(true);
            }
        }
        if (data.isStaffchat()) {
            event.setCancelled(true);
            Bukkit.broadcast(ColorUtil.translate("&6[StaffChat] &7" + player.getName() + "»&e " + event.getMessage()), "kitpvp.staff");
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(e.getPlayer().getUniqueId());
        if (data.getTask() != null) {
            if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                data.getPlayer().sendMessage(ColorUtil.translate("&cTeleport canceled because of movement"));
                data.getTask().cancel();
                data.setTask(null);
            }
        }
        if (KitPvP.INSTANCE.getEventManager().noEvent(data)) {
            if (!WorldGuardUtils.isPvp(data.getPlayer())) {
                if (!data.isSpawn()) {
                    data.setSpawn(true);
                    data.spawnPlayer();
                }
            } else {
                if (data.isSpawn()) {
                    data.setSpawn(false);
                    if (data.getLastKit() != null) {
                        data.giveKit(data.getLastKit());
                    } else {
                        data.giveKit(KitPvP.INSTANCE.getKitManager().getKits().get(0));
                    }
                }
            }
        } else {
            data.setSpawn(false);
        }

    }


    @EventHandler
    public void onDeath(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerData killedUser = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());

        if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
            if (KitPvP.INSTANCE.getEventManager().getEvent().getPlayers().contains(killedUser)) {
                KitPvP.INSTANCE.getEventManager().getEvent().onDeath(killedUser);
            }
        }
        if(KitPvP.INSTANCE.getEventManager().noEvent(killedUser)) {
            killedUser.spawnPlayer();
        }

    }

    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = event.getEntity().getKiller();
        if (killer != null) {
            PlayerData killedUser = KitPvP.INSTANCE.getDataManager().getPlayer(killed.getUniqueId());
            PlayerData killerUser = KitPvP.INSTANCE.getDataManager().getPlayer(killer.getUniqueId());
            if (killerUser.getLastKit() != null) {
                if (killerUser.getLastKit().getName().contains("Switcher")) {
                    ItemStack ability = new ItemStack(Material.SNOW_BALL, 1);
                    ItemMeta abilityMeta = ability.getItemMeta();
                    abilityMeta.setDisplayName(ColorUtil.translate("&fSwitcher Ball"));
                    ability.setItemMeta(abilityMeta);
                    killer.getInventory().addItem(ability);
                }
            }
            killedUser.setKillStreak(0);
            killedUser.setDeaths(killedUser.getDeaths() + 1);
            //Kill rewards
            killerUser.setKillStreak(killerUser.getKillStreak() + 1);
            if (killerUser.getKillStreak() > killerUser.getMaxKillStreak()) {
                killerUser.setMaxKillStreak(killerUser.getMaxKillStreak());
            }
            killerUser.setKills(killerUser.getKills() + 1);
            double xpAdd = Math.ceil(Math.random() * 5) + 5;
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
                killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
                killerUser.setNeededXp(killer.getLevel() * 25);
            } else {
                killer.playSound(killer.getLocation(), Sound.ITEM_PICKUP, 1, 1);
            }

            event.setDeathMessage(ColorUtil.translate(Config.KILL_MESSAGE.replace("%killer%", killer.getName()).replace("%victim%", killed.getName())));

            killerUser.getPlayer().setLevel(killerUser.getLevel());
            killerUser.getPlayer().setExp((float) Math.max(0.99, killerUser.getXp() / killerUser.getNeededXp()));

        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(KitPvP.INSTANCE.getPlugin(), () -> {
            killed.spigot().respawn();
        }, 5L);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
        if (!event.getPlayer().hasPermission("kit.admin") || KitPvP.INSTANCE.getEventManager().noEvent(data)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRight(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(event.getPlayer().getUniqueId());
            if (event.getItem() != null && data.isSpawn()) {

                if (event.getItem().getType() == Material.BOOK) {
                    KitGui kitGui = new KitGui(data);
                    kitGui.openGui(event.getPlayer());
                }
                if (event.getItem().getType() == Material.GLOWSTONE_DUST) {
                    if (data.getLastKit() != null) {
                        data.setLastKit(data.getLastKit());
                        data.getPlayer().sendMessage(ColorUtil.translate(Config.RECEIVED_KIT.replace("%kit%", data.getLastKit().getName())));
                    } else {
                        data.getPlayer().sendMessage(ChatColor.RED + "You have no last kit!");
                    }
                }
                if (event.getItem().getType() == Material.EYE_OF_ENDER) {
                    event.setCancelled(true);
                    EventGui eventGui = new EventGui(data);
                    eventGui.openGui(event.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void onHungerDeplete(FoodLevelChangeEvent e) {

        e.setCancelled(true);
        Player player = (Player) e.getEntity();
        player.setFoodLevel(20);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        PlayerData data = KitPvP.INSTANCE.getDataManager().getPlayer(player.getUniqueId());
        if (e.getView().getTitle().equalsIgnoreCase("Kit Selector")) {
            for (Kit kit : KitPvP.INSTANCE.getKitManager().getKits()) {

                if (e.getInventory().getItem(e.getSlot()).getItemMeta().getDisplayName().equalsIgnoreCase(kit.getName())) {
                    if (data.getLevel() >= kit.getLevel()) {
                        data.setLastKit(kit);
                        data.getPlayer().sendMessage(ColorUtil.translate(Config.RECEIVED_KIT.replace("%kit%", kit.getName())));
                        data.getPlayer().closeInventory();
                    } else {
                        data.getPlayer().sendMessage(ChatColor.RED + "You need to be level " + kit.getLevel() + " to use that kit!");
                    }

                }
            }
            e.setCancelled(true);
        }
        if (e.getView().getTitle().equalsIgnoreCase("Event Selector")) {
            if (e.getInventory().getItem(e.getSlot()) != null) {
                if (ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(e.getInventory().getItem(e.getSlot())).getItemMeta()).getDisplayName()).contains("Sumo Event")) {
                    if (player.hasPermission("kitpvp.sumo")) {
                        SumoMap sumoMap = MapManager.getSumoMaps().get(0);
                        if (sumoMap != null) {
                            KitPvP.INSTANCE.getEventManager().createSumoEvent(Event.Type.SUMO, data, sumoMap);
                            player.sendMessage(ChatColor.GREEN + "Started event");
                        }
                        player.closeInventory();
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use events");
                        player.closeInventory();
                    }
                }
                if (ChatColor.stripColor(Objects.requireNonNull(Objects.requireNonNull(e.getInventory().getItem(e.getSlot())).getItemMeta()).getDisplayName()).contains("FFA Event")) {
                    if (player.hasPermission("kitpvp.ffa")) {
                        FFAMap ffaMap = MapManager.getFfaMaps().get(0);
                        if (ffaMap != null) {
                            KitPvP.INSTANCE.getEventManager().createFFAEvent(Event.Type.FFA, data, ffaMap);
                            player.sendMessage(ChatColor.GREEN + "Started event");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You do not have permission to use events");
                        player.closeInventory();
                    }
                }
            }
        }
    }

}
