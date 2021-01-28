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
import net.astrocube.commons.bukkit.command.*;
import net.astrocube.commons.bukkit.command.flow.CoreCommandLanguageProvider;
import org.bukkit.plugin.Plugin;

public class CommandLoader implements Loader {

    private @Inject Plugin plugin;
    private @Inject CoreCommandLanguageProvider coreCommandLanguageProvider;
    private @Inject Injector injector;
    private @Inject LoginCommand loginCommand;
    private @Inject RegisterCommand registerCommand;
    private @Inject FriendsCommand friendsCommand;
    private @Inject MatchCommand matchCommand;
    private @Inject AdminChatCommand adminChatCommand;
    private @Inject PlayCommand playCommand;
<<<<<<< HEAD
    private @Inject PunishCommand punishCommand;
    private @Inject PlayerLookCommand playerLookCommand;
    private @Inject AdminCommand adminCommand;
    private @Inject FreezeCommand freezeCommand;
=======
    private @Inject PartyCommand partyCommand;
>>>>>>> 182b4cdfc15025f906cebb11c6854c1819bc1582

    @Override
    public void load() {
        CommandManager commandManager = new BukkitCommandManager(this.plugin.getName());
        commandManager.setTranslator(new DefaultTranslator(this.coreCommandLanguageProvider));

        PartInjector partInjector = new SimplePartInjector();

        partInjector.install(new BukkitModule());
        partInjector.install(new DefaultsModule());

        AnnotatedCommandTreeBuilder treeBuilder = new AnnotatedCommandTreeBuilderImpl(
                new AnnotatedCommandBuilderImpl(partInjector),
                (clazz, parent) -> this.injector.getInstance(clazz)
        );

<<<<<<< HEAD
        commandManager.registerCommands(treeBuilder.fromClass(adminChatCommand));
        commandManager.registerCommands(treeBuilder.fromClass(playCommand));
        commandManager.registerCommands(treeBuilder.fromClass(friendsCommand));
        commandManager.registerCommands(treeBuilder.fromClass(matchCommand));
        commandManager.registerCommands(treeBuilder.fromClass(punishCommand));
        commandManager.registerCommands(treeBuilder.fromClass(playerLookCommand));
        commandManager.registerCommands(treeBuilder.fromClass(adminCommand));
        commandManager.registerCommands(treeBuilder.fromClass(freezeCommand));
=======
        commandManager.registerCommands(treeBuilder.fromClass(this.friendsCommand));
        commandManager.registerCommands(treeBuilder.fromClass(this.matchCommand));
        commandManager.registerCommands(treeBuilder.fromClass(this.adminChatCommand));
        commandManager.registerCommands(treeBuilder.fromClass(this.playCommand));
        commandManager.registerCommands(treeBuilder.fromClass(this.partyCommand));
>>>>>>> 182b4cdfc15025f906cebb11c6854c1819bc1582

        if (this.plugin.getConfig().getBoolean("authentication.enabled")) {
            commandManager.registerCommands(treeBuilder.fromClass(this.loginCommand));
            commandManager.registerCommands(treeBuilder.fromClass(this.registerCommand));
        }
    }
}
