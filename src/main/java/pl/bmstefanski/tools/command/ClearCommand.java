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

public class ClearCommand {

    @CommandInfo (
            name = {"clear", "ci"},
            description = "clear command",
            usage = "[player]",
            userOnly = true,
            permission = "clear",
            completer = "clearCompleter"
    )

    public void clear(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
            player.getInventory().clear();
            MessageUtils.sendMessage(player, Messages.CLEAR);

            return;
        }

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(0));

        target.getInventory().clear();

        MessageUtils.sendMessage(target, Messages.CLEAR);
        MessageUtils.sendMessage(player, StringUtils.replace(Messages.CLEAR_OTHER, "%player%", target.getName()));
    }

    public List<String> clearCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
