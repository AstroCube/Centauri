package net.astrocube.commons.bukkit.translation;

import com.google.inject.Exposed;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.core.MessageProvider;
import me.yushust.message.core.MessageRepository;
import me.yushust.message.core.ProvideStrategy;
import me.yushust.message.format.bukkit.BukkitMessageAdapt;
import net.astrocube.commons.bukkit.translation.interceptor.CenterMessageInterceptor;
import net.astrocube.commons.bukkit.translation.interceptor.ColorMessageInterceptor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton @Exposed
    public MessageProvider<Player> provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {
        return MessageProvider.<Player>builder()
                .setRepository(
                        MessageRepository.builder()
                                .setDefaultLanguage("es")
                                .setNodeFileLoader(BukkitMessageAdapt.getYamlFileLoader(plugin, null))
                                .setFileFormat("lang_%lang%.yml")
                                .setProvideStrategy(ProvideStrategy.RETURN_PATH)
                                .setLoadSource(BukkitMessageAdapt.getPluginLoadSource(plugin))
                                .build()
                )
                .setLanguageProvider(languageProvider)
                .addInterceptor(new ColorMessageInterceptor())
                .addInterceptor(new CenterMessageInterceptor())
                .setMessageConsumer(CommandSender::sendMessage)
                .build();
    }
}