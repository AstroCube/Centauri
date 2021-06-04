package net.astrocube.commons.bukkit.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.*;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.admin.chat.StaffChatOptionsMenu;
import net.astrocube.api.bukkit.channel.admin.StaffMessageManager;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.utils.MessageUtils;
import net.kyori.text.TextComponent;
import net.kyori.text.event.ClickEvent;
import net.kyori.text.event.HoverEvent;
import net.kyori.text.format.TextColor;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class AdminChatCommand implements CommandClass {

	private @Inject FindService<User> findService;
	private @Inject MessageHandler messageHandler;
	private @Inject StaffMessageManager messageManager;
	private @Inject StaffChatOptionsMenu staffChatOptionsMenu;

	@Command(names = {"acs"}, permission = "commons.staff.chat")
	public boolean onSettingsCommand(@Sender Player player) {
		staffChatOptionsMenu.generateMenu(player);
		return true;
	}

	@Command(names = {"adminchat", "ac", "staffchat", "sc"}, permission = "commons.staff.chat")
	public boolean onCommand(
		@Sender Player player,
		@Switch("i") @Named("important") Boolean important,
		@Named("message") @OptArg @Text String message
	) {
		if (message == null) {
			//TODO Change user's channel to staff
			return true;
		}

		this.findService.find(player.getDatabaseIdentifier()).callback(userResponse -> {

			if (!userResponse.isSuccessful()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "channel.admin.error");
			}

			userResponse.ifSuccessful(user -> {

				if (important && !player.hasPermission("commons.staff.chat.important")) {
					this.messageHandler.sendIn(player, AlertModes.ERROR, "staff.chat.permission");
					return;
				}

				if (!user.getSettings().getAdminChatSettings().isActive()) {
					TextComponent disabledComponent = TextComponent.builder()
						.content(this.messageHandler.get(player, "staff.chat.disabled" + ". "))
						.color(TextColor.RED)
						.build();

					TextComponent hoverComponent = TextComponent.builder()
						.content(this.messageHandler.get(player, "staff.chat.reminder-click"))
						.color(TextColor.YELLOW)
						.clickEvent(ClickEvent.runCommand("/acs"))
						.hoverEvent(
							HoverEvent.showText(TextComponent.of(this.messageHandler.get(player, "staff.chat.reminder-click"))
								.color(TextColor.YELLOW)
							)
						)
						.build();

					player.sendMessage(MessageUtils.kyoriToBungee(disabledComponent.append(hoverComponent)));
					return;
				}

				this.messageManager.sendMessage(message, user, important);

			});

		});

		return true;
	}
}