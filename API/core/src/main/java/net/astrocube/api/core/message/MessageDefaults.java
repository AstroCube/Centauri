package net.astrocube.api.core.message;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface MessageDefaults {

	@Retention(RetentionPolicy.RUNTIME)
	@interface ChannelName {
		String value();
	}

}