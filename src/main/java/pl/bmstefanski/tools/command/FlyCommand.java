package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.BooleanUtils;
import pl.bmstefanski.tools.util.MessageUtils;

import java.util.Collections;

public class FlyCommand extends CommandImpl {

    public FlyCommand() {
        super("fly", "fly command", "/fly [player]", "fly", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 1) {
            MessageUtils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
            return;
        }

        if (args.length == 0) {
            final boolean flyState = !player.isFlying();
            player.setAllowFlight(flyState);

            MessageUtils.sendMessage(player, Messages.FLY_SWITCHED.replace("%state%", BooleanUtils.parse(flyState)));
        } else {
            if (Bukkit.getPlayer(args[0]) == null) {
                MessageUtils.sendMessage(player, Messages.PLAYER_NOT_FOUND.replace("%player%", args[0]));
                return;
            }

            final Player target = Bukkit.getPlayer(args[0]);
            final boolean flyState = !target.isFlying();

            target.setAllowFlight(flyState);

            MessageUtils.sendMessage(player, Messages.FLY_SWITCHED_OTHER
                    .replace("%state%", BooleanUtils.parse(flyState))
                    .replace("%player%", target.getName()));
            MessageUtils.sendMessage(target, Messages.FLY_SWITCHED.replace("%state%", BooleanUtils.parse(flyState)));
        }
    }
}
