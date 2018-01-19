package pl.bmstefanski.tools.command;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.SpawnConfig;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class SpawnCommand {

    private final Tools plugin;
    private final Messages messages;
    private final SpawnConfig config;

    public SpawnCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.config = plugin.getSpawnConfiguration();
    }

    @CommandInfo(
            name = "spawn",
            description = "spawn command",
            permission = "spawn",
            userOnly = true,
            usage = "[player]",
            completer = "spawnCompleter"
    )
    public void spawn(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        if (config.getExists()) {

            if (context.getArgs().length == 0) {
//                new TeleportManager(plugin, player).start(spawnManager.getSpawn());
                return;
            }

            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(messages.getPlayerNotFound(), "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));

            int x = config.getX();
            int y = config.getY();
            int z = config.getZ();
            String worldName = config.getWorld();

            Location location = new Location(Bukkit.getWorld(worldName), x, y, z);
            target.teleport(location);

            return;
        }

        MessageUtils.sendMessage(player, messages.getSpawnFailed());
    }

    public List<String> spawnCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
