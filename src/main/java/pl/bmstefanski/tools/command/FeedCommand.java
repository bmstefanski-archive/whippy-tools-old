package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class FeedCommand extends CommandImpl {

    public FeedCommand() {
        super("feed", "feed command", "/feed [player]", "feed", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            player.setFoodLevel(20);

            Utils.sendMessage(player, Messages.FEEDED);
        } else {
            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);

            target.setFoodLevel(20);

            Utils.sendMessage(target, Messages.FEEDED);
            Utils.sendMessage(player, Messages.FEEDED_OTHER.replace("%player%", target.getName()));
        }

    }
}
