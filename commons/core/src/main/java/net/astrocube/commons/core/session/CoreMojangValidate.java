package net.astrocube.commons.core.session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.session.MojangValidate;
import net.astrocube.commons.core.http.resolver.RequestExceptionResolverUtil;

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

		HttpResponse response = httpClient.executeRequestSync(
				url,
				HttpRequest::execute,
				new RequestOptions(
						RequestOptions.Type.GET,
						headers,
						"",
						""
				)
		);

		int statusCode = response.getStatusCode();
		String json = response.parseAsString();

		if (statusCode == 400) {
			// The api returns 400 if an invalid premium unique id was given
			return false;
		} else if (statusCode == 200) {
			JsonNode node = this.mapper.readTree(json);
			return node.get("username").toString().equalsIgnoreCase(user);
		} else {
			throw RequestExceptionResolverUtil.generateException(json, statusCode);
		}

	}

}
