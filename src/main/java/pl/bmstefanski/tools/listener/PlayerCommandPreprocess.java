package pl.bmstefanski.tools.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.util.MessageUtils;

public class PlayerCommandPreprocess implements Listener {

    private final Tools plugin;

    public PlayerCommandPreprocess(Tools plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().split(" ")[0];
        HelpTopic helpTopic = Bukkit.getHelpMap().getHelpTopic(command);
        Messages messages = plugin.getMessages();

        if (helpTopic == null) {
            event.setCancelled(true);
            MessageUtils.sendMessage(event.getPlayer(), messages.getUnknownCommand().replace("%command%", command));
        }
    }
}
