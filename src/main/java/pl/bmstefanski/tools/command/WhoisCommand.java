package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.basic.User;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TextUtils;
import pl.bmstefanski.tools.util.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhoisCommand {

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

        if (context.getArgs().length == 0) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

            send(player, offlinePlayer);
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target.getUniqueId());

            send(target, offlinePlayer);
        }
    }

    private void send(Player player, OfflinePlayer offlinePlayer) {
        User user = User.get(offlinePlayer.getUniqueId());

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

        String whois = TextUtils.listToString(Messages.WHOIS);

        MessageUtils.sendMessage(player, StringUtils.replaceEach(whois,
                new String[] {"%nickname%", "%uuid%", "%ip%", "%registered%", "%last%", "%location%", "%hp%", "%hunger%", "%gamemode%", "%god%", "%fly%"},
                new String[] {offlinePlayer.getName(), offlinePlayer.getUniqueId().toString(), offlinePlayer.getPlayer().getAddress().getHostName(),
                        playerJoin, playerLast, playerLocation, playerHealth, playerFoodLevel, playerGamemode, BooleanUtils.parse(user.isGod()),
                        BooleanUtils.parse(offlinePlayer.getPlayer().isFlying())
                }));
    }

    public List<String> whoisCompleter(CommandSender commandSender, CommandContext context) {
        if (context.getArgs().length == 1) {
            List<String> availableList = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(context.getParam(0).toLowerCase())) {
                    availableList.add(player.getName());
                }
            }

            Collections.sort(availableList);
            return availableList;
        }

        return null;
    }
}
