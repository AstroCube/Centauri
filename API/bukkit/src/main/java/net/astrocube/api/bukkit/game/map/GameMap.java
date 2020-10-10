package net.astrocube.api.bukkit.game.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;

import java.util.Set;

/**
 * Map record created at database which store
 * info to work correctly along Game Control.
 */
public interface GameMap extends Model.Stamped {

    /**
     * Retrieve the map name for certain identification purposes
     * @return string with map name
     */
    String getName();

    /**
     * Get the map file in a file array, which is stored in
     * Slime format.
     * @return byte array containing map
     */
    byte[] getFile();

    // TODO: MapConfiguration

    String getAuthor();

    String getVersion();

    Contribution getContributors();

    String getGameMode();

    @JsonProperty("subGamemode")
    String getSubMode();

    String getDescription();

    Set<Rating> getRating();

    interface Contribution {

        @JsonProperty("contributor")
        String getAuthor();

        @JsonProperty("contribution")
        String getDescription();

    }

    interface Rating {

        @JsonProperty("star")
        short getScore();

        @JsonProperty("user")
        String getAuthor();

    }

}
