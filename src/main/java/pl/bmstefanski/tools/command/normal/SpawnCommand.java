package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.manager.SpawnManager;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class SpawnCommand extends CommandImpl {

    private FileConfiguration fileConfiguration = Tools.getInstance().getConfig();

    public SpawnCommand() {
        super("spawn", "spawn command", "/spawn [player]", "spawn", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;
        final SpawnManager spawnManager = new SpawnManager();

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (fileConfiguration.getBoolean("spawn.setted")) {

            if (args.length == 0) {
                new TeleportManager(player).start(spawnManager.getSpawn());
            } else {

                if (Bukkit.getPlayer(args[0]) == null) {
                    Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                    return;
                }

                final Player target = Bukkit.getPlayer(args[0]);

                player.teleport(spawnManager.getSpawn());
            }

        } else {
            Utils.sendMessage(player, Messages.SPAWN_FAILED);
        }
    }
}
