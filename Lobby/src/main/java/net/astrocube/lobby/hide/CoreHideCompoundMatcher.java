package net.astrocube.lobby.hide;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.lobby.hide.HideCompound;
import net.astrocube.api.bukkit.lobby.hide.HideCompoundMatcher;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.part.GameOptions;
import net.astrocube.lobby.hide.collection.DefaultCompound;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class CoreHideCompoundMatcher implements HideCompoundMatcher {

    private final Map<GameOptions.General.HideType, HideCompound> compoundMap;

    @Inject
    public CoreHideCompoundMatcher() {
        this.compoundMap = new HashMap<>();
        compoundMap.put(GameOptions.General.HideType.ALONE, DefaultCompound.aloneCompound());
        compoundMap.put(GameOptions.General.HideType.DEFAULT, DefaultCompound.defaultCompound());
        compoundMap.put(GameOptions.General.HideType.FRIENDLESS, DefaultCompound.friendlessCompound());
    }


    @Override
    public HideCompound getUserCompound(User user) {
        return compoundMap.get(user.getSettings().getGeneralSettings().getHideType());
    }

}
