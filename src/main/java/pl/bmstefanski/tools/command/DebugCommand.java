package pl.bmstefanski.tools.command;

import org.bukkit.command.CommandSender;
import pl.bmstefanski.tools.Tools;
import pl.bmstefanski.tools.command.basic.CommandContext;
import pl.bmstefanski.tools.command.basic.CommandInfo;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;

public class DebugCommand {

    private final Tools plugin;

    public DebugCommand(Tools plugin) {
        this.plugin = plugin;
    }

    @CommandInfo(name = "debuger")
    private void debug(CommandSender sender, CommandContext context) {

        PluginConfig config = plugin.getConfiguration();

        System.out.println("configuration: " + plugin.getConfiguration());
        System.out.println("storage: " + plugin.getStorage());
        System.out.println(plugin.getConfiguration().getLanguage());
        System.out.println(plugin.getConfiguration().getMySQLSection().getUsername());

        System.out.println(config.getMySQLSection().getDatabase());
        System.out.println(config.getSpawnSection().getX());
    }
}
