package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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

public class InvseeCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public InvseeCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "invsee", usage = "[player]", max = 1)
    @Permission("tools.command.invsee")
    @GameOnly
    public void command(Arguments arguments) {

        Player player = arguments.getPlayer();

        if(arguments.getArgs().length == 0) {
            player.openInventory(player.getInventory());
            return;
        }

        if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
            sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
            return;
        }

        player.openInventory(Bukkit.getPlayer(arguments.getArgs(0)).getInventory());

    }

    @Completer("invsee")
    public List<String> completer(Arguments arguments) {
        List<String> availableList = TabCompleterUtils.getAvailableList(arguments);
        if (availableList != null) return availableList;

        return null;
    }
}
