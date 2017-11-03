package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.object.User;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class WhoisCommand extends CommandImpl {

    public WhoisCommand() {
        super("whois", "whois command", "/whois [player]", "whois", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

            send(player, offlinePlayer);
        } else {

            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);
            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target.getUniqueId());

            send(target, offlinePlayer);
        }
    }

    private void send(Player player, OfflinePlayer offlinePlayer) {
        final User user = User.get(offlinePlayer.getUniqueId());

        final Location location = offlinePlayer.getPlayer().getLocation();
        final String playerHealth = offlinePlayer.getPlayer().getHealth() + "/20";
        final String playerFoodLevel = offlinePlayer.getPlayer().getFoodLevel() + "/20";
        final String playerGamemode = offlinePlayer.getPlayer().getGameMode().toString().toLowerCase();
        final String playerLocation = "("
                + offlinePlayer.getPlayer().getWorld().getName() + ", "
                + location.getBlockX() + ", "
                + location.getBlockY() + ", "
                + location.getBlockZ() + ")";
        final String playerJoin = Utils.parseTime(offlinePlayer.getFirstPlayed());
        final String playerLast = user.isOnline() ? "online" : Utils.parseTime(offlinePlayer.getLastPlayed());

        final String whois = Utils.listToString(Messages.WHOIS);

        Utils.sendMessage(player, whois
                .replace("%nickname%", offlinePlayer.getName())
                .replace("%uuid%", offlinePlayer.getUniqueId().toString())
                .replace("%ip%", offlinePlayer.getPlayer().getAddress().getHostName())
                .replace("%registered%", playerJoin)
                .replace("%last%", playerLast)
                .replace("%location%", playerLocation)
                .replace("%hp%", playerHealth)
                .replace("%hunger%", playerFoodLevel)
                .replace("%gamemode%", playerGamemode)
                .replace("%god%", Utils.parseBoolean(user.isGod()))
                .replace("%fly%", Utils.parseBoolean(offlinePlayer.getPlayer().isFlying())));
    }
}
