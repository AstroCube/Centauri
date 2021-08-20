package net.astrocube.lobby.hide;

import com.google.inject.name.Names;
import net.astrocube.inject.ProtectedModule;
import net.astrocube.api.bukkit.lobby.hide.*;
import net.astrocube.lobby.hide.applier.FriendHideApplier;
import net.astrocube.lobby.hide.applier.PermissionHideApplier;
import net.astrocube.lobby.hide.applier.StaffHideApplier;

public class HideModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(HideApplier.class).annotatedWith(Names.named("friend")).to(FriendHideApplier.class);
		bind(HideApplier.class).annotatedWith(Names.named("permission")).to(PermissionHideApplier.class);
		bind(HideApplier.class).annotatedWith(Names.named("staff")).to(StaffHideApplier.class);
		bind(HideJoinProcessor.class).to(CoreHideJoinProcessor.class);
		bind(HideCompoundMatcher.class).to(CoreHideCompoundMatcher.class);
		bind(HideItemActionable.class).to(CoreHideItemActionable.class);
		bind(HideStatusModifier.class).to(CoreHideStatusModifier.class);
	}

}
