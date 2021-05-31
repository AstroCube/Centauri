package net.astrocube.api.core.message;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface Channel<T extends Message> {

	String getName();

	String getId();

	Class<T> getType();

	Channel<T> sendMessage(T object, Map<String, Object> headers) throws JsonProcessingException;

}