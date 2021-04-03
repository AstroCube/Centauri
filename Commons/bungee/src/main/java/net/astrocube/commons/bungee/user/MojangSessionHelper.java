package net.astrocube.commons.bungee.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;

import java.util.HashMap;
import java.util.Locale;

public class MojangSessionHelper {

    private @Inject HttpClient httpClient;
    private @Inject ObjectMapper mapper;

    public boolean hasValidUUID(String username, String UUID) throws Exception {

        String response = httpClient.executeRequestSync(
                "https://api.minetools.eu/uuid/" + UUID.toLowerCase(Locale.ROOT),
                new CoreRequestCallable<>(TypeToken.of(String.class), mapper),
                new CoreRequestOptions(
                        RequestOptions.Type.POST,
                        new HashMap<>(),
                        "",
                        null
                )
        );

        JsonNode node = this.mapper.readTree(response);
        return node.get("name").toString().equalsIgnoreCase(username) &&
                node.get("status").toString().equalsIgnoreCase("OK");

    }

}
