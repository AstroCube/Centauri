package net.astrocube.commons.core.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;
import net.astrocube.api.core.virtual.server.ServerDoc.Partial;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;

import java.util.HashMap;

@SuppressWarnings("UnstableApiUsage")
public class ServerModelService implements ServerService {

    private @Inject ModelMeta<Server, ServerDoc.Partial> modelMeta;
    private @Inject HttpClient httpClient;
    private @Inject ObjectMapper objectMapper;

    public String connect(CreateRequest<Partial> request) throws Exception {
        return httpClient.executeRequestSync(
                this.modelMeta.getRouteKey(),
                new CoreRequestCallable<>(TypeToken.of(String.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        this.objectMapper.writeValueAsString(request.getModel()),
                        null
                )
        );
    }

    public void disconnect() throws Exception {
        httpClient.executeRequestSync(
                this.modelMeta.getRouteKey(),
                new CoreRequestCallable<>(TypeToken.of(Void.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.DELETE,
                        ""
                )
        );
    }

    @Override
    public Server getActual() throws Exception {
        return httpClient.executeRequestSync(
                this.modelMeta.getRouteKey() + "/view/me",
                new CoreRequestCallable<>(TypeToken.of(Server.class), this.objectMapper),
                new CoreRequestOptions(
                        RequestOptions.Type.GET,
                        ""
                )
        );
    }

}
