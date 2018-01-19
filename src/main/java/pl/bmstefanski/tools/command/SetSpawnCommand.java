package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.SpawnConfig;
import pl.bmstefanski.tools.util.MessageUtils;

public class SetSpawnCommand {

    private final Tools plugin;
    private final Messages messages;
    private final SpawnConfig config;

    public SetSpawnCommand(Tools plugin) {
        this.plugin = plugin;
        this.messages = plugin.getMessages();
        this.config = plugin.getSpawnConfiguration();
    }

    @CommandInfo(
            name = "setspawn",
            description = "setspawn command",
            permission = "setspawn",
            userOnly = true
    )
    public void setSpawn(CommandSender commandSender, CommandContext context) {
        Player player = (Player) commandSender;
        Location location = player.getLocation();

        String worldName = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        config.setX(x);
        config.setY(y);
        config.setZ(z);
        config.setWorld(worldName);
        config.setExists(true);

        MessageUtils.sendMessage(player, StringUtils.replaceEach(messages.getSetspawnSuccess(),
                new String[] {"%x%", "%y%", "%z%", "%world%"},
                new String[] {x + "", y + "", z + "", worldName}));
    }
}
