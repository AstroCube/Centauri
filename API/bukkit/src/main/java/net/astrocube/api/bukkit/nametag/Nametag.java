package net.astrocube.api.bukkit.nametag;

import org.bukkit.entity.Player;

import java.util.Set;

public interface Nametag {

    Player getRecipient();

    String getTag();

    String getTeamName();

    interface Rendered extends Nametag {

        Player getViewer();

        Set<Entity> getSpawnedEntities();

        void setTag(String tag);

        void hide();

        void show();

        interface Entity {

            /**
             * Identifier of the entity
             * Used to identify the rendered entity
             * and interact with it
             */
            String getIdentifier();

            /**
             * Id of the entity
             * Used to identify the rendered entity
             * and interact with it
             */
            int getEntityId();

            /**
             * Object representing the packet entity
             * Used to identify the rendered entity
             * and interact with it
             */
            Object getEntityObject();

        }

    }

}