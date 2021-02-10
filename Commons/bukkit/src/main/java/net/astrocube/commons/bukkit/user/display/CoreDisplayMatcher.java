package net.astrocube.commons.bukkit.user.display;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.bukkit.user.display.TranslatedFlairFormat;
import net.astrocube.api.bukkit.user.display.TranslatedGroupProvider;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.Optional;

public class CoreDisplayMatcher implements DisplayMatcher {

    private @Inject TranslatedGroupProvider translatedFlairFormat;

    @Override
    public TranslatedFlairFormat getDisplay(Player player, User user) {
        Optional<Group> flairGroup = user.getGroups().stream()
                .map(UserDoc.UserGroup::getGroup)
                .min(Comparator.comparingInt(Group::getPriority));
        return translatedFlairFormat.getGroupTranslations(
                player,
                flairGroup.isPresent() ? flairGroup.get().getId() : "default",
                flairGroup.isPresent() ? DisplayMatcher.getColor(flairGroup.get()) : ChatColor.GRAY
        );
    }

}
