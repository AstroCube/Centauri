package net.astrocube.api.core.virtual.session;


import net.astrocube.api.core.model.ModelProperties;

/**
 * This is a session validation response. Backend will check if account is correctly
 * registered. Also will check if user is infringing multi-account policy.
 */
@ModelProperties.RouteKey("session")
public interface SessionValidate extends SessionValidateDoc.Complete {
}
