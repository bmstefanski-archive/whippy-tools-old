package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
            send(player);
        } else {

            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);

            send(target);
        }
    }

    private void send(Player player) {
        User user = User.get(player.getUniqueId());

        final Location location = player.getLocation();
        final String playerHealth = player.getHealth() + "/20";
        final String playerFoodLevel = player.getFoodLevel() + "/20";
        final String playerGamemode = player.getGameMode().toString().toLowerCase();
        final String playerLocation = "("
                + player.getWorld().getName() + ", "
                + location.getBlockX() + ", "
                + location.getBlockY() + ", "
                + location.getBlockZ() + ")";

        for (String string : Messages.WHOIS) {
            Utils.sendMessage(player, string
                    .replace("%nickname%", user.getName())
                    .replace("%uuid%", user.getUUID().toString())
                    .replace("%ip%", user.getIp())
                    .replace("%registered%", Utils.parseTime(user.getJoin()))
                    .replace("%last%", Utils.parseTime(user.getLast()))
                    .replace("%location%", playerLocation)
                    .replace("%hp%", playerHealth)
                    .replace("%hunger%", playerFoodLevel)
                    .replace("%gamemode%", playerGamemode)
                    .replace("%god%", Utils.parseBoolean(user.isGod()))
                    .replace("%fly%", Utils.parseBoolean(player.isFlying())));
        }
    }
}
