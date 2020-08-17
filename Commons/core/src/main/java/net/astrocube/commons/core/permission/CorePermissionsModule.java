package net.astrocube.commons.core.permission;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.permission.EffectivePermissionsExtractor;
import net.astrocube.api.core.permission.PermissionBalancer;
import net.astrocube.api.core.permission.PermissionEvaluator;

public class CorePermissionsModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(EffectivePermissionsExtractor.class).to(CoreEffectivePermissionsExtractor.class);
        bind(PermissionBalancer.class).to(CorePermissionBalancer.class);
        bind(PermissionEvaluator.class).to(CorePermissionEvaluator.class);
    }
}