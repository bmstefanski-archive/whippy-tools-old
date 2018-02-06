package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import pl.bmstefanski.tools.util.Parser;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class TpPosCommand implements Messageable, Parser {

    private final Tools plugin;
    private final Messages messages;

    public TpPosCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tppos", usage = "[x] [y] [z] [player]", min = 3, max = 4)
    @Permission("tools.command.tppos")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        int x = parseInt(arguments.getArgs(0));
        int y = parseInt(arguments.getArgs(1));
        int z = parseInt(arguments.getArgs(2));

        if (arguments.getArgs().length == 3) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) sender;

            Location location = new Location(player.getWorld(), x, y, z);
            player.teleport(location);
            sendMessage(player, StringUtils.replaceEach(messages.getTppos(),
                    new String[] {"%player%" ,"%x%", "%y%", "%z%"},
                    new String[] {player.getName(), x + "", y + "", z + ""}));

            return;
        }

        if (Bukkit.getPlayer(arguments.getArgs(3)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(3)));
            return;
        }

        Player player = Bukkit.getPlayer(arguments.getArgs(3));
        Location location = new Location(player.getWorld(), x, y, z);

        player.teleport(location);
        sendMessage(player, StringUtils.replaceEach(messages.getTppos(),
                new String[] {"%player%" ,"%x%", "%y%", "%z%"},
                new String[] {player.getName(), x + "", y + "", z + ""}));
    }

    @Completer("tppos")
    public List<String> completer(Arguments arguments) {
        List<String> availableList = TabCompleterUtils.getAvailableList(arguments);
        if (availableList != null) return availableList;

        return null;
    }
}
