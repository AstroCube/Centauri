package net.astrocube.commons.bukkit.translation;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.core.MessageProvider;
import me.yushust.message.format.bukkit.BukkitMessageProviders;
import me.yushust.message.format.bukkit.yaml.YamlFileLoader;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TranslationModule extends ProtectedModule {

    @Provides @Singleton
    public MessageProvider<Player> provideMessageProvider(Plugin plugin, CoreLanguageProvider languageProvider) {
        MessageProvider<Player> messageProvider = BukkitMessageProviders.getPlayerMessageProvider(
                plugin, new YamlFileLoader(plugin, plugin.getDataFolder()), languageProvider, "lang_%lang%.yml"
        );
        messageProvider.getInterceptor().addInterceptor(new ColorMessageInterceptor());
        return messageProvider;
    }
}