package pl.bmstefanski.tools.listener;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.storage.configuration.Messages;

public class PlayerLogin implements Listener {

    private final Tools plugin;
    private final Messages messages;

    public PlayerLogin(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {

        Player player = event.getPlayer();

        int maxNicknameLength = plugin.getConfiguration().getMaxNicknameLength();

        if (player.getName().length() > maxNicknameLength) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringUtils.replace(messages.getTooLongNickname(), "%max%", maxNicknameLength + ""));

            return;
        }

    }

}
