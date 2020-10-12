package net.astrocube.api.bukkit.game.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.api.core.virtual.user.User;

import java.util.Set;

/**
 * Map record created at database which store
 * info to work correctly along Game Control.
 */
public interface GameMap extends Model.Stamped {

    /**
     * @return map name registered at database
     */
    String getName();

    /**
     * @return byte array containing map file in slime format
     */
    byte[] getFile();

    // TODO: MapConfiguration

    /**
     * @return map author id to be used with a {@link FindService}
     */
    String getAuthor();

    /**
     * @return semantic versioned map. Generally using (vx.x.x) template
     */
    String getVersion();

    /**
     * @return set containing registered contributions at database
     */
    Set<Contribution> getContributors();

    /**
     * @return map {@link GameMode} id to be used with a {@link FindService}
     */
    String getGameMode();

    /**
     * @return map {@link SubGameMode} id to be filtered from {@link GameMode} registry
     */
    @JsonProperty("subGamemode")
    String getSubMode();

    /**
     * @return simple description created by map makers
     */
    String getDescription();

    /**
     * @return rating set containing every user rate related to the map
     */
    Set<Rating> getRating();

    /**
     * Interface containing certain methods to determine contributors from map
     */
    interface Contribution {

        /**
         * @return contribution author id to be used with a {@link FindService}
         */
        @JsonProperty("contributor")
        String getAuthor();

        /**
         * @return which was the contribution made by the user
         */
        @JsonProperty("contribution")
        String getDescription();

    }

    /**
     * Interface containing certain methods to determine a {@link User}
     */
    interface Rating {

        /**
         * @return number from 0 to 5 indicating star quantity of the rating
         */
        @JsonProperty("star")
        short getScore();

        /**
         * @return rating author id to be used with a {@link FindService}
         */
        @JsonProperty("user")
        String getAuthor();

    }

}
