package net.astrocube.commons.core.http.resolver;

import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpIOExceptionHandler;
import com.google.api.client.util.ExponentialBackOff;
import net.astrocube.api.core.http.resolver.RequestExceptionHandler;

public class CoreRequestExceptionHandler implements RequestExceptionHandler {

	@Override
	public HttpIOExceptionHandler getExceptionBackoff() {
		return new HttpBackOffIOExceptionHandler(new ExponentialBackOff.Builder().build());
	}

}
