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
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.util.TabCompleterUtils;
import pl.bmstefanski.tools.util.TeleportUtils;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.List;

public class BackCommand {

    private final PluginConfig config;
    private final Tools plugin;

    public BackCommand(Tools plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
    }

    @CommandInfo (
            name = "back",
            description = "back command",
            usage = "[player]",
            userOnly = true,
            permission = "back",
            completer = "backCompleter"
    )

    public void back(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (context.getArgs().length == 0) {
//            Location location = new TeleportUtils().getLocation(player);
//            new TeleportManager(plugin, player).start();

            return;
        }

        if (Bukkit.getPlayer(context.getParam(0)) == null) {
            MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
            return;
        }

        Player target = Bukkit.getPlayer(context.getParam(0));
//        Location location = new TeleportUtils().getLocation(target);

//        target.teleport(location);
    }

    public List<String> backCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
