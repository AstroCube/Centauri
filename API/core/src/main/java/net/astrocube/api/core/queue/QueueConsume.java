package net.astrocube.api.core.queue;

import java.util.Map;

public interface QueueConsume {

	String getName();

	boolean isDurable();

	boolean isExclusive();

	boolean isAutoDelete();

	Map<String, Object> getArguments();

}
