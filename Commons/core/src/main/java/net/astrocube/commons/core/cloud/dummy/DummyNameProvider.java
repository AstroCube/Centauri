package net.astrocube.commons.core.cloud.dummy;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.InstanceNameProvider;

@Singleton
public class DummyNameProvider implements InstanceNameProvider {

    @Override
    public String getName() {
        return "No Cloud Hooked";
    }

}
