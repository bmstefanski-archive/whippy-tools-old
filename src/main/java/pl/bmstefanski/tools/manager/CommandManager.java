package pl.bmstefanski.tools.manager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

public class CommandManager {

    private CommandManager() {

    }

    public static CommandMap getCommandMap() {
        Field field = null;

        try {
            field = SimplePluginManager.class.getDeclaredField("commandMap");
        } catch (NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }

        assert field != null;
        field.setAccessible(true);

        try {
            return (CommandMap)field.get(Bukkit.getServer().getPluginManager());
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void registerCommand(Command command) {
        getCommandMap().register("tools", command);
    }
}
