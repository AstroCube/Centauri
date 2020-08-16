package net.astrocube.commons.bukkit.translation;

import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import me.fixeddev.inject.ProtectedModule;
import me.yushust.message.core.MessageProvider;
import org.bukkit.entity.Player;

public class TranslationModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<MessageProvider<Player>>() {}).toProvider(MessageProviderProvider.class).in(Scopes.SINGLETON);
    }

}
