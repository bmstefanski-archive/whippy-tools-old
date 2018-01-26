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

public class KickCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public KickCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "kick",
            description = "kick command",
            usage = "[player] [reason]",
            permission = "kick",
            min = 1,
            completer = "kickCompleter"
    )
    private void kick(CommandSender sender, CommandContext context) {

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(0));

        if (sender.getName().equals(target.getName())) {
            sendMessage(sender, messages.getCannotKickYourself());
            return;
        }

        String reason = "";

        if (context.getArgs().length == 1) {
            reason = fixColor(messages.getDefaultReason());
        } else if (context.getArgs().length > 1) reason = fixColor(StringUtils.join(context.getArgs(), " ", 1, context.getArgs().length));

        target.kickPlayer(reason);
    }

    public List<String> kickCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
