package net.astrocube.commons.bukkit.channel.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.bukkit.channel.admin.StaffMentionParser;
import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.channel.admin.StaffMessageManager;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.api.bukkit.virtual.channel.ChatChannel;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessage;
import net.astrocube.api.bukkit.virtual.channel.ChatChannelMessageDoc;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CoreStaffMessageManager implements StaffMessageManager {

    private @Inject StaffMentionParser mentionParser;
    private @Inject OnlineStaffProvider staffProvider;
    private @Inject StaffMessageDelivery messageDelivery;
    private @Inject ObjectMapper mapper;

    private @Inject QueryService<ChatChannel> queryService;
    private @Inject CreateService<ChatChannelMessage, ChatChannelMessageDoc.Creation> createService;

    private final Channel<ChatChannelMessage> messageChannel;

    @Inject CoreStaffMessageManager(Messenger messenger) {
        this.messageChannel = messenger.getChannel(ChatChannelMessage.class);
    }

    @Override
    public void sendMessage(String message, User sender, boolean important) {
        try {
            Set<User> onlineStaff = this.staffProvider.provide();
            Set<String> mentionedUsers = this.mentionParser.parseMentions(message)
                    .stream()
                    .filter(mention -> {
                        for (User staff : onlineStaff)
                            if (
                                    staff.getDisplay().equalsIgnoreCase(mention) &&
                                            !staff.getId().equalsIgnoreCase(sender.getId()) // Comment this line to allow own-mention
                            ) {
                                return true;
                            }
                        return false;
                    }).map(mention -> {
                        for (User staff : onlineStaff) {
                            if (staff.getDisplay().equalsIgnoreCase(mention)) return staff.getDisplay();
                        }
                        return mention;
                    }).collect(Collectors.toSet());

            this.createService.create(new ChatChannelMessageDoc.Creation() {
                @Override
                public String getSender() {
                    return sender.getId();
                }

                @Override
                public String getMessage() {
                    return message;
                }

                @Override
                public String getChannel() {
                    return "ac-staff";
                }

                @Override
                public Origin getOrigin() {
                    return Origin.INGAME;
                }

                @Override
                public Map<String, Object> getMeta() {
                    Map<String, Object> meta = new LinkedHashMap<>();
                    meta.put("important", important);
                    meta.put("mentions", mentionedUsers);

                    return meta;
                }
            }).callback(Callbacks.applyCommonErrorHandler("ChannelMessage creation", formattedMessage -> {
                try {
                    this.messageDelivery.deliver(formattedMessage);
                    ObjectNode staffChannelQuery = this.mapper.createObjectNode();
                    staffChannelQuery.put("name", "ac-staff");

                    Map<String, Object> headers = new HashMap<>();
                    headers.put(
                            "channel",
                            this.mapper.writeValueAsString(this.queryService.querySync(staffChannelQuery).getFoundModels().stream()
                                    .findAny()
                                    .orElseThrow(() -> new IllegalArgumentException("Staff channel should exist"))
                            )
                    );
                    this.messageChannel.sendMessage(formattedMessage, headers);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}