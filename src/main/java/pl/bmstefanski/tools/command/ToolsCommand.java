package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.util.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class ToolsCommand extends CommandImpl {

    public ToolsCommand() {
        super("tools", "tools command", "tools", "tools", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        Player player = (Player) commandSender;

        Utils.sendMessage(player, "&e" + Tools.getInstance().getDescription().getName() +
                                        "&f(" + Tools.getInstance().getConfig().getString("language").toUpperCase() +
                                        ") &7" + Tools.getInstance().getDescription().getVersion() +
                                        " by §e" + Tools.getInstance().getDescription().getAuthors());


    }
}
