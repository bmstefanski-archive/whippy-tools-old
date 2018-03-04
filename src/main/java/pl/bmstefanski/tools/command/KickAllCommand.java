package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.CommandArguments;
import pl.bmstefanski.commands.CommandExecutor;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class KickAllCommand implements Messageable, CommandExecutor {

    private final Tools plugin;
    private final Messages messages;

    public KickAllCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "kickall")
    @Permission("tools.command.kickall")
    @GameOnly(false)
    @Override
    public void execute(CommandSender commandSender, CommandArguments commandArguments) {

        String reason = "";

        if (commandArguments.getSize() == 1) {
            reason = fixColor(messages.getDefaultReason());
        } else if (commandArguments.getSize() > 1) reason = fixColor(StringUtils.join(commandArguments.getParams().toArray(), " ", 1, commandArguments.getArgs()));

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(reason);
        }

    }

}
