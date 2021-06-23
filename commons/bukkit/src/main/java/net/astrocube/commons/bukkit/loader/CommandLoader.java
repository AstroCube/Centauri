package net.astrocube.commons.bukkit.loader;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.SimplePartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.translator.DefaultTranslator;
import net.astrocube.api.bukkit.command.flow.CoreCommandLanguageProvider;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.commons.bukkit.command.*;
import net.astrocube.commons.bukkit.command.authentication.LoginCommand;
import net.astrocube.commons.bukkit.command.authentication.RegisterCommand;
import net.astrocube.commons.bukkit.command.punishment.PunishmentCommand;
import org.bukkit.plugin.Plugin;

public class CommandLoader implements Loader {

	private @Inject Plugin plugin;
	private @Inject CoreCommandLanguageProvider coreCommandLanguageProvider;
	private @Inject Injector injector;
	private @Inject LoginCommand loginCommand;
	private @Inject RegisterCommand registerCommand;
	private @Inject FriendsCommand friendsCommand;
	private @Inject MatchCommand matchCommand;
	private @Inject LobbyCommand lobbyCommand;
	private @Inject AdminChatCommand adminChatCommand;
	private @Inject PlayCommand playCommand;
	private @Inject PunishCommand punishCommand;
	private @Inject PlayerLookCommand playerLookCommand;
	private @Inject GoToCommand goToCommand;
	private @Inject ShoutCommand shoutCommand;
	private @Inject AdminCommand adminCommand;
	private @Inject FreezeCommand freezeCommand;
	private @Inject PartyCommand partyCommand;
	private @Inject WhisperCommands whisperCommands;
	private @Inject PunishmentCommand punishmentCommand;
	private @Inject FlyCommand flyCommand;
	private @Inject GlobalBroadcastCommand globalBroadcastCommand;

	private @Inject TestCommand testCommand;

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

		registerCommands(
			commandManager, treeBuilder,
			adminChatCommand,
			playCommand,
			friendsCommand,
			matchCommand,
			whisperCommands,
			punishCommand,
			playerLookCommand,
			shoutCommand,
			adminCommand,
			freezeCommand,
			lobbyCommand,
			goToCommand,
			partyCommand,
			punishmentCommand,
			flyCommand,
			testCommand,
			globalBroadcastCommand
		);

		if (this.plugin.getConfig().getBoolean("authentication.enabled")) {
			registerCommands(
				commandManager, treeBuilder,
				loginCommand, registerCommand
			);
		}
	}

	private void registerCommands(
		CommandManager commandManager,
		AnnotatedCommandTreeBuilder treeBuilder,
		CommandClass... commandClasses
	) {
		for (CommandClass commandClass : commandClasses) {
			commandManager.registerCommands(treeBuilder.fromClass(commandClass));
		}
	}

}
