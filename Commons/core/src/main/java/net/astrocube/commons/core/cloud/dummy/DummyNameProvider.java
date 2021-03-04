package net.astrocube.commons.core.cloud.dummy;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.InstanceNameProvider;

import java.util.UUID;

@Singleton
public class DummyNameProvider implements InstanceNameProvider {

    @Override
    public String getName() {
        return UUID.randomUUID().toString();
    }

}
