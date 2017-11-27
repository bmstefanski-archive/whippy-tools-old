package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BanCommand {

    @CommandInfo (
            name = "ban",
            description = "ban command",
            usage = "[player]",
            permission = "ban",
            min = 1,
            completer = "banCompleter"
    )

    private void ban(CommandSender commandSender, CommandContext context) {

    }

    public List<String> banCompleter(CommandSender commandSender, CommandContext context) {
        if (context.getArgs().length == 1) {
            List<String> availableList = new ArrayList<>();

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().startsWith(context.getParam(0).toLowerCase())) {
                    availableList.add(player.getName());
                }
            }

            Collections.sort(availableList);
            return availableList;
        }

        return null;
    }
}
