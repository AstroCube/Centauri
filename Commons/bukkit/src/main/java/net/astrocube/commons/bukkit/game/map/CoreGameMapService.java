package net.astrocube.commons.bukkit.game.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.map.GameMapService;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.core.http.config.HttpClientConfig;
import net.astrocube.api.core.http.header.AuthorizationProcessor;
import net.astrocube.api.core.model.ModelMeta;
import net.astrocube.api.core.service.query.QueryService;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

@Singleton
public class CoreGameMapService implements GameMapService {

	private @Inject ObjectMapper mapper;
	private @Inject QueryService<GameMap> queryService;
	private @Inject HttpClientConfig httpClientConfig;
	private @Inject ModelMeta<GameMap, GameMap> modelMeta;
	private @Inject AuthorizationProcessor authorizationProcessor;

	@Override
	public byte[] getMapWorld(String id) throws IOException {
		return getBytesFromFile("files/file/" + id);
	}

	@Override
	public byte[] getMapConfiguration(String id) throws IOException {
		return getBytesFromFile("files/config/" + id);
	}

	@Override
	public Optional<GameMap> getRandomMap(String gameMode, String subGameMode) throws Exception {
		ObjectNode node = mapper.createObjectNode();
		node.put("gamemode", gameMode);
		node.put("subGamemode", subGameMode);

		Set<GameMap> paginate = queryService.querySync(node).getFoundModels();

		return paginate.stream().findAny();
	}

	private byte[] getBytesFromFile(String route) throws IOException {

		URL url = new URL(
			httpClientConfig.getBaseURL() + this.modelMeta.getRouteKey() + "/" + route
		);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("User-Agent", "Mozilla/4.0");
		con.setRequestProperty("Authorization", "Bearer " + new String(authorizationProcessor.getAuthorizationToken()));

		return IOUtils.toByteArray(con.getInputStream());
	}

}
