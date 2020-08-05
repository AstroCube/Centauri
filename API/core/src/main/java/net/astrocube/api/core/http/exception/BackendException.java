package net.astrocube.api.core.http.exception;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * Thrown when an API call has an exceptional response. This abstracts away HTTP
 * status codes so we can use other protocols for the API.
 */
public abstract class BackendException extends Exception {

    private @Nullable StackTraceElement[] originalTrace;
    private @Nullable StackTraceElement[] callSite;

    public BackendException(String message) {
        this(message, null, null);
    }

    public BackendException(String message, @Nullable StackTraceElement[] callSite) {
        this(message, null, callSite);
    }

    public BackendException(String message, @Nullable Throwable cause, @Nullable StackTraceElement[] callSite) {
        super(message, cause);
        setCallSite(callSite);
    }

    public @Nullable StackTraceElement[] getCallSite() {
        return callSite;
    }

    public void setCallSite(@Nullable StackTraceElement[] callSite) {
        this.callSite = callSite;
        if(callSite != null) {
            if(originalTrace == null) {
                originalTrace = getStackTrace();
            }

            List<StackTraceElement> element = Arrays.asList(originalTrace);
            element.addAll(Arrays.asList(callSite));
            setStackTrace((StackTraceElement[]) element.toArray());
        }
    }

    abstract int statusCode();

}