package net.astrocube.commons.core.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.session.MojangValidate;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

@Singleton
public class CoreMojangValidate implements MojangValidate {

    private @Inject HttpClient httpClient;
    private @Inject ObjectMapper mapper;

    @Override
    public boolean validateUUID(String user, UUID uniqueId) throws Exception {

        String response = httpClient.executeRequestSync(
                "https://api.minetools.eu/uuid/" + uniqueId.toString().toLowerCase(Locale.ROOT),
                new CoreRequestCallable<>(TypeToken.of(String.class), mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        "",
                        null
                )
        );

        JsonNode node = this.mapper.readTree(response);
        return node.get("name").toString().equalsIgnoreCase(user) &&
                node.get("status").toString().equalsIgnoreCase("OK");

    }

}
