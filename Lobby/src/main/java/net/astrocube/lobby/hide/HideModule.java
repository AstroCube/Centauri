package net.astrocube.lobby.hide;

import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.hide.HideCompoundMatcher;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.bukkit.lobby.hide.HideApplier;
import net.astrocube.lobby.hide.applier.FriendHideApplier;
import net.astrocube.lobby.hide.applier.PermissionHideApplier;
import net.astrocube.lobby.hide.applier.StaffHideApplier;

public class HideModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(HideApplier.class).annotatedWith(Names.named("friend")).to(FriendHideApplier.class);
        bind(HideApplier.class).annotatedWith(Names.named("permission")).to(PermissionHideApplier.class);
        bind(HideApplier.class).annotatedWith(Names.named("staff")).to(StaffHideApplier.class);
        bind(HideCompoundMatcher.class).to(CoreHideCompoundMatcher.class);
        bind(HideStatusModifier.class).to(CoreHideStatusModifier.class);
    }

}
