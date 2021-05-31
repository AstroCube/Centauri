package net.astrocube.commons.bukkit.channel.admin;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.channel.admin.StaffMentionParser;
import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.channel.admin.StaffMessageManager;

public class StaffChannelModule extends ProtectedModule {

	@Override
	protected void configure() {
		this.bind(StaffMessageManager.class).to(CoreStaffMessageManager.class).in(Scopes.SINGLETON);
		this.bind(StaffMessageDelivery.class).to(CoreStaffMessageDelivery.class).in(Scopes.SINGLETON);
		this.bind(StaffMentionParser.class).to(CoreStaffMentionParser.class).in(Scopes.SINGLETON);
	}
}