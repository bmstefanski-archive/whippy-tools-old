package pl.bmstefanski.tools;

import org.bukkit.Bukkit;

public class Logger {

    private static final Tools plugin = Tools.getInstance();

    private static final String pluginName = "[Tools] ";

    public static void info(String string) {
        Bukkit.getLogger().info(pluginName + "> " + string);
    }

    public static void warning(String string) {
        Bukkit.getLogger().warning(pluginName + "> " + string);
    }

    public static void error(String string) {
        Bukkit.getLogger().severe("[Server thread/ERROR] #!# " + string);
    }

    public static boolean exception(String cause, StackTraceElement[] stackTraceElements) {
        error("");
        error(pluginName + "Critical error:");
        error("");
        error("Server Details:");
        error("  Tools: " + plugin.getDescription().getVersion());
        error("  Bukkit: " + Bukkit.getBukkitVersion());
        error("  Java: " + System.getProperty("java.version"));
        error("  Thread:" + Thread.currentThread());
        error("");
        error("Stacktrace: ");

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            error("  at " + stackTraceElement.toString());

        }

        error("");
        error("Caused by: " + cause);
        error("");
        return false;
    }




























}
