package net.astrocube.commons.core.command;

import com.google.inject.Inject;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Optional;
import me.yushust.message.core.MessageProvider;
import net.astrocube.api.core.friend.FriendshipHandler;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.friend.Friendship;
import net.astrocube.commons.core.utils.Callbacks;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

@ACommand(names = {"friends", "friend", "f"})
public class FriendsCommand implements CommandClass {

    private static final int FRIENDS_PER_PAGE = 10;
    private @Inject MessageProvider<Player> messageProvider;
    private @Inject FriendshipHandler friendshipHandler;

    @ACommand(names = {"", "help"})
    public boolean onCommand(@Injected(true) Player player) {
        for (String message : messageProvider.getMessages(player, "friend-help").getContents()) {
            player.sendMessage(message);
        }
        return true;
    }

    @ACommand(names = "list")
    public boolean onListCommand(@Injected(true) Player player, @Optional Integer providedPage) {
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
                        player.sendMessage(
                                messageProvider.getMessage(player, "friend-list.element")
                                    .replace("%%friend_name%%", "")
                                    .replace("%%status%%", "")
                        );
                    }
                    // TODO: Create a util to center this messages
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
