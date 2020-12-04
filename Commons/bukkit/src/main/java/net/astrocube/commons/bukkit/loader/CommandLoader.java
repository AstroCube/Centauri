package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import com.google.inject.Injector;

import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.SimplePartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.translator.DefaultTranslator;

import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.command.FriendsCommand;
import net.astrocube.commons.bukkit.command.LoginCommand;
import net.astrocube.commons.bukkit.command.MatchCommand;
import net.astrocube.commons.bukkit.command.flow.CoreCommandLanguageProvider;

import org.bukkit.plugin.Plugin;

public class CommandLoader implements Loader {

    private @Inject Plugin plugin;
    private @Inject CoreCommandLanguageProvider coreCommandLanguageProvider;
    private @Inject Injector injector;
    private @Inject LoginCommand loginCommand;
    private @Inject FriendsCommand friendsCommand;
    private @Inject MatchCommand matchCommand;

    @Override
    public void load() {

        CommandManager commandManager = new BukkitCommandManager(plugin.getName());
        commandManager.setTranslator(new DefaultTranslator(coreCommandLanguageProvider));

        PartInjector partInjector = new SimplePartInjector();

        partInjector.install(new BukkitModule());
        partInjector.install(new DefaultsModule());

        AnnotatedCommandTreeBuilder treeBuilder = new AnnotatedCommandTreeBuilderImpl(
                new AnnotatedCommandBuilderImpl(partInjector),
                (clazz, parent) -> injector.getInstance(clazz)
        );

        commandManager.registerCommands(treeBuilder.fromClass(friendsCommand));
        commandManager.registerCommands(treeBuilder.fromClass(matchCommand));

        if (plugin.getConfig().getBoolean("authentication.enabled")) {
            commandManager.registerCommands(treeBuilder.fromClass(loginCommand));
        }

    }

}
