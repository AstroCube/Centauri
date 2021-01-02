package net.astrocube.commons.bungee.configuration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class BungeeConcurrentProvider implements ExecutorServiceProvider {

    private ExecutorService executorService;
    private @Inject PluginConfigurationHelper configurationHelper;

    @Override
    public ExecutorService getRegisteredService() {
        if (executorService == null) this.executorService = Executors.newFixedThreadPool(getConfiguredThreads());
        System.out.println("Registered service");
        System.out.println(executorService);
        System.out.println(getConfiguredThreads());
        return executorService;
    }

    @Override
    public int getConfiguredThreads() {
        return configurationHelper.get().getInt("api.threads", 2);
    }

}
