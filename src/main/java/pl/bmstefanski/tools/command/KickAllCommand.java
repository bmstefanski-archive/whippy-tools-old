package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class KickAllCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public KickAllCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "kickall")
    @Permission("tools.command.kickall")
    @GameOnly(false)
    public void command(Arguments arguments) {

        String reason = "";

        if (arguments.getArgs().length == 0) {
            reason = fixColor(messages.getDefaultReason());
        } else if (arguments.getArgs().length > 0) reason = fixColor(StringUtils.join(arguments.getArgs(), " ", 1, arguments.getArgs().length));

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(reason);
        }
    }
}
