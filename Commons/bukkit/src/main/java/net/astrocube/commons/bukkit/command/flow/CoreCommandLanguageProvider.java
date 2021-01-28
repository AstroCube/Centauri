package net.astrocube.commons.bukkit.command.flow;

import com.google.inject.Inject;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.translator.TranslationProvider;

import me.yushust.message.MessageHandler;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoreCommandLanguageProvider implements TranslationProvider {

    private @Inject MessageHandler playerMessageHandler;

    @Override
    public String getTranslation(Namespace namespace, String key) {
        CommandSender commandSender = namespace.getObject(CommandSender.class, BukkitCommandManager.SENDER_NAMESPACE);

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            return playerMessageHandler.get(player, "commands.translation." + key);
        }

        return key;
    }

}