/*
 MIT License

 Copyright (c) 2018 Whippy Tools

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

package pl.bmstefanski.tools;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.diorite.config.ConfigManager;
import pl.bmstefanski.commands.BukkitCommands;
import pl.bmstefanski.commands.annotation.Command;
import pl.bmstefanski.tools.api.ToolsAPI;
import pl.bmstefanski.tools.api.storage.Storage;
import pl.bmstefanski.tools.basic.manager.UserManager;
import pl.bmstefanski.tools.command.*;
import pl.bmstefanski.tools.storage.StorageConnector;
import pl.bmstefanski.tools.storage.configuration.Messages;
import pl.bmstefanski.tools.storage.configuration.PluginConfig;
import pl.bmstefanski.tools.listener.*;
import pl.bmstefanski.tools.storage.resource.BanResourceManager;
import pl.bmstefanski.tools.type.DatabaseType;

import java.io.File;
import java.lang.reflect.Method;

public class Tools extends JavaPlugin implements ToolsAPI {

    private final File pluginConfigFile = new File(getDataFolder(), "config.yml");
    private final File messagesFile = new File(getDataFolder(), "messages.yml");

    private static Tools instance;

    private BanResourceManager banResource;
    private PluginConfig pluginConfig;
    private UserManager userManager;
    private Messages messages;
    private Storage storage;

    public Tools() {
        instance = this;
    }

    @Override
    public void onEnable() {

        this.pluginConfig = ConfigManager.createInstance(PluginConfig.class);
        this.messages = ConfigManager.createInstance(Messages.class);

        this.pluginConfig.bindFile(pluginConfigFile);
        this.messages.bindFile(messagesFile);

        this.pluginConfig.load();
        this.pluginConfig.save();

        this.messages.load();
        this.messages.save();

        setUpStorage();

        this.userManager = new UserManager();
        this.banResource = new BanResourceManager(storage);

        this.banResource.load();

        registerListeners(
                new PlayerCommandPreprocess(this),
                new PlayerJoin(this),
                new PlayerPreLogin(this),
                new PlayerQuit(this),
                new PlayerMove(this),
                new EntityDamage(this),
                new PlayerDeath(this),
                new PlayerLogin(this),
                new PlayerInteract(this),
                new EntityPickupItem(this)
        );

        registerCommands(
                new ToolsCommand(this),
                new WhoisCommand(this),
                new WorkbenchCommand(this),
                new ReloadCommand(this),
                new ListCommand(this),
                new HealCommand(this),
                new GodCommand(this),
                new GamemodeCommand(this),
                new FlyCommand(this),
                new FeedCommand(this),
                new EnderchestCommand(this),
                new DisableCommand(this),
                new ClearCommand(this),
                new BroadcastCommand(this),
                new BackCommand(this),
                new BanCommand(this),
                new UnbanCommand(this),
                new AfkCommand(this),
                new HatCommand(this),
                new SkullCommand(this),
                new TpCommand(this),
                new TpHereCommand(this),
                new TpPosCommand(this),
                new DayCommand(this),
                new NightCommand(this),
                new RepairCommand(this),
                new KickCommand(this),
                new KickAllCommand(this),
                new DayCommand(this),
                new NightCommand(this)
        );

    }

    @Override
    public void onDisable() {
        this.pluginConfig.save();
        this.messages.save();
        this.banResource.save();
    }

    private void registerCommands(Object... commands) {
        BukkitCommands bukkitCommands = new BukkitCommands(this);

        for (Object object : commands) {

            for (Method method : object.getClass().getMethods()) {

                if (method.isAnnotationPresent(Command.class)) {
                    Command commandAnnotation = method.getAnnotation(Command.class);

                    if (getConfiguration().containsBlockedCommands(commandAnnotation.name())) {
                        continue;
                    }

                    bukkitCommands.registerCommands(object);
                }

            }
        }
    }

    private void registerListeners(Listener... listeners) {

        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void setUpStorage() {
        this.storage = new StorageConnector(this, DatabaseType.MYSQL).getStorage();
    }

    @Override
    public PluginConfig getConfiguration() {
        return pluginConfig;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public UserManager getUserManager() {
        return userManager;
    }

    @Override
    public Messages getMessages() {
        return messages;
    }

    @Override
    public BanResourceManager getBanResource() {
        return banResource;
    }

    public static Tools getInstance() {
        return instance;
    }
}
