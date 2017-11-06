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

public class MessageUtils {

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
}
