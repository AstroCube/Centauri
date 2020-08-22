package net.astrocube.lobby.hide;

import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideCompound;
import net.astrocube.api.bukkit.lobby.hide.HideStatusModifier;
import net.astrocube.api.core.virtual.user.User;

@Singleton
public class CoreHideStatusModifier implements HideStatusModifier {

    @Override
    public void apply(User user, HideCompound compound) {



    }

    @Override
    public void restore(User user) {

    }

}
