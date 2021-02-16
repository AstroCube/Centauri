package net.astrocube.commons.bukkit.channel.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.channel.admin.StaffMentionParser;
import net.astrocube.api.bukkit.channel.admin.StaffMessageDelivery;
import net.astrocube.api.bukkit.channel.admin.StaffMessageManager;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
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
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class CoreStaffMessageManager implements StaffMessageManager {

    private @Inject StaffMentionParser mentionParser;
    private @Inject OnlineStaffProvider staffProvider;
    private @Inject StaffMessageDelivery messageDelivery;
    private @Inject ObjectMapper mapper;

    private @Inject QueryService<ChatChannel> queryService;
    private @Inject Plugin plugin;
    private @Inject MessageHandler messageHandler;
    private @Inject CreateService<ChatChannelMessage, ChatChannelMessageDoc.Creation> createService;

    private final Channel<ChatChannelMessage> messageChannel;

    @Inject CoreStaffMessageManager(Messenger messenger) {
        this.messageChannel = messenger.getChannel(ChatChannelMessage.class);
    }

    @Override
    public void sendMessage(String message, User sender, boolean important) {

        Player player = Bukkit.getPlayerByIdentifier(sender.getId());

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
            }).callback(channelResponse -> {

                if (!channelResponse.isSuccessful()) {
                    messageHandler.sendIn(player, AlertModes.ERROR, "channel.admin.error");
                }

                channelResponse.ifSuccessful(formattedMessage -> {

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
                    } catch (Exception ex) {
                        messageHandler.sendIn(player, AlertModes.ERROR, "channel.admin.error");
                        plugin.getLogger().log(Level.SEVERE, "Error while creating staff channel", ex);
                    }

                });

            });
        } catch (Exception ex) {
            messageHandler.sendIn(player, AlertModes.ERROR, "channel.admin.error");
            plugin.getLogger().log(Level.SEVERE, "Error while creating staff channel", ex);
        }
    }
}