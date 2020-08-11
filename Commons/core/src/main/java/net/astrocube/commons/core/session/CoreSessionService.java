package net.astrocube.commons.core.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.*;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.session.SessionService;
import net.astrocube.api.core.virtual.session.SessionValidateDoc;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;

@SuppressWarnings("UnstableApiUsage")
public class CoreSessionService implements SessionService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ListeningExecutorService listeningExecutorService;

    @Inject CoreSessionService(
            HttpClient httpClient,
            ObjectMapper objectMapper,
            ExecutorServiceProvider executorServiceProvider
    ) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.listeningExecutorService  = executorServiceProvider.getRegisteredService();
    }

    @Override
    public AsyncResponse<SessionValidateDoc.Complete> authenticationCheck(CreateRequest<SessionValidateDoc.Request> validate) {
        return new SimpleAsyncResponse<>(this.listeningExecutorService.submit(() -> {
            try {
                return new WrappedResponse<>(Response.Status.SUCCESS, authenticationCheckSync(validate), null);
            } catch (Exception exception) {
                return new WrappedResponse<>(Response.Status.ERROR, null, exception);
            }
        }), listeningExecutorService);

    }

    @Override
    public SessionValidateDoc.Complete authenticationCheckSync(CreateRequest<SessionValidateDoc.Request> validate) throws Exception {
        return httpClient.executeRequestSync(
                "session/auth-session",
                new CoreRequestCallable<>(TypeToken.of(SessionValidateDoc.Complete.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        this.objectMapper.writeValueAsString(validate.getModel())
                )
        );
    }

    @Override
    public void serverSwitch(CreateRequest<SessionValidateDoc.ServerSwitch> user) throws Exception {
        httpClient.executeRequestSync(
                "session/server-switch",
                new CoreRequestCallable<>(TypeToken.of(SessionValidateDoc.Complete.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        this.objectMapper.writeValueAsString(user.getModel())
                )
        );
    }

    @Override
    public void serverDisconnect(String user) throws Exception {
        httpClient.executeRequestSync(
                "session/user-disconnect/" + user,
                new CoreRequestCallable<>(TypeToken.of(SessionValidateDoc.Complete.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.GET,
                        ""
                )
        );
    }

}
