package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.manager.TeleportManager;
import pl.bmstefanski.tools.util.TeleportUtils;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class BackCommand extends CommandImpl {

    public BackCommand() {
        super("back", "back command", "/back [player]", "back", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            final Location location = new TeleportUtils().getLocation(player);

            new TeleportManager(player).start(location);
        } else {

            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);
            final Location location = new TeleportUtils().getLocation(target);

            target.teleport(location);
        }
    }
}
