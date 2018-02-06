package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.Completer;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class KickCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public KickCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "kick", usage = "[player] [reason]", min = 1, max = 16)
    @Permission("tools.command.kick")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
            return;
        }

        Player target = Bukkit.getPlayer(arguments.getArgs(0));

        if (sender.getName().equals(target.getName())) {
            sendMessage(sender, messages.getCannotKickYourself());
            return;
        }

        String reason = "";

        if (arguments.getArgs().length == 1) {
            reason = fixColor(messages.getDefaultReason());
        } else if (arguments.getArgs().length > 1) reason = fixColor(StringUtils.join(arguments.getArgs(), " ", 1, arguments.getArgs().length));

        target.kickPlayer(reason);
    }

    @Completer("kick")
    public List<String> completer(Arguments arguments) {
        List<String> availableList = TabCompleterUtils.getAvailableList(arguments);
        if (availableList != null) return availableList;

        return null;
    }
}
