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

public class TpHereCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public TpHereCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "tphere",
            description = "tphere description",
            usage = "[player]",
            permission = "tphere",
            min = 1,
            userOnly = true,
            completer = "tphereCompleter"
    )
    private void tpHere(CommandSender sender, CommandContext context) {

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
            return;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(context.getParam(0));

        target.teleport(player);
        sendMessage(player, StringUtils.replaceEach(messages.getTpSuccess(),
                new String[] {"%player%", "%target%"},
                new String[] {target.getName(), player.getName()}));
    }

    public List<String> tphereCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
