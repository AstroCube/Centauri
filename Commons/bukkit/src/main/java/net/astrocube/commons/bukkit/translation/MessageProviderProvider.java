package net.astrocube.commons.bukkit.translation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import me.yushust.message.core.MessageProvider;
import me.yushust.message.format.bukkit.BukkitMessageProviders;
import me.yushust.message.format.bukkit.yaml.YamlFileLoader;
import net.astrocube.commons.bukkit.translation.holder.PlayerPlaceholderApplier;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class MessageProviderProvider implements Provider<MessageProvider<Player>> {

    private @Inject Plugin plugin;
    private @Inject CoreLanguageProvider languageProvider;

    @Override
    public MessageProvider<Player> get() {
        MessageProvider<Player> messageProvider = BukkitMessageProviders.getPlayerMessageProvider(
                plugin, new YamlFileLoader(plugin, plugin.getDataFolder()), languageProvider, "lang_%lang%.yml"
        );
        messageProvider.getInterceptor().addInterceptor(new ColorMessageInterceptor());
        messageProvider.getInterceptor().addPlaceholderApplier(new PlayerPlaceholderApplier());
        return messageProvider;
    }

}
