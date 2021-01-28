package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.yushust.message.MessageHandler;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.logging.Level;

@Command(names = "list")
public class ListSubCommand implements CommandClass {

    private static final int FRIENDS_PER_PAGE = 10;
    private @Inject MessageHandler messageHandler;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FindService<User> userFindService;

    @Command(names = "")
    public boolean execute(@Sender Player player, @OptArg Integer providedPage) {
        int page = providedPage == null ? 0 : providedPage - 1;
        friendshipHandler.paginate(player.getDatabaseIdentifier(), page, FRIENDS_PER_PAGE).callback(
                Callbacks.applyCommonErrorHandler("Paginate friends", paginateResult -> {

                    Set<Friendship> friendships = paginateResult.getData();
                    PaginateResult.Pagination pagination = paginateResult.getPagination();

                    int pageIndicator = pagination.page().orElse(-1);

                    if (friendships.size() == 0 && pageIndicator == -1
                            && !pagination.hasNextPage() && !pagination.hasPrevPage()) {
                        messageHandler.send(player, "no-friends");
                        return;
                    }


                    messageHandler.send(player, "friend-list.header");
                    for (Friendship friendship : friendships) {

                        String id = friendship.getIssuer();

                        if (player.getDatabaseIdentifier().equals(id)) {
                            id = friendship.getReceiver();
                        }

                        User user;

                        try {
                            user = userFindService.findSync(id);
                        } catch (Exception e) {
                            Bukkit.getLogger().log(Level.SEVERE, "[Centauri] Failed to get friends of " + player.getName(), e);
                            continue;
                        }

                        if (user == null) {
                            continue;
                        }

                        player.sendMessage(
                                messageHandler.get(player, "friend-list.element")
                                        .replace("%%friend_name%%", user.getUsername())
                                        .replace("%%status%%", messageHandler.get(player,
                                                user.getSession().isOnline() ? "online" : "offline"
                                        ))
                        );
                    }

                    TextComponent clickableComponents = new TextComponent();

                    if (pagination.hasPrevPage()) {
                        String message = messageHandler.get(player, "friends-previous-page");
                        clickableComponents.setText(message);
                        clickableComponents.setHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                TextComponent.fromLegacyText(message)
                        ));
                    }

                    if (pagination.hasNextPage()) {
                        clickableComponents.addExtra("        "); // just add space to separate that shit
                        String message = messageHandler.get(player, "friends-next-page");
                        TextComponent component = new TextComponent(message);
                        component.setHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                TextComponent.fromLegacyText(message)
                        ));
                        clickableComponents.addExtra(component);
                    }

                    player.sendMessage(clickableComponents);

                })
        );
        return true;
    }

}
