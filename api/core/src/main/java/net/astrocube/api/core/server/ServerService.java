package net.astrocube.api.core.server;

import net.astrocube.api.core.service.create.CreateRequest;
import net.astrocube.api.core.virtual.server.Server;
import net.astrocube.api.core.virtual.server.ServerDoc;

public interface ServerService {

	String connect(CreateRequest<ServerDoc.Partial> request) throws Exception;

	void disconnect() throws Exception;

	Server getActual() throws Exception;

}
