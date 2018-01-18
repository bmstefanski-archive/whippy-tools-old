package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.api.basic.User;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.*;

import java.util.List;

public class WhoisCommand {

    private final Tools plugin;

    public WhoisCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo (
            name = "whois",
            description = "whois command",
            permission = "whois",
            userOnly = true,
            usage = "[player]",
            completer = "whoisCompleter"
    )
    public void whois(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;
        Messages messages = plugin.getMessages();

        if (context.getArgs().length == 0) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

            send(player, offlinePlayer);
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target.getUniqueId());

            send(target, offlinePlayer);
        }
    }

    private void send(Player player, OfflinePlayer offlinePlayer) {
        User user = UserManager.getUser(offlinePlayer.getUniqueId());
        Messages messages = plugin.getMessages();

        Location location = offlinePlayer.getPlayer().getLocation();
        String playerHealth = offlinePlayer.getPlayer().getHealth() + "/20";
        String playerFoodLevel = offlinePlayer.getPlayer().getFoodLevel() + "/20";
        String playerGamemode = offlinePlayer.getPlayer().getGameMode().toString().toLowerCase();
        String playerLocation = "("
                + offlinePlayer.getPlayer().getWorld().getName() + ", "
                + location.getBlockX() + ", "
                + location.getBlockY() + ", "
                + location.getBlockZ() + ")";
        String playerJoin = TimeUtils.parse(offlinePlayer.getFirstPlayed());
        String playerLast = user.isOnline() ? "online" : TimeUtils.parse(offlinePlayer.getLastPlayed());

        String whois = TextUtils.listToString(messages.getWhois());

        MessageUtils.sendMessage(player, StringUtils.replaceEach(whois,
                new String[] {"%nickname%", "%uuid%", "%ip%", "%registered%", "%last%", "%location%", "%hp%", "%hunger%", "%gamemode%", "%god%", "%fly%"},
                new String[] {offlinePlayer.getName(), offlinePlayer.getUniqueId().toString(), offlinePlayer.getPlayer().getAddress().getHostName(),
                        playerJoin, playerLast, playerLocation, playerHealth, playerFoodLevel, playerGamemode, BooleanUtils.parse(user.isGod()),
                        BooleanUtils.parse(offlinePlayer.getPlayer().isFlying())
                }));
    }

    public List<String> whoisCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
