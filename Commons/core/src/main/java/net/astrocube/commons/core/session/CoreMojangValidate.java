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
import net.astrocube.commons.core.http.RawRequestCallable;

import java.util.*;

@Singleton
public class CoreMojangValidate implements MojangValidate {

    private @Inject HttpClient httpClient;
    private @Inject ObjectMapper mapper;

    @Override
    public boolean validateUUID(String user, UUID uniqueId) throws Exception {

        Map<String, String> headers = new HashMap<>();
        String url = "https://api.minetools.eu/uuid/" + uniqueId.toString().toLowerCase(Locale.ROOT);
        headers.put("Referer", url);

        String response = httpClient.executeRequestSync(
                url,
                new RawRequestCallable(),
                new CoreRequestOptions(
                        RequestOptions.Type.GET,
                        headers,
                        "",
                        null
                )
        );

        JsonNode node = this.mapper.readTree(response);
        return node.get("name").toString().equalsIgnoreCase(user) &&
                node.get("status").toString().equalsIgnoreCase("OK");

    }

}
