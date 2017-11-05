package pl.bmstefanski.tools.impl;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.util.Utils;

import java.util.*;

public abstract class CommandImpl extends Command {

    private final HashMap<String, Long> cooldown = new HashMap<>();

    public CommandImpl(String name, String description, String usage, String permission, List<String> aliases) {
        super(name, description, usage, aliases);

        setPermission("tools.command." + permission);
    }

    public abstract void onExecute(CommandSender commandSender, String[] args);

    public boolean execute(CommandSender commandSender, String string, String[] args) {

        if (!commandSender.hasPermission(getPermission())) {
            Utils.sendMessage(commandSender, Messages.NO_PERMISSIONS.replace("%permission%", getPermission()));
            return true;
        }

        if (!(commandSender instanceof Player)) {
            Utils.sendMessage(commandSender, Messages.ONLY_PLAYER);
            return true;
        }


        onExecute(commandSender, args);
        return true;
    }
}
