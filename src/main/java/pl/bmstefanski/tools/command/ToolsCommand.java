package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.util.MessageUtils;

public class ToolsCommand {

    @CommandInfo(name = {"tools"}, description = "tools command", permission = "tools", userOnly = true)
    public void tools(CommandSender commandSender, CommandContext context) {

        Player player = (Player) commandSender;

        MessageUtils.sendMessage(player, "&e" + Tools.getInstance().getDescription().getName() +
                                        "&f(" + Tools.getInstance().getConfig().getString("language").toUpperCase() +
                                        ") &7" + Tools.getInstance().getDescription().getVersion() +
                                        " by §e" + Tools.getInstance().getDescription().getAuthors());
    }
}
