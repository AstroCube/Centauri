package net.astrocube.commons.bukkit.translation;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.core.MessageProvider;
import me.yushust.message.core.MessageProviderBuilder;
import me.yushust.message.core.ProvideStrategy;
import me.yushust.message.format.bukkit.BukkitMessageProviders;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton
    public MessageProvider<Player> provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {
        return MessageProviderBuilder.<Player>create()
                .withLoadSource(BukkitMessageProviders.createPluginLoadSource(plugin))
                .usingLanguageProvider(languageProvider)
                .defaultLanguage("es")
                .nodeFileLoader(BukkitMessageProviders.createYamlFileLoader(plugin))
                .fileFormat("lang_%lang%.yml")
                .withMessageConsumer(CommandSender::sendMessage)
                .provideStrategy(ProvideStrategy.RETURN_PATH)
                .addInterceptor(new ColorMessageInterceptor())
                .build();
    }
}