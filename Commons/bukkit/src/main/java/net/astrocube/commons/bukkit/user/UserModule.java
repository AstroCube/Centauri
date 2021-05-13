package net.astrocube.commons.bukkit.user;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.user.UserMatcher;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedGroupProvider;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.core.message.ChannelBinder;
import net.astrocube.api.core.session.SessionPingMessage;
import net.astrocube.commons.bukkit.user.display.CoreDisplayMatcher;
import net.astrocube.commons.bukkit.user.display.CoreTranslatedGroupProvider;
import net.astrocube.commons.bukkit.user.skin.SkinModule;
import net.astrocube.commons.bukkit.user.staff.CoreOnlineStaffProvider;

public class UserModule extends ProtectedModule implements ChannelBinder {

	@Override
	protected void configure() {

		install(new SkinModule());

		bind(TranslatedGroupProvider.class).to(CoreTranslatedGroupProvider.class);
		bind(DisplayMatcher.class).to(CoreDisplayMatcher.class).in(Scopes.SINGLETON);
		bind(UserMatcher.class).to(CoreUserMatcher.class).in(Scopes.SINGLETON);
		bind(OnlineStaffProvider.class).to(CoreOnlineStaffProvider.class).in(Scopes.SINGLETON);
		bindChannel(SessionPingMessage.class);

		expose(TranslatedGroupProvider.class);
		expose(DisplayMatcher.class);
		expose(UserMatcher.class);
		expose(OnlineStaffProvider.class);
	}
}