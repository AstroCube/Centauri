package net.astrocube.api.bukkit.translation;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.MessageHandler;
import me.yushust.message.MessageRepository;
import me.yushust.message.format.bukkit.BukkitMessageAdapt;
import me.yushust.message.format.bukkit.language.SpigotLanguageProvider;
import me.yushust.message.strategy.Strategy;
import net.astrocube.api.bukkit.translation.mode.CoreMessenger;
import net.astrocube.api.bukkit.translation.interceptor.CenterMessageInterceptor;
import net.astrocube.api.bukkit.translation.interceptor.ColorMessageInterceptor;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton
    public MessageHandler provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {
        return MessageHandler.create(
                MessageRepository.builder()
                        .setDefaultLanguage("es")
                        .setNodeFileLoader(BukkitMessageAdapt.getYamlFileLoader(plugin))
                        .setFileFormat("lang_%lang%.yml")
                        .setStrategy(new Strategy())
                        .setLoadSource(BukkitMessageAdapt.getPluginLoadSource(plugin))
                        .build(),
                wireHandle -> {
                    wireHandle.specify(Player.class)
                            .setLinguist(languageProvider)
                            .setMessenger(new CoreMessenger());
                    wireHandle.delimiting("%", "%");
                    wireHandle.intercept(new ColorMessageInterceptor());
                    wireHandle.intercept(new CenterMessageInterceptor());
                }
        );
    }
}
