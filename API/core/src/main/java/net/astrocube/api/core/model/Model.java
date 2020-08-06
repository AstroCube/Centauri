package net.astrocube.api.core.model;

import java.time.Instant;

public interface Model extends IdentifiableModel, PartialModel {

    /**
     * Instant where the model was exactly created at
     * @return created at instant
     */
    Instant getCreatedAt();

    /**
     * Instant where the model was exactly updated at
     * @return updated at instant
     */
    Instant getUpdatedAt();

}
