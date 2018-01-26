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

public class KickAllCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public KickAllCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "kickall",
            description = "kick all command",
            permission = "kickall"
    )
    private void kickAll(CommandSender sender, CommandContext context) {

        String reason = "";

        if (context.getArgs().length == 0) {
            reason = fixColor(messages.getDefaultReason());
        } else if (context.getArgs().length > 0) reason = fixColor(StringUtils.join(context.getArgs(), " ", 1, context.getArgs().length));

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(reason);
        }
    }
}
