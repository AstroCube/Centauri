package net.astrocube.api.core.http.resolver;

import com.google.inject.Singleton;

import java.util.logging.Logger;

@Singleton
public interface TransportLoggerModifier {

    /**
     * Will override default Google Http Client and replace it in order to prevent noisiness
     * @param logger to be replaced
     */
    void overrideDefaultLogger(Logger logger);

}
