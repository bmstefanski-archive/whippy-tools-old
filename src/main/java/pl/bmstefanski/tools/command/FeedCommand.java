package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class FeedCommand {

    private final Tools plugin;

    public FeedCommand(Tools plugin) {
        this.plugin = plugin;
    }

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
        Messages messages = plugin.getMessages();

        if (context.getArgs().length == 0) {
            player.setFoodLevel(20);

            MessageUtils.sendMessage(player, messages.getFed());
        } else {
            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));

            target.setFoodLevel(20);

            MessageUtils.sendMessage(target, messages.getFed());
            MessageUtils.sendMessage(player, StringUtils.replace(messages.getFedOther(), "%player%", target.getName()));
        }

    }

    public List<String> feedCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
