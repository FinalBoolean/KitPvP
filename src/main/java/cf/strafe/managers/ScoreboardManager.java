package cf.strafe.managers;

import cf.strafe.KitPvP;
import cf.strafe.data.DataManager;
import cf.strafe.data.PlayerData;
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

            board.updateTitle(ColorUtil.translate("&6&lStrafed &7┃ &fKits"));
            board.updateLine(0, ColorUtil.translate("&7&m----------------------------"));
            board.updateLine(1, ColorUtil.translate("&6Level: &f" + pData.getLevel()));
            board.updateLine(2, ColorUtil.translate("&6Kills: &f" + pData.getKills()));
            board.updateLine(3, ColorUtil.translate("&6Deaths: &f" + pData.getDeaths()));
            board.updateLine(4, ColorUtil.translate("&6Killstreak: &f" + pData.getKillStreak()));
            board.updateLine(5, ColorUtil.translate("&6Progress: &f" + pData.getXp() + "/" + pData.getNeededXp()));
            //board.updateLine(5, ColorUtil.translate("&cCombat Tag: &f" + MathUtil.roundTo(Math.random() * 10, 1)));
            //board.updateLine(5, ColorUtil.translate("&7&m------------------"));
            //board.updateLine(6, ColorUtil.translate("&fEvent &6Sumo"));
            //board.updateLine(7, ColorUtil.translate(" &6Time: &f00:00"));
            //board.updateLine(8, ColorUtil.translate(" &6Players: &f10/50"));
            //board.updateLine(9, "");
            board.updateLine(7, "strafekits.minehut.gg");
            board.updateLine(8, ColorUtil.translate("&7&m----------------------------"));
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

}
