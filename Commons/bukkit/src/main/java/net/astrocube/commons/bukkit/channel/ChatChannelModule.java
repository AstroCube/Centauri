package net.astrocube.commons.bukkit.channel;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.channel.InterceptorRegistry;
import net.astrocube.commons.bukkit.channel.admin.StaffChannelModule;

public class ChannelModule extends ProtectedModule {

    @Override
    protected void configure() {
        this.bind(InterceptorRegistry.class).to(CoreInterceptorRegistry.class).in(Scopes.SINGLETON);

        install(new StaffChannelModule());
    }
}