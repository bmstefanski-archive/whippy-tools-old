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

public class TpCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public TpCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "tp",
            description = "tp command",
            usage = "[player] [target]",
            permission = "tp",
            min = 1,
            completer = "tpCompleter"
    )
    private void tp(CommandSender sender, CommandContext context) {

        if (context.getArgs().length == 1) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
                return;
            }

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(context.getParam(0));

            player.teleport(target);
            sendMessage(sender, messages.getTeleportSuccess());

            return;
        }

        if (Bukkit.getPlayer(context.getParam(0)) == null || Bukkit.getPlayer(context.getParam(1)) == null) {
            sendMessage(sender, StringUtils.replaceEach(messages.getTpFailed(),
                    new String[] {"%player%", "%target%"},
                    new String[] {context.getParam(0), context.getParam(1)}));
            return;
        }

        Player player = Bukkit.getPlayer(context.getParam(0));
        Player target = Bukkit.getPlayer(context.getParam(1));

        player.teleport(target);
        sendMessage(sender, StringUtils.replaceEach(messages.getTpSuccess(),
                new String[] {"%player%", "%target%"},
                new String[] {player.getName(), target.getName()}));
    }

    public List<String> tpCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}