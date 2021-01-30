package net.astrocube.commons.bukkit.user;

import com.google.inject.Scopes;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.user.UserMatcher;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.FlairFormatter;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;
import net.astrocube.commons.bukkit.user.display.CoreDisplayMatcher;
import net.astrocube.commons.bukkit.user.display.CoreFlairFormatter;
import net.astrocube.commons.bukkit.user.staff.CoreOnlineStaffProvider;

public class UserModule extends ProtectedModule {

    @Override
    protected void configure() {
        bind(DisplayMatcher.class).to(CoreDisplayMatcher.class).in(Scopes.SINGLETON);
        bind(UserMatcher.class).to(CoreUserMatcher.class).in(Scopes.SINGLETON);
        bind(FlairFormatter.class).to(CoreFlairFormatter.class).in(Scopes.SINGLETON);
        bind(OnlineStaffProvider.class).to(CoreOnlineStaffProvider.class).in(Scopes.SINGLETON);

        expose(DisplayMatcher.class);
        expose(UserMatcher.class);
        expose(FlairFormatter.class);
        expose(OnlineStaffProvider.class);
    }
}