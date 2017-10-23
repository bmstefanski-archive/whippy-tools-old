package pl.bmstefanski.tools.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.configuration.Messages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Utils {

    private static final StringBuilder stringBuilder = new StringBuilder();

    public static String fixColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendMessage(Player player, String string) {
        player.sendMessage(fixColor(string));
    }

    public static void sendMessage(CommandSender sender, String string) {
        sender.sendMessage(fixColor(string));
    }

    public static void sendMessageToConsole(String string) {
        Bukkit.getConsoleSender().sendMessage(fixColor(string));
    }

    public static String parseBoolean(boolean bool) {
        return bool ? Messages.BOOLEAN_ON : Messages.BOOLEAN_OFF;
    }

    public static String listToString(List<String> list) {
        stringBuilder.setLength(0);
        String result = null;

        for (String string : list) {
            stringBuilder.append(string + "\n");
        }

        return Utils.fixColor(stringBuilder.toString());
    }

    public static String parseTime(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMAN);
        Date date = new Date(time);

        return simpleDateFormat.format(date);
    }
}
