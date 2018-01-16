package pl.bmstefanski.tools.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.util.MessageUtils;

public class SetSpawnCommand {

    private final Tools plugin;

    public SetSpawnCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo (
            name = "setspawn",
            description = "setspawn command",
            permission = "setspawn",
            userOnly = true
    )

    public void setSpawn(CommandSender commandSender, CommandContext context) {
        PluginConfig config = plugin.getConfiguration();

        Player player = (Player) commandSender;

        World world = player.getWorld();
        Location location = player.getLocation();

        int x =  location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        config.set("spawn.x", x);
        config.set("spawn.y", y);
        config.set("spawn.z", z);
        config.set("spawn.world", world.getName());
        config.set("spawn.set", true);

        MessageUtils.sendMessage(player, StringUtils.replaceEach(Messages.SETSPAWN_SUCCESS,
                new String[] {"%x%", "%y%", "%z%", "%world%"},
                new String[] {x + "", y + "", z + "", world.getName()}));

    }
}
