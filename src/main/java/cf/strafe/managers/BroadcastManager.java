package cf.strafe.managers;

import cf.strafe.KitPvP;
import cf.strafe.config.Config;
import cf.strafe.utils.DefaultFontInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class BroadcastManager {

    private int counter;

    public BroadcastManager() {
        FileConfiguration config = KitPvP.INSTANCE.getPlugin().getConfig();
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(KitPvP.INSTANCE.getPlugin(), () -> {

            counter++;
            if (counter > Config.broadcasts) {
                counter = 1;
            }
            List<String> messages = config.getStringList("announcements.announcement" + counter);
            for (String message : messages) {

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (message.contains("[center]")) {
                        sendCenteredMessage(p, message.replace("[center]", ""));
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                }

            }
        }, 0, Config.delay);
    }

    private final static int CENTER_PX = 154;
    public void sendCenteredMessage(Player player, String message) {
        if (message == null || message.equals(""))
            player.sendMessage("");
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;


        for (char c : message.toCharArray()) {
            if (c == '�') {
                previousCode = true;
                continue;
            } else if (previousCode == true) {
                previousCode = false;
                if (c == 'l' || c == 'L') {
                    isBold = true;
                    continue;
                } else
                    isBold = false;
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while (compensated < toCompensate) {
            sb.append(" ");
            compensated += spaceLength;
        }
        player.sendMessage(sb.toString() + message);
    }
}
