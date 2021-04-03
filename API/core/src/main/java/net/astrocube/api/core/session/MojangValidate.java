package net.astrocube.api.core.session;

import java.util.UUID;

public interface MojangValidate {

    /**
     * Validates if a session is legit
     * @param user to validate
     * @param uniqueId provided
     * @return if session is valid
     */
    boolean validateUUID(String user, UUID uniqueId) throws Exception;

}
