package net.astrocube.api.core.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;

public interface SessionService {

    /**
     * Will check if session is legitimate or has any trouble before authorize the server login
     * @param validate compound of user and address
     * @return validation checking
     */
    AsyncResponse<SessionValidateDoc.Complete> authenticationCheck(CreateRequest<SessionValidateDoc.Request> validate);

    /**
     * Will check if session is legitimate or has any trouble before authorize the server login
     * @param validate compound of user and address
     * @return validation checking
     */
    SessionValidateDoc.Complete authenticationCheckSync(CreateRequest<SessionValidateDoc.Request> validate) throws Exception;

    /**
     * Will send a notification of server switching to the backend
     * @param serverSwitch to be notified
     */
    void serverSwitch(CreateRequest<SessionValidateDoc.ServerSwitch> serverSwitch) throws Exception;

    /**
     * Will send a notification when a user finished his proxy connection
     * @param user to be disconnected from the proxy
     */
    void serverDisconnect(String user) throws Exception;

}
