package net.astrocube.commons.core.http.header;

import com.google.inject.Singleton;
import net.astrocube.api.core.http.header.AuthorizationProcessor;

@Singleton
public class CoreAuthorizationProcessor implements AuthorizationProcessor {

	private char[] authorization;

	@Override
	public char[] getAuthorizationToken() {
		return this.authorization;
	}

	@Override
	public void authorizeBackend(char[] token) throws Exception {
		if (this.authorization == null) {
			throw new Exception("You can not authorize server again");
		}
		this.authorization = token;
	}
}
