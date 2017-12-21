package pl.bmstefanski.tools.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.util.TabCompleterUtils;

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
        List<String> availableList = TabCompleterUtils.getAvailableList(context);
        if (availableList != null) return availableList;

        return null;
    }
}
