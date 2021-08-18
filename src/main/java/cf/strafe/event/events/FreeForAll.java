package cf.strafe.event.events;

import cf.strafe.KitPvP;
import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;
import cf.strafe.event.map.FFAMap;
import cf.strafe.utils.ColorUtil;
import cf.strafe.utils.scoreboard.FastBoard;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FreeForAll extends Event {
    private final FFAMap map;

    public FreeForAll(FFAMap map, PlayerData host) {
        this.map = map;
        maxPlayers = 50;
        state = State.WAITING;
        gameTime = 30;
        this.host = host;
    }


    @Override
    public void onDeath(PlayerData playerData) {
        players.remove(playerData);
        new BukkitRunnable() {
            @Override
            public void run() {
                addPlayer(playerData);
            }
        }.runTaskLater(KitPvP.INSTANCE.getPlugin(), 5);

        super.onDeath(playerData);
    }

    @Override
    public void addPlayer(PlayerData player) {
        player.getPlayer().teleport(map.getSpawnLocation());
        KitPvP.INSTANCE.getScoreboardManager().get(player.getPlayer()).updateLines();
        super.addPlayer(player);
    }

    @Override
    public void update() {
        updateBoard();
        switch (state) {
            case WAITING: {
                gameTime--;
                if (gameTime == 29 || gameTime == 20 || gameTime == 10) {
                    TextComponent textComponent = new TextComponent(ColorUtil.translate("&6[Event] &f" + host.getPlayer().getName() + " &7is hosting a &fFFA Event! &a[Click to join]"));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ffa join"));
                    Bukkit.broadcastMessage(ColorUtil.translate("&7"));
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.spigot().sendMessage(textComponent);
                    }
                    Bukkit.broadcastMessage(ColorUtil.translate("&7"));
                }
                if (gameTime == 0) {
                    if (players.size() > 1) {
                        state = State.INGAME;
                        Bukkit.broadcastMessage(ColorUtil.translate("&6[Event] &fFFA Event &7has started!"));
                    } else {
                        KitPvP.INSTANCE.getEventManager().deleteEvent("Not enough players.");
                    }
                }
                break;
            }

            case INGAME: {
                gameTime++;
                if (gameTime == 1) {
                    for (PlayerData data : players) {
                        data.getPlayer().teleport(map.getFightLocation());
                        data.getPlayer().sendMessage(ColorUtil.translate("&6[Event] &fFree for all!"));
                        if (data.getLastKit() == null) {
                            data.giveKit(KitPvP.INSTANCE.getKitManager().getKits().get(0));
                        } else {
                            data.giveKit(data.getLastKit());
                        }
                    }
                }
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
        List<PlayerData> allPlayers = new ArrayList<PlayerData>(players);
        allPlayers.addAll(spectators);
        for (PlayerData boardPlayer : allPlayers) {
            FastBoard board = KitPvP.INSTANCE.getScoreboardManager().get(boardPlayer.getPlayer());
            if (board.getLines().size() > 8) {
                board.updateLines();
            }
            board.updateTitle(ColorUtil.translate("&6&lStrafed &7┃ &fKits"));
            board.updateLine(0, ColorUtil.translate("&7&m------------------"));
            board.updateLine(1, ColorUtil.translate("&6Event: &fFFA"));
            board.updateLine(2, ColorUtil.translate("&6Players: &f" + players.size() + "/" + maxPlayers));
            board.updateLine(3, ColorUtil.translate("&7&m------------------"));
            if (state == State.INGAME) {
                board.updateLine(4, "Free for all!!!");
            } else if (state == State.WAITING) {
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
