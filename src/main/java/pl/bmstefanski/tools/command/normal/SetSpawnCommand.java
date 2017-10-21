package pl.bmstefanski.tools.command.normal;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.impl.CommandImpl;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.util.Utils;

import java.util.Collections;

public class SetSpawnCommand extends CommandImpl {

    private FileConfiguration fileConfiguration = Tools.getInstance().getConfig();

    public SetSpawnCommand() {
        super("setspawn", "setspawn command", "/setspawn", "setspawn", Collections.singletonList(""));
    }

    @Override
    public void onExecute(CommandSender commandSender, String[] args) {

        final Player player = (Player) commandSender;

        if (args.length > 0) {
            Utils.sendMessage(player, Messages.CORRECT_USAGE.replace("%usage%", getUsage()));
        } else {

            final World world = player.getWorld();
            final Location location = player.getLocation();

            final int x =  location.getBlockX();
            final int y = location.getBlockY();
            final int z = location.getBlockZ();

            fileConfiguration.set("spawn.x", x);
            fileConfiguration.set("spawn.y", y);
            fileConfiguration.set("spawn.z", z);
            fileConfiguration.set("spawn.world", world.getName());
            fileConfiguration.set("spawn.setted", true);

            fileConfiguration.options().copyDefaults(true);
            Tools.getInstance().saveConfig();

            Utils.sendMessage(player, Messages.SETSPAWN_SUCCESS
                    .replace("%x%", x + "")
                    .replace("%y%", y + "")
                    .replace("%z%", z + "")
                    .replace("%world%", world.getName()));
        }

    }
}
