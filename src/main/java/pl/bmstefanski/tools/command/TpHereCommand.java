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

public class TpHereCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public TpHereCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tphere", usage = "[player]", min = 1, max = 1)
    @Permission("tools.command.tphere")
    @GameOnly
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
            return;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(arguments.getArgs(0));

        target.teleport(player);
        sendMessage(player, StringUtils.replaceEach(messages.getTpSuccess(),
                new String[] {"%player%", "%target%"},
                new String[] {target.getName(), player.getName()}));
    }

    @Completer("tphere")
    public List<String> completer(Arguments arguments) {
        List<String> availableList = TabCompleterUtils.getAvailableList(arguments);
        if (availableList != null) return availableList;

        return null;
    }
}
