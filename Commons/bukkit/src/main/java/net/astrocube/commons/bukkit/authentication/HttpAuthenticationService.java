package net.astrocube.commons.bukkit.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.authentication.UserAuthorization;
import net.astrocube.api.bukkit.authentication.gateway.AuthenticationService;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.commons.core.http.CoreRequestCallable;

@Singleton
@SuppressWarnings("UnstableApiUsage")
public class HttpAuthenticationService implements AuthenticationService {

	private @Inject HttpClient httpClient;
	private @Inject ObjectMapper objectMapper;

	@Override
	public void register(UserAuthorization authorization) throws Exception {
		httpClient.executeRequestSync(
			"authentication/register-server",
			new CoreRequestCallable<>(TypeToken.of(Void.class), objectMapper),
			new RequestOptions(
				RequestOptions.Type.POST,
				objectMapper.writeValueAsString(authorization)
			)
		);
	}

	@Override
	public void login(UserAuthorization authorization) throws Exception {
		httpClient.executeRequestSync(
			"authentication/login-server",
			new CoreRequestCallable<>(TypeToken.of(Void.class), objectMapper),
			new RequestOptions(
				RequestOptions.Type.POST,
				objectMapper.writeValueAsString(authorization)
			)
		);
	}
}
