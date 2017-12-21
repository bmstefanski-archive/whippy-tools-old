package pl.bmstefanski.tools.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.command.basic.CommandContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompleterUtils {

    public static List<String> getAvailableList(CommandContext context) {
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
