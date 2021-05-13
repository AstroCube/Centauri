package net.astrocube.commons.bukkit.loader.listener;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.server.ListenerLoader;
import net.astrocube.commons.bukkit.listener.punishment.PunishmentIssueActionsListener;
import net.astrocube.commons.bukkit.listener.punishment.PunishmentStaffChatListener;
import net.astrocube.commons.bukkit.listener.session.StaffSessionLogListener;
import org.bukkit.plugin.Plugin;

public class StaffListenerLoader implements ListenerLoader {

	private @Inject Plugin plugin;

	private @Inject StaffSessionLogListener staffSessionLogListener;

	private @Inject PunishmentIssueActionsListener punishmentIssueActionsListener;
	private @Inject PunishmentStaffChatListener punishmentStaffChatListener;

	@Override
	public void registerEvents() {
		registerEvent(
			plugin,
			staffSessionLogListener,
			punishmentIssueActionsListener,
			punishmentStaffChatListener
		);
	}
}
