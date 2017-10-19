package pl.bmstefanski.tools.command.normal;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class FlyCommand extends CommandImpl {

    public FlyCommand() {
        super("fly", "fly command", "/fly [player]", "fly", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            final boolean flyState = !player.isFlying();
            player.setAllowFlight(flyState);

            Utils.sendMessage(player, Messages.FLY_SWITCHED.replace("%state%", Utils.parseBoolean(flyState)));
        } else {
            if (Bukkit.getPlayer(args[0]) == null) {
                Utils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);
            final boolean flyState = !target.isFlying();

            target.setAllowFlight(flyState);

            Utils.sendMessage(player, Messages.FLY_SWITCHED_OTHER
                    .replace("%state%", Utils.parseBoolean(flyState))
                    .replace("%player%", target.getName()));
            Utils.sendMessage(target, Messages.FLY_SWITCHED.replace("%state%", Utils.parseBoolean(flyState)));
        }
    }
}
