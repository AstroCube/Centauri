package net.astrocube.api.core.message;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A {@link Messenger} consumer that can handle multiple message types.
 * Message handler methods are annotated with {@link HandleMessage},
 * and must take a {@link Message} subtype as their first parameter.
 * They can optionally take a {@link Metadata} as the second parameter.
 */
public interface MessageListener {

    @Retention(RetentionPolicy.RUNTIME)
    @interface HandleMessage {}

}