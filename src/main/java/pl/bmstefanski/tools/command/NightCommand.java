package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

public class NightCommand implements MessageUtils {

    private final Tools plugin;
    private final Messages messages;

    public NightCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @CommandInfo(
            name = "night",
            description = "night command",
            permission = "night",
            usage = "[world]"
    )
    private void night(CommandSender sender, CommandContext context) {

        if (context.getArgs().length == 0) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) sender;
            player.getWorld().setTime(12566);

            sendMessage(player, StringUtils.replace(messages.getNight(), "%world%", player.getWorld().getName()));

            return;
        }

        if (Bukkit.getWorld(context.getParam(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getWorldNotFound(), "%world%", context.getParam(0)));
            return;
        }

        World world = Bukkit.getWorld(context.getParam(0));
        world.setTime(12566);

        sendMessage(sender, StringUtils.replace(messages.getNight(), "%world%", world.getName()));
    }
}
