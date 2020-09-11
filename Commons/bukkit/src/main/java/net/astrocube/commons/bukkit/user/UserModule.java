package net.astrocube.commons.bukkit.user;

import me.fixeddev.inject.ProtectedModule;

import net.astrocube.api.bukkit.user.UserMatcher;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.commons.bukkit.user.display.CoreDisplayMatcher;

public class UserModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(DisplayMatcher.class).to(CoreDisplayMatcher.class);
        bind(UserMatcher.class).to(UserMatcher.class);

        expose(DisplayMatcher.class);
        expose(UserMatcher.class);
    }

}