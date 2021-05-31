package net.astrocube.commons.bukkit.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.bukkit.channel.InterceptorRegistry;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelDoc;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.commons.bukkit.channel.admin.StaffChatMessageInterceptor;
import net.astrocube.commons.bukkit.game.channel.MatchChannelInterceptor;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class ChannelLoader implements Loader {

	private @Inject InterceptorRegistry interceptorRegistry;
	private @Inject StaffChatMessageInterceptor staffMessageInterceptor;
	private @Inject MatchChannelInterceptor matchChannelInterceptor;

	private @Inject ObjectMapper mapper;
	private @Inject QueryService<ChatChannel> channelQueryService;
	private @Inject CreateService<ChatChannel, ChatChannelDoc.Creation> channelCreateService;

	@Override
	public void load() {
		ObjectNode staffChannelQuery = this.mapper.createObjectNode();
		staffChannelQuery.put("name", "ac-staff");

		try {
			if (this.channelQueryService.querySync(staffChannelQuery).getFoundModels().isEmpty()) {
				this.channelCreateService.createSync(new ChatChannelDoc.Creation() {
					@Override
					public String getName() {
						return "ac-staff";
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
						return Visibility.PERMISSION;
					}

					@Override
					public Set<String> getParticipants() {
						return new HashSet<>();
					}

					@Override
					public String getPermission() {
						return "commons.staff.chat";
					}
				});
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		this.interceptorRegistry.register(this.staffMessageInterceptor);
		this.interceptorRegistry.register(this.matchChannelInterceptor);
	}
}