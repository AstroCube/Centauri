package net.astrocube.api.bukkit.friend;

import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.virtual.friend.FriendshipDoc;

public interface FriendshipAction extends Message {

    /**
     * @return friendship.
     */
    FriendshipDoc.Relation getFriendship();

    /**
     * @return action type related to the friendship.
     */
    Action getActionType();

    enum Action {
        ADD, ACCEPT, FORCE
    }

}
