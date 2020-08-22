package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import me.fixeddev.ebcm.CommandManager;
import me.fixeddev.ebcm.SimpleCommandManager;
import me.fixeddev.ebcm.bukkit.BukkitAuthorizer;
import me.fixeddev.ebcm.bukkit.BukkitCommandManager;
import me.fixeddev.ebcm.bukkit.BukkitMessenger;
import me.fixeddev.ebcm.bukkit.parameter.provider.BukkitModule;
import me.fixeddev.ebcm.parameter.provider.ParameterProviderRegistry;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.ReflectionParametricCommandBuilder;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.command.LoginCommand;
import org.bukkit.plugin.Plugin;

public class CommandLoader implements Loader {

    private @Inject Plugin plugin;

    private @Inject LoginCommand loginCommand;

    @Override
    public void load() {

        ParameterProviderRegistry registry = ParameterProviderRegistry.createRegistry();
        ParametricCommandBuilder commandBuilder = new ReflectionParametricCommandBuilder();
        registry.installModule(new BukkitModule());

        CommandManager commandManager = new SimpleCommandManager(new BukkitAuthorizer(),
                new BukkitMessenger(),
                registry);
        BukkitCommandManager bukkitManager = new BukkitCommandManager(commandManager, plugin.getName());

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            bukkitManager.registerCommands(commandBuilder.fromClass(loginCommand));
        }

    }

}
