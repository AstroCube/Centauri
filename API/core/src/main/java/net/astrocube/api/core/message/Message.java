package net.astrocube.api.core.message;

import net.astrocube.api.core.model.Document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A {@link Document} representing incoming and outgoing messages from
 * a {@link Channel} through the {@link Messenger}.
 */
public interface Message extends Document {

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@interface ChannelName {

		String value();

	}

}