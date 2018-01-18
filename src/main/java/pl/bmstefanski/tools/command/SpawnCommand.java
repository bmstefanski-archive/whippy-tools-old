package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.List;

public class SpawnCommand {

    private final PluginConfig config;
    private final Tools plugin;

    public SpawnCommand(Tools plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
    }

    @CommandInfo (
            name = "spawn",
            description = "spawn command",
            permission = "spawn",
            userOnly = true,
            usage = "[player]",
            completer = "spawnCompleter"
    )

//    public void spawn(CommandSender commandSender, CommandContext context) {
//
//        Player player = (Player) commandSender;
//        SpawnManager spawnManager = new SpawnManager(config);
//
//        if (config.getSpawn()) {
//
//            if (context.getArgs().length == 0) {
//                new TeleportManager(plugin, player).start(spawnManager.getSpawn());
//                return;
//            }
//
//            if (Bukkit.getPlayer(context.getParam(0)) == null) {
//                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
//                return;
//            }
//
//            Player target = Bukkit.getPlayer(context.getParam(0));
//            target.teleport(spawnManager.getSpawn());
//
//        } else MessageUtils.sendMessage(player, Messages.SPAWN_FAILED);
//    }

    public List<String> spawnCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
