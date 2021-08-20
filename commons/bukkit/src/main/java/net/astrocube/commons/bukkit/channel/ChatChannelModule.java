package net.astrocube.commons.bukkit.channel;

import com.google.inject.Scopes;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.channel.InterceptorRegistry;
import net.astrocube.api.bukkit.channel.admin.StaffLoginBroadcaster;
import net.astrocube.commons.bukkit.channel.admin.StaffChannelModule;

public class ChatChannelModule extends ProtectedModule {

	@Override
	protected void configure() {

		this.bind(StaffLoginBroadcaster.class).to(CoreStaffLoginBroadcaster.class);
		this.bind(InterceptorRegistry.class).to(CoreInterceptorRegistry.class).in(Scopes.SINGLETON);

		install(new StaffChannelModule());
	}
}