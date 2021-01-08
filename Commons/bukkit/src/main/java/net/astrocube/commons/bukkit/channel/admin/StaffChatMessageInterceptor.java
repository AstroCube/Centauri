package net.astrocube.commons.bukkit.channel.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.bukkit.channel.MessageInterceptor;
import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelDoc;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class StaffMessageInterceptor implements MessageInterceptor {

    private @Inject StaffMessageDelivery staffMessageDelivery;

    @Inject StaffMessageInterceptor(
            ObjectMapper mapper,
            QueryService<ChatChannel> channelQueryService,
            CreateService<ChatChannel,
            ChatChannelDoc.Creation> channelCreateService
    ) {
        ObjectNode staffChannelQuery = mapper.createObjectNode();
        staffChannelQuery.put("name", "ac-staff");

        try {
            if (channelQueryService.querySync(staffChannelQuery).getFoundModels().isEmpty()) {
                channelCreateService.createSync(new ChatChannelDoc.Creation() {
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
    }

    @Override
    public void intercept(ChatChannelMessage message) {
        System.out.println("Intercepting message MONDA");
        System.out.println(message.getChannel());
        if (!message.getChannel().equalsIgnoreCase("ac-staff")) return;

        this.staffMessageDelivery.deliver(message);
    }
}