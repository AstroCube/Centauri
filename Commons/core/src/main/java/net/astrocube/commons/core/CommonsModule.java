package net.astrocube.commons.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.commons.core.friend.FriendModule;
import net.astrocube.commons.core.http.HttpModule;
import net.astrocube.commons.core.message.MessagerModule;
import net.astrocube.commons.core.redis.RedisModule;
import net.astrocube.commons.core.server.CoreServerModule;
import net.astrocube.commons.core.session.SessionModule;
import net.astrocube.commons.core.virtual.ModelManifest;

public class CommonsModule extends ProtectedModule {

    @Override
    protected void configure() {
        install(new FriendModule());
        install(new HttpModule());
        install(new RedisModule());
        install(new MessagerModule());
        install(new SessionModule());
        install(new ModelManifest());
        install(new CoreServerModule());

        bind(ObjectMapper.class).toProvider(() -> {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JodaModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
            mapper.setVisibility(mapper.getSerializationConfig()
                    .getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new MrBeanModule());
            return mapper;
        }).in(Scopes.SINGLETON);

    }

}
