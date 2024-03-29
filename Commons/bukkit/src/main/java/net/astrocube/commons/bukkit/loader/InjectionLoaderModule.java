package net.astrocube.commons.bukkit.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.bukkit.authentication.AuthenticationModule;
import net.astrocube.commons.bukkit.core.BukkitConfigurationModule;
import net.astrocube.commons.bukkit.friend.FriendsModule;
import net.astrocube.commons.bukkit.game.GameModule;
import net.astrocube.commons.bukkit.server.ServerModule;
import net.astrocube.commons.bukkit.session.BukkitSessionModule;
import net.astrocube.commons.bukkit.teleport.TeleportModule;
import net.astrocube.commons.bukkit.translation.TranslationModule;
import net.astrocube.commons.bukkit.user.UserModule;
import net.astrocube.commons.bukkit.virtual.BukkitModelModule;
import net.astrocube.commons.core.CommonsModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new BukkitModelModule());
        install(new FriendsModule());
        install(new TeleportModule());
        install(new CommonsModule());
        install(new AuthenticationModule());
        install(new LoaderModule());
        install(new ServerModule());
        install(new BukkitSessionModule());
        install(new GameModule());
        install(new BukkitConfigurationModule());
        install(new TranslationModule());
        install(new UserModule());
    }
}
