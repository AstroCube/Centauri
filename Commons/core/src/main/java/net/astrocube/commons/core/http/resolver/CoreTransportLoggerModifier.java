package net.astrocube.commons.core.http.resolver;

import com.google.api.client.http.HttpTransport;
import net.astrocube.api.core.http.resolver.TransportLoggerModifier;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class CoreTransportLoggerModifier implements TransportLoggerModifier {

    @Override
    public void overrideDefaultLogger(final Logger logger) {
        final Logger httpLogger = Logger.getLogger(HttpTransport.class.getName());
        httpLogger.setUseParentHandlers(false);
        httpLogger.addHandler(new ConsoleHandler() {
            @Override
            public void publish(LogRecord record) {
                String message = record.getMessage();
                if (record.getThrown() != null) message += ": " + record.getThrown().toString();
                logger.log(record.getLevel(), message);
            }
        });
    }

}
