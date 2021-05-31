package net.astrocube.commons.bukkit.game.channel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.channel.MatchChannelProvider;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelDoc;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.delete.DeleteService;
import net.astrocube.api.core.service.find.FindService;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;

@Singleton
public class CoreMatchChannelProvider implements MatchChannelProvider {

	private @Inject CreateService<ChatChannel, ChatChannelDoc.Creation> createService;
	private @Inject FindService<ChatChannel> findService;
	private @Inject DeleteService<ChatChannel> deleteService;
	private @Inject Plugin plugin;

	private final Map<String, String> assignation = new HashMap<>();

	@Override
	public void createChannel(String match) throws Exception {

		ChatChannel channel = createService.createSync(new ChatChannelDoc.Creation() {
			@Override
			public String getName() {
				return "match_" + match;
			}

			@Override
			public int getLifecycle() {
				return -1;
			}

			@Override
			public boolean getConfirmation() {
				return false;
			}

			@Override
			public Visibility getVisibility() {
				return Visibility.PUBLIC;
			}

			@Override
			public Set<String> getParticipants() {
				return new HashSet<>();
			}

			@Nullable
			@Override
			public String getPermission() {
				return "";
			}
		});

		assignation.put(match, channel.getId());
	}

	@Override
	public Optional<ChatChannel> retrieveChannel(String match) {
		try {
			return Optional.of(findService.findSync(assignation.get(match)));
		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "There was an error retrieving chat channel.", e);
			return Optional.empty();
		}
	}

	@Override
	public void unlinkChannel(String match) {
		deleteService.delete(assignation.get(match));
		assignation.remove(match);
	}
}
