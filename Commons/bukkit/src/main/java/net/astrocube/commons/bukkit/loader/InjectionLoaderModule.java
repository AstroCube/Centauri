package net.astrocube.commons.bukkit.loader;

import com.google.inject.Exposed;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.teleport.ServerTeleportRetry;
import net.astrocube.api.bukkit.translation.TranslationModule;
import net.astrocube.commons.bukkit.authentication.AuthenticationModule;
import net.astrocube.commons.bukkit.board.ScoreboardModule;
import net.astrocube.commons.bukkit.channel.ChatChannelModule;
import net.astrocube.commons.bukkit.cloud.CloudModule;
import net.astrocube.commons.bukkit.core.BukkitConfigurationModule;
import net.astrocube.commons.bukkit.friend.FriendsModule;
import net.astrocube.commons.bukkit.game.GameModule;
import net.astrocube.commons.bukkit.menu.MenuModule;
import net.astrocube.commons.bukkit.nametag.NametagModule;
import net.astrocube.commons.bukkit.party.PartyModule;
import net.astrocube.commons.bukkit.perk.PerkModule;
import net.astrocube.commons.bukkit.punishment.PunishmentModule;
import net.astrocube.commons.bukkit.server.ServerModule;
import net.astrocube.commons.bukkit.session.BukkitSessionModule;
import net.astrocube.commons.bukkit.tablist.TablistModule;
import net.astrocube.commons.bukkit.teleport.CoreServerTeleportRetry;
import net.astrocube.commons.bukkit.user.UserModule;
import net.astrocube.commons.bukkit.virtual.BukkitVirtualModule;
import net.astrocube.commons.bukkit.whisper.WhisperModule;
import net.astrocube.commons.core.CommonsModule;
import net.astrocube.puppets.entity.CorePuppetRegistry;
import net.astrocube.puppets.entity.PuppetRegistry;
import net.astrocube.puppets.packet.PacketHandler;
import net.astrocube.puppets.packet.PuppetClickPacket;
import org.bukkit.plugin.Plugin;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new BukkitVirtualModule());
        install(new FriendsModule());
        install(new CloudModule());
        install(new PerkModule());
        install(new CommonsModule());
        install(new AuthenticationModule());
        install(new ScoreboardModule());
        install(new LoaderModule());
        install(new ServerModule());
        install(new WhisperModule());
        install(new BukkitSessionModule());
        install(new GameModule());
        install(new BukkitConfigurationModule());
        install(new TranslationModule());
        install(new UserModule());
        install(new ChatChannelModule());
        install(new NametagModule());
        install(new TablistModule());
        install(new MenuModule());
        install(new PartyModule());
        install(new PunishmentModule());

        bind(ServerTeleportRetry.class).to(CoreServerTeleportRetry.class);
        expose(ServerTeleportRetry.class);
    }

    @Provides @Singleton @Named("puppet")
    public PacketHandler providePuppetHandler(PuppetRegistry registry, Plugin plugin) {
        return new PuppetClickPacket(registry, plugin);
    }

    @Provides @Singleton @Exposed
    public PuppetRegistry providePuppetRegistry() {
        return new CorePuppetRegistry();
    }

}
