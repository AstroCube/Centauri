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
import java.util.Set;

public class PlayerLookCommand implements CommandClass {

    @Inject
    private FindService<User> userFindService;
    @Inject
    private MessageHandler messageHandler;
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

        StringBuilder punishmentBuilder = new StringBuilder();
        Set<Punishment> punishments = queryService.querySync(objectNode).getFoundModels();

        if (punishments.isEmpty()) {
            messageHandler.send(player, "punishment-expiration-menu.empty");
            return true;
        }

        punishments.forEach(punishment -> punishmentBuilder.append(messageHandler.get(player, "lookup.punishments-format")
                .replace("%punishment_type%", punishment.getType().toString())
                .replace("%punishment_pusher%", punishment.getIssuer())
                .replace("%punishment_created_at%", punishment.getCreatedAt().toString())
                .replace("%punishment_reason%", punishment.getReason())));

        messageHandler.sendReplacing(player,
                "lookup.information-message",
                "%player_name%", offlineTarget.getName(),
                "%player_country%", targetUser.getPublicInfo().getLocation(),
                "%player_last_seen%", targetUser.getSession().getLastSeen(),
                "%player_ip%", offlineTarget.getPlayer().getAddress().getAddress().getHostAddress(),
                "%player_punishments%", punishmentBuilder.toString());

        return true;
    }
}