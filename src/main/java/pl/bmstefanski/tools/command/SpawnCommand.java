package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.configuration.Config;
import pl.bmstefanski.tools.configuration.Messages;
import pl.bmstefanski.tools.manager.SpawnManager;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.util.MessageUtils;
import pl.bmstefanski.tools.util.TabCompleterUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnCommand {

    private final Config config = Config.getInstance();

    @CommandInfo (
            name = "spawn",
            description = "spawn command",
            permission = "spawn",
            userOnly = true,
            usage = "[player]",
            completer = "spawnCompleter"
    )

    public void spawn(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;
        SpawnManager spawnManager = new SpawnManager();

        if (config.spawnSet) {

            if (context.getArgs().length == 0) {
                new TeleportManager(player).start(spawnManager.getSpawn());
                return;
            }

            if (Bukkit.getPlayer(context.getParam(0)) == null) {
                MessageUtils.sendMessage(player, StringUtils.replace(Messages.PLAYER_NOT_FOUND, "%player%", context.getParam(0)));
                return;
            }

            Player target = Bukkit.getPlayer(context.getParam(0));
            target.teleport(spawnManager.getSpawn());

        } else MessageUtils.sendMessage(player, Messages.SPAWN_FAILED);
    }

    public List<String> spawnCompleter(CommandSender commandSender, CommandContext context) {
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
