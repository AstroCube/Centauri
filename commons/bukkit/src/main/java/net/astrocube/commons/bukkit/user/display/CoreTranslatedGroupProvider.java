package net.astrocube.commons.bukkit.user.display;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.bukkit.user.display.TranslatedGroupProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Singleton
public class CoreTranslatedGroupProvider implements TranslatedGroupProvider {

	private @Inject MessageHandler messageHandler;

	@Override
	public TranslatedFlairFormat getGroupTranslations(Player player, String id, ChatColor color) {
		return new TranslatedFlairFormat() {
			@Override
			public String getId() {
				return id;
			}

			@Override
			public ChatColor getColor() {
				return color;
			}

			@Override
			public String getName() {
				String name = messageHandler.get(player, "groups." + id + ".name");
				return name.equalsIgnoreCase("groups." + id + ".name") ?
					messageHandler.get(player, "groups.default.name") : name;
			}

			;

			@Override
			public String getPrefix() {
				String prefix = messageHandler.get(player, "groups." + id + ".prefix");
				return prefix.equalsIgnoreCase("groups." + id + ".prefix") ?
					messageHandler.get(player, "groups.default.prefix") : prefix;
			}

			@Override
			public String getJoinMessage() {
				String joinMessage = messageHandler.get(player, "groups." + id + ".message.join");
				return joinMessage.equalsIgnoreCase("groups." + id + ".message.join")
					? messageHandler.get(player, "groups.default.message.join") : joinMessage;
			}

			@Override
			public String getLeaveMessage() {
				String leaveMessage = messageHandler.get(player, "groups." + id + ".message.leave");
				return leaveMessage.equalsIgnoreCase("groups." + id + ".message.leave")
					? messageHandler.get(player, "groups.default.message.leave") : leaveMessage;
			}
		};
	}

}
