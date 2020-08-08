package net.astrocube.commons.bukkit.core.config;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

import java.util.concurrent.Executors;

public class BukkitConcurrentProvider implements ExecutorServiceProvider {

    private ListeningExecutorService executorService;

    @Override
    public ListeningExecutorService getRegisteredService() {
        if (executorService == null) this.executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(getConfiguredThreads()));
        return executorService;
    }

    @Override
    public int getConfiguredThreads() {
        return Configuration.getInteger("api.threads", 2);
    }

}
