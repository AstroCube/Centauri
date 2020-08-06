package net.astrocube.api.core.concurrent;


import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;

public class WrappedResponse<T> implements Response<T> {

    private final Status status;
    @Nullable private final T response;
    @Nullable private final Exception thrownException;

    /**
     * Constructor of async response
     * @param thrownException shouldn't be null when an exception was thrown during the async block
     * @param status should be SUCCESS when throwedException is null or ERROR when response is null
     * @param response shouldn't be null when an exception was never thrown during the async block
     */
    public WrappedResponse(Status status, @Nullable T response, @Nullable Exception thrownException) {
        this.status = status;
        this.thrownException = thrownException;
        this.response = response;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public Optional<T> getResponse() {
        return Optional.ofNullable(this.response);
    }

    @Override
    public Optional<Exception> getThrownException() {
        return Optional.ofNullable(this.thrownException);
    }

    @Override
    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    @Override
    public void ifSuccessful(Consumer<? super T> consumer) {
        if (this.status == Status.SUCCESS)
            consumer.accept(this.response);
    }
}