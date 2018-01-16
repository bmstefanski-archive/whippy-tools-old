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

public class HealCommand {

    @CommandInfo (
            name = "heal",
            description = "heal command",
            usage = "[player]",
            userOnly = true,
            permission = "heal",
            completer = "healCompleter"
    )

    public void heal(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
            player.setHealth(20D);

            MessageUtils.sendMessage(player, Messages.HEALED);
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));

            target.setHealth(20D);

            MessageUtils.sendMessage(target, Messages.HEALED);
            MessageUtils.sendMessage(player, StringUtils.replace(Messages.HEALED_OTHER, "%player%", target.getName()));
        }

    }

    public List<String> healCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
