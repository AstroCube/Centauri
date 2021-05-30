package net.astrocube.lobby.premium;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumSelectBook;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.upperlevel.spigot.book.BookUtil;

@Singleton
public class CorePremiumSelectBook implements PremiumSelectBook {

	private @Inject MessageHandler messageHandler;
	private @Inject FindService<User> findService;

	@Override
	public void matchDisplay(Player player) {
		findService.find(player.getDatabaseIdentifier()).callback(response -> {

			if (!response.isSuccessful()) {
				messageHandler.sendIn(player, AlertModes.ERROR, "premium.error");
			}

			response.ifSuccessful(user -> {

				if (user.getSession().getAuthorizeMethod() == UserDoc.Session.Authorization.PREMIUM) {
					displayDisable(player);
				} else {
					displayEnable(player);
				}

			});

		});
	}

	@Override
	public void displayEnable(Player player) {

		BookUtil.openPlayer(
			player,
			BookUtil.writtenBook()
				.pages(new BookUtil.PageBuilder()
					.add(messageHandler.get(player, "premium.enable.book"))
					.add(new TextComponent("\n\n"))
					.add(BookUtil.TextBuilder.of(messageHandler.get(player, "premium.enable.button"))
						.onHover(BookUtil.HoverAction.showText(messageHandler.get(player, "premium.enable.hover")))
						.onClick(BookUtil.ClickAction.runCommand("/premium confirm"))
						.color(ChatColor.DARK_GREEN)
						.build())
					.build()
				)
				.build()
		);

	}

	@Override
	public void displayDisable(Player player) {

		BookUtil.openPlayer(
			player,
			BookUtil.writtenBook()
				.pages(new BookUtil.PageBuilder()
					.add(messageHandler.get(player, "premium.disable.book"))
					.add(new TextComponent("\n\n"))
					.add(BookUtil.TextBuilder.of(messageHandler.get(player, "premium.disable.button"))
						.onHover(BookUtil.HoverAction.showText(messageHandler.get(player, "premium.disable.hover")))
						.onClick(BookUtil.ClickAction.runCommand("/premium confirm"))
						.color(ChatColor.RED)
						.build())
					.build()
				)
				.build()
		);

	}

}
