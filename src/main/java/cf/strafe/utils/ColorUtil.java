package cf.strafe.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> message) {
        List<String> list = new ArrayList<>();
        for (String string : message) {
            list.add(ColorUtil.translate(string));
        }
        return list;
    }
}