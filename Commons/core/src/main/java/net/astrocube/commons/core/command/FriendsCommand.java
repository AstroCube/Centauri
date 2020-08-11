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

                    

                })
        );
        return true;
    }

}
