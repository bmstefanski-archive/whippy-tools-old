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

public class TpCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public TpCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "tp", usage = "[player] [target]", min = 1, max = 2)
    @Permission("tools.command.tp")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (arguments.getArgs().length == 1) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            if (Bukkit.getPlayer(arguments.getArgs(0)) == null) {
                sendMessage(sender, StringUtils.replace(messages.getPlayerNotFound(), "%player%", arguments.getArgs(0)));
                return;
            }

            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(arguments.getArgs(0));

            player.teleport(target);
            sendMessage(sender, messages.getTeleportSuccess());

            return;
        }

        if (Bukkit.getPlayer(arguments.getArgs(0)) == null || Bukkit.getPlayer(arguments.getArgs(1)) == null) {
            sendMessage(sender, StringUtils.replaceEach(messages.getTpFailed(),
                    new String[] {"%player%", "%target%"},
                    new String[] {arguments.getArgs(0), arguments.getArgs(1)}));
            return;
        }

        Player player = Bukkit.getPlayer(arguments.getArgs(0));
        Player target = Bukkit.getPlayer(arguments.getArgs(1));

        player.teleport(target);
        sendMessage(sender, StringUtils.replaceEach(messages.getTpSuccess(),
                new String[] {"%player%", "%target%"},
                new String[] {player.getName(), target.getName()}));
    }

    @Completer("tp")
    public List<String> completer(Arguments arguments) {
        List<String> availableList = TabCompleterUtils.getAvailableList(arguments);
        if (availableList != null) return availableList;

        return null;
    }
}