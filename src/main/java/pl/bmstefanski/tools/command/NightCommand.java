package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.commands.Arguments;
import pl.bmstefanski.commands.Messageable;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.commands.annotation.GameOnly;
import pl.bmstefanski.commands.annotation.Permission;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class NightCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public NightCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "night", usage = "[world]", max = 1)
    @Permission("tools.command.night")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (arguments.getArgs().length == 0) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) sender;
            player.getWorld().setTime(12566);

            sendMessage(player, StringUtils.replace(messages.getNight(), "%world%", player.getWorld().getName()));

            return;
        }

        if (Bukkit.getWorld(arguments.getArgs(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getWorldNotFound(), "%world%", arguments.getArgs(0)));
            return;
        }

        World world = Bukkit.getWorld(arguments.getArgs(0));
        world.setTime(12566);

        sendMessage(sender, StringUtils.replace(messages.getNight(), "%world%", world.getName()));
    }
}
