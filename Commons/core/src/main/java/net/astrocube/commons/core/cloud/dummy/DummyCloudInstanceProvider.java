package net.astrocube.commons.core.cloud.dummy;

import com.google.inject.Singleton;
import net.astrocube.api.core.cloud.CloudInstanceProvider;

import java.util.HashSet;
import java.util.Set;

@Singleton
public class DummyCloudInstanceProvider implements CloudInstanceProvider {

    @Override
    public Set<Instance> getGroupInstances(String name) {

        Set<Instance> instances = new HashSet<>();
        instances.add(generateTestLobbies(1, 5, 1, false));
        instances.add(generateTestLobbies(2, 2, 2, false));
        instances.add(generateTestLobbies(3, 5, 3, true));
        instances.add(generateTestLobbies(3, 3, 4, true));

        return instances;
    }

    private Instance generateTestLobbies(int connected, int max, int pos, boolean same) {
        return new Instance() {
            @Override
            public String getName() {
                return "Test Lobby";
            }

            @Override
            public int getConnected() {
                return connected;
            }

            @Override
            public int getMax() {
                return max;
            }

            @Override
            public boolean isFull() {
                return connected >= max;
            }

            @Override
            public int getNumber() {
                return pos;
            }
        };
    }

}
