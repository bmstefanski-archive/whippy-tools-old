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

import java.util.*;

public class ListCommand {

    private final Tools plugin;
    private final Messages messages;

    public ListCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "list",
            description = "list command",
            usage = "[full/basic]",
            userOnly = true,
            completer = "listCompleter",
            min = 1
    )
    public void list(CommandSender commandSender, CommandContext context) {
        Player player = (Player) commandSender;

        int playersOnlineSize = plugin.getUserManager().getOnlinePlayers().size();
        int maxPlayers = Bukkit.getMaxPlayers();

        if (context.getArgs().length == 1) {
            if (context.getParam(0).equalsIgnoreCase("full")) {
                MessageUtils.sendMessage(player, StringUtils.replace(messages.getListFull(), "%online%", Arrays.toString(plugin.getUserManager().getOnlinePlayers().toArray())));
            } else if (context.getParam(0).equalsIgnoreCase("basic")) {
                MessageUtils.sendMessage(player, StringUtils.replaceEach(messages.getListBasic(),
                        new String[] {"%online%", "%max%"},
                        new String[] {playersOnlineSize + "", maxPlayers + ""}));
            }
        }
    }

    public List<String> listCompleter(CommandSender commandSender, CommandContext context) {
        if (context.getArgs().length == 1) {
            List<String> availableList = Arrays.asList("basic", "full");

            Collections.sort(availableList);
            return availableList;
        }

        return null;
    }
}
