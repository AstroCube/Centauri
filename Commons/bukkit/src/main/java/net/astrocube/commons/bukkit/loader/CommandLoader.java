package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.SimpleCommandManager;
import me.fixeddev.commandflow.bukkit.BukkitAuthorizer;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.command.FriendsCommand;
import net.astrocube.commons.bukkit.command.LoginCommand;
import net.astrocube.commons.bukkit.command.MatchCommand;
import net.astrocube.commons.bukkit.command.builder.InjectionParametricCommandBuilder;
import org.bukkit.plugin.Plugin;

public class CommandLoader implements Loader {

    private @Inject InjectionParametricCommandBuilder commandBuilder;
    private @Inject Plugin plugin;
    private @Inject LoginCommand loginCommand;
    private @Inject FriendsCommand friendsCommand;
    private @Inject MatchCommand matchCommand;

    @Override
    public void load() {


        ParameterProviderRegistry registry = ParameterProviderRegistry.createRegistry();
        registry.installModule(new BukkitModule());

        CommandManager commandManager = new SimpleCommandManager(new BukkitAuthorizer(),
                new BukkitMessenger(),
                registry);
        BukkitCommandManager bukkitManager = new BukkitCommandManager(commandManager, plugin.getName());

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            bukkitManager.registerCommands(commandBuilder.fromClass(loginCommand));
        }

        bukkitManager.registerCommands(commandBuilder.fromClass(friendsCommand));
        bukkitManager.registerCommands(commandBuilder.fromClass(matchCommand));

    }

}
