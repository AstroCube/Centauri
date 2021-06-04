package net.astrocube.commons.core.server;

import com.google.inject.Inject;
import net.astrocube.api.core.server.ServerConnectionManager;
import net.astrocube.api.core.server.ServerService;
import net.astrocube.api.core.virtual.server.ServerDoc;

public class CentauriConnectionManager implements ServerConnectionManager {

	private final ServerService serverService;

	@Inject
	public CentauriConnectionManager(ServerService serverService) {
		this.serverService = serverService;
	}

	@Override
	public String startConnection(String slug, ServerDoc.Type type, String cluster, boolean sandbox) throws Exception {
		ServerDoc.Identity identity = new ServerDoc.Identity() {
			@Override
			public String getSlug() {
				return slug;
			}

			@Override
			public boolean isSandbox() {
				return sandbox;
			}

			@Override
			public ServerDoc.Type getServerType() {
				return type;
			}

			@Override
			public String getCluster() {
				return cluster;
			}
		};
		return this.serverService.connect(() -> identity);
	}

	@Override
	public void endConnection() throws Exception {
		serverService.disconnect();
	}

}
