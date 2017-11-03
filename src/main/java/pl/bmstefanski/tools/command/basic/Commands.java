package pl.bmstefanski.tools.command.basic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.bmstefanski.tools.impl.configuration.Messages;
import pl.bmstefanski.tools.io.Files;
import pl.bmstefanski.tools.util.Utils;

public abstract class Commands {

    private final Map<String, Command> commandMap = new HashMap<>();
    private final HashMap<String, Long> cooldown = new HashMap<>();
    private final YamlConfiguration yamlConfiguration = Files.getCommandsFile();

    public Command getCommand(String command) {
        return this.commandMap.get(command.toLowerCase());
    }

    public Set<String> getCommandNames() {
        return this.commandMap.keySet();
    }

    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();

        for (Command command : this.commandMap.values()) {
            if (!commands.contains(command)) {
                commands.add(command);
            }
        }

        return commands;
    }

    public abstract void handleCommand(CommandSender sender, CommandContext context);

    public void handleCommand(CommandSender sender, Command command, String label, String[] args) {
        this.handleCommand(sender, command, label, args, new CommandContextParser());
    }

    public void handleCommand(CommandSender sender, Command command, String label, String[] args, CommandContext.IContextParser parser) {
        int cooldownTime = yamlConfiguration.getInt(command.getCommand() + ".cooldown");
        if (cooldown.containsKey(sender.getName())) {
            long secondsLeft = ((cooldown.get(sender.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (secondsLeft > 0) {
                Utils.sendMessage(sender, Messages.TIME_LEFT.replace("%time%", secondsLeft + ""));
                return;
            }
        }
        cooldown.put(sender.getName(), System.currentTimeMillis());

        this.handleCommand(sender, parser.parse(command, label, args));
    }

    public abstract List<String> handleCompleter(CommandSender sender, CommandContext context);

    public List<String> handleCompleter(CommandSender sender, Command command, String label, String[] args) {
        return this.handleCompleter(sender, command, label, args, new CommandContextParser());
    }

    public List<String> handleCompleter(CommandSender sender, Command command, String label, String[] args, CommandContext.IContextParser parser) {
        return this.handleCompleter(sender, parser.parse(command, label, args));
    }

    public void registerCommand(Command command) {
        for (String name : command.getName()) {
            this.commandMap.put(name, command);
        }
    }

    public void registerCommandClass(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);

            Annotation annotation = method.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                this.registerCommandMethod(method, null, (CommandInfo) annotation);
            }
        }
    }

    public void registerCommandClasses(Class... classes) {
        for (Class clazz : classes) {
            this.registerCommandClass(clazz);
        }
    }

    public void registerCommandObject(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            Annotation annotation = method.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                this.registerCommandMethod(method, object, (CommandInfo) annotation);
            }
        }
    }

    public void registerCommandObjects(Object... objects) {
        for (Object object : objects) {
            this.registerCommandObject(object);
        }
    }

    public void registerCommandMethod(Method method, Object object, CommandInfo info) {
        Method completer = null;
        if (!info.completer().isEmpty()) {
            try {
                completer = object.getClass().getDeclaredMethod(info.completer(), CommandSender.class, CommandContext.class);
                completer.setAccessible(true);
            } catch (NoSuchMethodException ignored) {

            }
        }

        this.registerCommand(new Command(
                info.name(),
                info.description(),
                info.min(),
                info.usage(),
                info.userOnly(),
                info.flags(), info.permission(),
                method,
                object,
                completer
        ));
    }
}
