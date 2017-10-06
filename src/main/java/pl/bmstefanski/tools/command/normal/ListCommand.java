package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.object.User;
import pl.bmstefanski.tools.object.util.UserUtils;
import pl.bmstefanski.tools.util.Utils;

import java.util.*;

public class ListCommand extends CommandImpl {

    public ListCommand() {
        super("list", "list command", "/list full/basic", "list", Collections.singletonList(""), true);
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;
        final Collection<? extends Player> playersOnline = Bukkit.getOnlinePlayers();
        final int playersOnlineSize = playersOnline.size();
        final int maxPlayers = Bukkit.getMaxPlayers();
        List<String> onlinePlayers = new ArrayList<>();

        if (args.length == 0 || args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("full")) {

                for (User user : UserUtils.getUsers()) {
                    if (!user.isOnline()) return;
                    if (onlinePlayers.contains(user.getName())) return;

                    onlinePlayers.add(user.getName());
                }

                Utils.sendMessage(player, Messages.LIST_FULL.replace("%online%", onlinePlayers.toString()));

            } else if (args[0].equalsIgnoreCase("basic")) {
                Utils.sendMessage(player, Messages.LIST_BASIC
                        .replace("%online%", playersOnlineSize + "")
                        .replace("%max%", maxPlayers + ""));
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            final List<String> availableList = Arrays.asList("basic", "full");

            Collections.sort(availableList);
            return availableList;
        }

        return null;
    }
}
