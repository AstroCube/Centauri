package net.astrocube.api.core.service;

import com.google.common.reflect.TypeToken;
import net.astrocube.api.core.message.Message;
import net.astrocube.api.core.message.MessageDefaults;
import net.astrocube.api.core.model.PartialModel;

@MessageDefaults.ChannelName("model-message")
@SuppressWarnings("UnstableApiUsage")
public interface ModelRequest<Complete extends PartialModel> extends Message {

    /**
     * WIll return TypeToken to be used for any request
     * @return TypeToken
     */
    default TypeToken<Complete> getModelType(){
        return new TypeToken<Complete>(getClass()) {};
    }

}