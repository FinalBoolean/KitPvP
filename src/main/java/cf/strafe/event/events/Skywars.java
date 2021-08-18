package cf.strafe.event.events;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;
import cf.strafe.event.map.SkywarsMap;
import cf.strafe.event.map.skywars.ChestLocation;
import cf.strafe.utils.ColorUtil;
import cf.strafe.utils.ItemUtil;
import cf.strafe.utils.Pair;
import cf.strafe.utils.scoreboard.FastBoard;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class Skywars extends Event {

    private final SkywarsMap map;

    private final List<Location> usedLocations = new ArrayList<>();
    public Skywars(SkywarsMap map, PlayerData host) {
        this.map = map;
        maxPlayers = 12;

        state = State.WAITING;
        gameTime = 60;
        this.host = host;
    }
    /*
    This should work right ?
     */

    @Override
    public void addPlayer(PlayerData player) {
        for(Location locations : map.getSpawnLocations()) {
            if(!usedLocations.contains(locations)) {
                player.getPlayer().teleport(locations);
                int y_inc = 2;
                int xz_inc = 1;
                for (int x = -(xz_inc); x <= xz_inc; x++) {
                    for (int y = -(y_inc); y <= y_inc; y++) {
                        for (int z = -(xz_inc); z <= xz_inc; z++) {
                            Location loc = new Location(locations.getWorld(), x, y, z);
                            if (locations == loc || player.getPlayer().getEyeLocation() == loc) {
                                return;
                            }
                            Block b = loc.getBlock();
                            b.setType(Material.GLASS);
                        }
                    }
                }
                usedLocations.add(locations);
                break;
            }
        }
        super.addPlayer(player);
    }

    @Override
    public void update() {
        updateBoard();
        switch (state) {
            case WAITING: {
                gameTime--;

                if(gameTime == 30 || gameTime == 20 || gameTime == 10 || gameTime == 50) {
                    TextComponent textComponent = new TextComponent(ColorUtil.translate("&6[Event] &f" + host.getPlayer().getName() + " &7is hosting a &fSkywars Event! &a[Click to join]"));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/skywars join"));
                    Bukkit.broadcastMessage(ColorUtil.translate("&7"));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.spigot().sendMessage(textComponent);
                    }
                    Bukkit.broadcastMessage(ColorUtil.translate("&7"));
                }

                if (gameTime == 0) {
                    if (players.size() > 1) {
                        state = State.INGAME;
                        Bukkit.broadcastMessage(ColorUtil.translate("&6[Event] &fSkyWars Event &7has started!"));
                        for(Location location : usedLocations) {
                            int y_inc = 2;
                            int xz_inc = 1;
                            for (int x = -(xz_inc); x <= xz_inc; x++) {
                                for (int y = -(y_inc); y <= y_inc; y++) {
                                    for (int z = -(xz_inc); z <= xz_inc; z++) {
                                        Location loc = new Location(location.getWorld(), x, y, z);
                                        if (location == loc) {
                                            return;
                                        }
                                        Block b = loc.getBlock();
                                        b.setType(Material.AIR);
                                    }
                                }
                            }
                        }
                        for(ChestLocation chestLocations : map.getChestLocations()) {
                            Block b = chestLocations.getLocation().getBlock();
                            if(b.getBlockData().getMaterial() == Material.CHEST) {
                                Chest chest = (Chest) b;
                                chest.getBlockInventory().setContents(chestLocations.getInventory());
                            }
                        }
                    } else {
                        KitPvP.INSTANCE.getEventManager().deleteEvent("Not enough players.");
                    }
                }

                break;
            }
            case INGAME: {
                if (players.size() < 2) {
                    Bukkit.broadcastMessage(ColorUtil.translate("&6[Event] &f" + players.get(0).getPlayer().getName() + " &7has won the &fFFA Event&7!"));
                    state = State.END;
                }
                break;
            }
            case END: {
                KitPvP.INSTANCE.getEventManager().deleteEvent();
                /*
                 * Having to do this since if someone dies we don't want a concurrent exception.
                 */
                for (PlayerData players : getSpectators()) {
                    removePlayer(players);
                }
                break;
            }
        }
    }

    private void updateBoard() {
        List<PlayerData> allPlayers = new ArrayList<>(players);
        allPlayers.addAll(spectators);
        for (PlayerData boardPlayer : allPlayers) {
            FastBoard board = KitPvP.INSTANCE.getScoreboardManager().get(boardPlayer.getPlayer());
            if (board.getLines().size() > 8) {
                board.updateLines();
            }
            board.updateTitle(ColorUtil.translate("&6&lStrafed &7┃ &fKits"));
            board.updateLine(0, ColorUtil.translate("&7&m------------------"));
            board.updateLine(1, ColorUtil.translate("&6Event: &fSumo"));
            board.updateLine(2, ColorUtil.translate("&6Players: &f" + players.size() + "/" + maxPlayers));
            board.updateLine(3, ColorUtil.translate("&7&m------------------"));

            if (state == State.WAITING) {
                board.updateLine(4, ColorUtil.translate("&6Starting in: &f" + gameTime + "s"));
            } else {
                board.updateLine(4, ColorUtil.translate("&eWaiting for players..."));
            }
            board.updateLine(5, "");
            board.updateLine(6, "strafekits.minehut.gg");
            board.updateLine(7, ColorUtil.translate("&7&m------------------"));
        }
    }



}
