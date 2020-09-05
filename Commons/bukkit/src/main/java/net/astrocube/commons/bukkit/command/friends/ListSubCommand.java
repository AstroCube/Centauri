package net.astrocube.commons.bukkit.command.friends;

import com.google.inject.Inject;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Optional;
import me.yushust.message.core.MessageProvider;
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

import java.util.logging.Level;

@ACommand(names = "list")
public class ListSubCommand implements CommandClass {

    private static final int FRIENDS_PER_PAGE = 10;
    private @Inject MessageProvider<Player> messageProvider;
    private @Inject FriendshipHandler friendshipHandler;
    private @Inject FindService<User> userFindService;

    @ACommand(names = "")
    public boolean execute(@Injected(true) @Sender Player player, @Optional Integer providedPage) {
        int page = providedPage == null ? 0 : providedPage - 1;
        friendshipHandler.paginate(player.getDatabaseIdentifier(), page, FRIENDS_PER_PAGE).callback(
                Callbacks.applyCommonErrorHandler("Paginate friends", paginateResult -> {

                    Friendship[] friendships = paginateResult.getData();
                    PaginateResult.Pagination pagination = paginateResult.getPagination();

                    int pageIndicator = pagination.page().orElse(-1);

                    if (friendships.length == 0 && pageIndicator == -1
                            && !pagination.hasNextPage() && !pagination.hasPrevPage()) {
                        player.sendMessage(messageProvider.getMessage(player, "no-friends"));
                        return;
                    }


                    player.sendMessage(messageProvider.getMessage(player, "friend-list.header"));
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
                                messageProvider.getMessage(player, "friend-list.element")
                                        .replace("%%friend_name%%", user.getUsername())
                                        .replace("%%status%%", messageProvider.getMessage(player,
                                                user.getSession().isOnline() ? "online" : "offline"
                                        ))
                        );
                    }

                    TextComponent clickableComponents = new TextComponent();

                    if (pagination.hasPrevPage()) {
                        String message = messageProvider.getMessage(player, "friends-previous-page");
                        clickableComponents.setText(message);
                        clickableComponents.setHoverEvent(new HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                TextComponent.fromLegacyText(message)
                        ));
                    }

                    if (pagination.hasNextPage()) {
                        clickableComponents.addExtra("        "); // just add space to separate that shit
                        String message = messageProvider.getMessage(player, "friends-next-page");
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
