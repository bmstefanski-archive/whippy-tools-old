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

public class DayCommand implements Messageable {

    private final Tools plugin;
    private final Messages messages;

    public DayCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @Command(name = "day", usage = "[world]", max = 1)
    @Permission("tools.command.day")
    @GameOnly(false)
    public void command(Arguments arguments) {

        CommandSender sender = arguments.getSender();

        if (arguments.getArgs().length == 0) {

            if (!(sender instanceof Player)) {
                sendMessage(sender, messages.getOnlyPlayer());
                return;
            }

            Player player = (Player) sender;
            player.getWorld().setTime(24000);

            sendMessage(player, StringUtils.replace(messages.getDay(), "%world%", player.getWorld().getName()));

            return;
        }

        if (Bukkit.getWorld(arguments.getArgs(0)) == null) {
            sendMessage(sender, StringUtils.replace(messages.getWorldNotFound(), "%world%", arguments.getArgs(0)));
            return;
        }

        World world = Bukkit.getWorld(arguments.getArgs(0));
        world.setTime(24000);

        sendMessage(sender, StringUtils.replace(messages.getDay(), "%world%", world.getName()));
    }
}
