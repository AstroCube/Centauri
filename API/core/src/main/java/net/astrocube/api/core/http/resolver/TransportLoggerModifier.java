package net.astrocube.api.core.http.resolver;

import java.util.logging.Logger;

public interface TransportLoggerModifier {

	/**
	 * Will override default Google Http Client and replace it in order to prevent noisiness
	 * @param logger to be replaced
	 */
	void overrideDefaultLogger(Logger logger);

}
