package net.astrocube.commons.core.http.resolver;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.ExecutorServiceProvider;
import net.astrocube.api.core.http.resolver.RequestExecutorResolver;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CoreRequestExecutorResolver implements RequestExecutorResolver {

    private @Inject ExecutorServiceProvider executorServiceProvider;

    @Override
    public ListeningExecutorService getExecutor() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("API HTTP Executor").build();
        if (executorServiceProvider.getConfiguredThreads() > 0) {
            return executorServiceProvider.getRegisteredService();
        } else {
            return MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(threadFactory));
        }
    }

}
