package net.astrocube.api.bukkit.party;

import net.astrocube.api.core.virtual.party.Party;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

/**
 * Service of {@link Party} handle
 * that contains a set of util methods
 * for handling the parties
 */
public interface PartyService {

	/**
	 * Removes the invite of the specified {@code playerName}
	 */
	void removeInvite(String playerName);

	void cleanupInvitations(Player player);

	/**
	 * Fetches the current invite for the specified {@code playerName}
	 *
	 * @param playerName The player that you're checking for
	 * @return The current inviter id of the user
	 */
	Set<String> getInvitations(String playerName);

	/**
	 * Handles the invitation made by {@code inviter}
	 * to {@code invited} player to the given {@code party}.
	 * Checks that the inviter is the leader of the party,
	 * checks that the inviter and the invited aren't the same
	 * player. Checks that the invited player is available
	 * (isn't in a party and it's not invited to another party).
	 * Checks party max member count.
	 *
	 * @param inviter The inviter player, must be the leader of the
	 *                specified {@code party}
	 * @param party   The party
	 * @param target  The invited player in name
	 */
	void handleInvitation(Player inviter, Party party, String target);

	/**
	 * Handle the request invitation when is send to player
	 *
	 * @param inviter the inviter player at String
	 * @param invited the invited player at String
	 */

	void handleRequestInvitation(String inviter, Player invited);

	/**
	 * Handle the accept invitation
	 * @param invited the player invited
	 */

	void handleAcceptInvitation(Player invited, String inviter);

	/**
	 * Send all players the party to server is the leader party
	 * @param player
	 * @param party
	 */

	void warp(Player player, Party party);

	/**
	 * Fetches the party by party id
	 * @param partyId The identifier party
	 * @return The found party, empty if the party no exists
	 */

	Optional<Party> getParty(String partyId);

	/**
	 * Fetches the party of the given {@code userId}
	 *
	 * @param userId The member or leader of the
	 *               searched party
	 * @return The found party, empty if user isn't member
	 * or leader of a party
	 */
	Optional<Party> getPartyOf(String userId);

	/**
	 * Creates a new party using the given {@code leaderId}
	 * the method doesn't check if the player is already in a
	 * party so, it must be made by the invoker
	 *
	 * @param leaderId The party leader
	 * @return The created party
	 */
	Party createParty(String leaderId);

}
