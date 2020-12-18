package net.astrocube.commons.bukkit.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class PlayerLookCommand implements CommandClass {

    @Inject
    private FindService<User> userFindService;
    @Inject
    private MessageHandler<Player> messageHandler;
    @Inject
    private QueryService<Punishment> queryService;
    @Inject
    private ObjectMapper objectMapper;

    @Command(names = {"look", "lookup"})
    public boolean lookupCommand(@Sender Player player, OfflinePlayer offlineTarget) throws Exception {

        if (offlineTarget.getPlayer() == null) {
            messageHandler.send(player, "offline");
            return true;
        }

        User targetUser = userFindService.findSync(offlineTarget.getPlayer().getDatabaseIdentifier());

        ObjectNode objectNode = objectMapper.createObjectNode()
                .put("username", offlineTarget.getName());

        StringBuilder stringBuilder = new StringBuilder();

        // TODO: 18/12/2020 Make this configurable

        queryService.querySync(objectNode).getFoundModels().forEach(punishment ->
                stringBuilder.append("Type: ").append(punishment.getType()).append("\n")
                .append("Reason: ").append(punishment.getReason()).append("\n")
                .append("Applied: ").append(punishment.getCreatedAt()));

        messageHandler.sendReplacing(player,
                "lookup.information-message",
                "%player_name%", offlineTarget.getName(),
                "%player_country%", targetUser.getPublicInfo().getLocation(),
                "%player_last_seen%", targetUser.getSession().getLastSeen(),
                "%player_ip%", offlineTarget.getPlayer().getAddress().getAddress().getHostAddress(),
                "%player_punishments%", stringBuilder.toString());

        return true;
    }
}