package net.astrocube.commons.core.utils;

import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.concurrent.Response;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Callbacks {

    private Callbacks() {
        throw new UnsupportedOperationException("This class couldn't be instantiated!");
    }

    public static <T> Callback<Response<T>> applyCommonErrorHandler(Callback<T> callback) {
        return applyCommonErrorHandler(null, callback);
    }

    public static <T> Callback<Response<T>> applyCommonErrorHandler(@Nullable String processName, Callback<T> callback) {
        return new Callback<Response<T>>() {

            @Override
            public void call(Response<T> object) {
                if (!object.isSuccessful() || object.getResponse().isPresent()) {
                    handleException(object.getThrownException().orElse(null));
                    return;
                }
                callback.call(object.getResponse().get());
            }

            @Override
            public void handleException(Throwable throwable) {
                Logger.getLogger("async-pool").warning("Process '" + processName + "' has not been completed successfully!");
                Optional.ofNullable(throwable).ifPresent(exception -> {
                    Logger.getLogger("async-pool").log(Level.SEVERE, "'" + processName + "' process exception: ", exception);
                });
            }

        };
    }

}
