package cf.strafe.managers;

import cf.strafe.KitPvP;
import cf.strafe.data.DataManager;
import cf.strafe.data.PlayerData;
import cf.strafe.event.Event;
import cf.strafe.utils.ColorUtil;
import cf.strafe.utils.scoreboard.FastBoard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardManager {

    private final DataManager data = KitPvP.INSTANCE.getDataManager();
    private final Map<PlayerData, FastBoard> boards = new HashMap<>();

    public ScoreboardManager() {

        new BukkitRunnable() {
            @Override
            public void run() {
                update();
            }
        }.runTaskTimerAsynchronously(KitPvP.INSTANCE.getPlugin(), 0, 2);

    }

    private void update() {
        for (Map.Entry<PlayerData, FastBoard> entry : boards.entrySet()) {
            PlayerData pData = entry.getKey();
            FastBoard board = entry.getValue();

            entry.getKey().getPlayer().setPlayerListName(entry.getKey().getPlayer().getDisplayName());
            Event event;
            boolean inEvent = false;
            if (KitPvP.INSTANCE.getEventManager().getEvent() != null) {
                event = KitPvP.INSTANCE.getEventManager().getEvent();
                if (KitPvP.INSTANCE.getEventManager().getEvent().getPlayers().contains(pData)) {
                    inEvent = true;
                }
                if (KitPvP.INSTANCE.getEventManager().getEvent().getSpectators().contains(pData)) {
                    inEvent = true;
                }
            }

            if (!inEvent) {
                board.updateTitle(ColorUtil.translate("&6&lStrafed &7┃ &fKits"));
                board.updateLine(0, ColorUtil.translate("&7&m------------------"));
                board.updateLine(1, ColorUtil.translate("&6Level: &f" + pData.getLevel()));
                board.updateLine(2, ColorUtil.translate("&6Kills: &f" + pData.getKills()));
                board.updateLine(3, ColorUtil.translate("&6Deaths: &f" + pData.getDeaths()));
                board.updateLine(4, ColorUtil.translate("&6Killstreak: &f" + pData.getKillStreak()));
                board.updateLine(5, ColorUtil.translate("&6Progress: &f" + pData.getXp() + "/" + pData.getNeededXp()));
                board.updateLine(6, "");
                board.updateLine(7, "strafekits.cf");
                board.updateLine(8, ColorUtil.translate("&7&m------------------"));
            }
        }
    }

    public void create(Player player) {
        FastBoard board = new FastBoard(player);

        this.boards.put(data.getPlayer(player.getUniqueId()), board);
    }

    public void remove(Player player) {
        FastBoard board = this.boards.get(data.getPlayer(player.getUniqueId()));

        this.boards.remove(data.getPlayer(player.getUniqueId()));

        if (board != null) {
            board.delete();
        }

    }

    public FastBoard get(Player player) {
        return this.boards.get(data.getPlayer(player.getUniqueId()));
    }

}
