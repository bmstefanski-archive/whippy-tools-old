package pl.bmstefanski.tools.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0];
        HelpTopic helpTopic = Bukkit.getHelpMap().getHelpTopic(command);

        if (helpTopic == null) {
            event.setCancelled(true);
            Utils.sendMessage(event.getPlayer(), Messages.UNKNOWN_COMMAND.replace("%command%", command));
        }
    }
}
