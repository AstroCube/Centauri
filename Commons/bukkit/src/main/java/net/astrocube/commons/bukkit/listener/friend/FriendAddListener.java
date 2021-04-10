package net.astrocube.commons.bukkit.listener.friend;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.friend.FriendshipAction;
import net.astrocube.api.bukkit.friend.FriendshipActionEvent;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;
import net.astrocube.api.core.virtual.user.User;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FriendAddListener implements Listener {

    private @Inject DisplayMatcher displayMatcher;
    private @Inject MessageHandler messageHandler;
    private @Inject FindService<User> findService;

    @EventHandler
    public void onFriendshipAction(FriendshipActionEvent event) {

        if (event.getAction().getActionType() != FriendshipAction.Action.ADD) {
            return;
        }

        if (!(event.getAction().getFriendship() instanceof FriendshipDoc.Complete)) {
            return;
        }

        FriendshipDoc.Creation friendship = (FriendshipDoc.Creation) event.getAction().getFriendship();

        Player receiver = Bukkit.getPlayerByIdentifier(friendship.getReceiver());

        if (receiver == null) {
            return;
        }

        findService.find(friendship.getSender()).callback(senderResponse ->
                senderResponse.ifSuccessful(sender -> {
                    TranslatedFlairFormat flairFormat = displayMatcher.getDisplay(receiver, sender);

                    BaseComponent[] builder = new ComponentBuilder(
                            messageHandler.get(receiver, "friend.request.holder")
                    )
                            .event(new ClickEvent(
                                    ClickEvent.Action.RUN_COMMAND, "/friends accept -v" + sender.getUsername())
                            )
                            .event(
                                    new HoverEvent(
                                            HoverEvent.Action.SHOW_TEXT,
                                            new ComponentBuilder(
                                                    messageHandler.get(receiver, "friend.request.holder")
                                            ).create()
                                    )
                            )
                            .create();


                    messageHandler.sendReplacingIn(
                            receiver, AlertModes.INFO, "friend.request.received",
                            "%sender%", flairFormat.getColor() + sender.getDisplay(),
                            "%holder%", builder
                    );

                })
        );

    }

}
