package net.astrocube.commons.bukkit.translation;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.core.MessageProvider;
import me.yushust.message.core.MessageProviderBuilder;
import me.yushust.message.core.ProvideStrategy;
import me.yushust.message.format.bukkit.BukkitMessageAdapt;
import net.astrocube.commons.bukkit.translation.interceptor.CenterMessageInterceptor;
import net.astrocube.commons.bukkit.translation.interceptor.ColorMessageInterceptor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton
    public MessageProvider<Player> provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {
        return MessageProviderBuilder.<Player>create()
                .setLoadSource(BukkitMessageAdapt.getPluginLoadSource(plugin))
                .setLanguageProvider(languageProvider)
                .setDefaultLanguage("es")
                .setNodeFileLoader(BukkitMessageAdapt.getYamlFileLoader(plugin, null))
                .setFileFormat("lang_%lang%.yml")
                .setMessageConsumer(CommandSender::sendMessage)
                .setProvideStrategy(ProvideStrategy.RETURN_PATH)
                .addInterceptor(new ColorMessageInterceptor())
                .addInterceptor(new CenterMessageInterceptor())
                .build();
    }
}