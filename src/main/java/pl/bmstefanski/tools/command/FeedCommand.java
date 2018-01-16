package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class FeedCommand {

    @CommandInfo (
            name = "feed",
            description = "feed command",
            usage = "[player]",
            userOnly = true,
            permission = "feed",
            completer = "feedCompleter"
    )

    public void feed(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
            player.setFoodLevel(20);

            MessageUtils.sendMessage(player, Messages.FED);
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));

            target.setFoodLevel(20);

            MessageUtils.sendMessage(target, Messages.FED);
            MessageUtils.sendMessage(player, StringUtils.replace(Messages.FED_OTHER, "%player%", target.getName()));
        }

    }

    public List<String> feedCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
