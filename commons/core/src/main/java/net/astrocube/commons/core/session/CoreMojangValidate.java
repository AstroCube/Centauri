package net.astrocube.commons.core.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpResponseException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.session.MojangValidate;
import net.astrocube.commons.core.http.RawRequestCallable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Singleton
public class CoreMojangValidate implements MojangValidate {

	private @Inject HttpClient httpClient;
	private @Inject ObjectMapper mapper;

	@Override
	public boolean validateUUID(String user, UUID uniqueId) throws Exception {

		Map<String, String> headers = new HashMap<>();
		String url = "https://api.ashcon.app/mojang/v2/user/" + uniqueId.toString().toLowerCase(Locale.ROOT);
		headers.put("Referer", url);
		String json;

		try {
			json = httpClient.executeRequestSync(
					url,
					new RawRequestCallable(),
					new RequestOptions(
							RequestOptions.Type.GET,
							headers,
							"",
							""
					)
			);
		} catch (HttpResponseException e) {
			// I'm sure we don't have a bad request, but
			// it returns 400 if the unique id isn't a valid
			// premium uuid
			if (e.getStatusCode() == 400) {
				return false;
			} else {
				throw e;
			}
		}

		JsonNode node = this.mapper.readTree(json);
		return node.get("username").textValue().equalsIgnoreCase(user);
	}

}