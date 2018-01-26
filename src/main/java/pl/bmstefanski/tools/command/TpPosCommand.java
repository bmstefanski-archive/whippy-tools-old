package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.Parser;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class TpPosCommand implements MessageUtils, Parser {

    private final Tools plugin;
    private final Messages messages;

    public TpPosCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "tppos",
            description = "tppos description",
            permission = "tppos",
            usage = "[x] [y] [z] [player]",
            min = 3,
            completer = "tpposCompleter"
    )
    private void tppos(CommandSender sender, CommandContext context) {

        int x = parseInt(context.getParam(0));
        int y = parseInt(context.getParam(1));
        int z = parseInt(context.getParam(2));

        if (context.getArgs().length == 3) {

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

        if (Bukkit.getPlayer(context.getParam(3)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(3)));
            return;
        }

        Player player = Bukkit.getPlayer(context.getParam(3));
        Location location = new Location(player.getWorld(), x, y, z);

        player.teleport(location);
        sendMessage(player, StringUtils.replaceEach(messages.getTppos(),
                new String[] {"%player%" ,"%x%", "%y%", "%z%"},
                new String[] {player.getName(), x + "", y + "", z + ""}));
    }

    public List<String> tpposCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
