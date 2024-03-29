package net.astrocube.api.core.message;

import net.astrocube.api.core.model.Document;
import org.joda.time.DateTime;

import java.util.Map;

public interface Metadata extends Document {

    Map<String, Object> getHeaders();

    String getMessageId();

    String getAppId();

    String getInstanceId();

    DateTime getTimestamp();

}