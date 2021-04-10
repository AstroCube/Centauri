package net.astrocube.commons.bukkit.listener.friend;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.bukkit.command.friends.FriendCallbackHelper;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FriendAcceptListener implements Listener {

    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler messageHandler;
    private @Inject FindService<User> findService;
    @EventHandler
    public void onFriendshipAction(FriendshipActionEvent event) {

        if (event.getAction().getActionType() != FriendshipAction.Action.ACCEPT) {
            return;
        }

        FriendshipDoc.Relation friendship = event.getAction().getFriendship();

        Player receiver = Bukkit.getPlayerByIdentifier(friendship.getReceiver());
        Player sender = Bukkit.getPlayerByIdentifier(friendship.getSender());

        if (receiver != null) {
            alertFriendship(receiver, friendship);
        }

        if (sender != null) {
            alertFriendship(sender, friendship);
        }

    }

    private void alertFriendship(Player player, FriendshipDoc.Relation relation) {

        String related = player.getDatabaseIdentifier().equalsIgnoreCase(relation.getSender())
                ? relation.getReceiver() : relation.getReceiver();

        findService.find(related).callback(userResponse ->
                userResponse.ifSuccessful(user -> {

                    TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(
                            player,
                            user
                    );

                    messageHandler.sendReplacingIn(
                            player, AlertModes.MUTED, "friend.request.accept",
                            "%receiver%", flairFormat.getColor() + user.getDisplay()
                    );

                })
        );

    }

}
