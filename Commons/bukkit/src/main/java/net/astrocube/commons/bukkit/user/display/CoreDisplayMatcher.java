package net.astrocube.commons.bukkit.user.display;

import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.api.core.virtual.user.UserDoc;

import org.bukkit.craftbukkit.libs.jline.internal.Configuration;

import java.util.Comparator;
import java.util.Optional;

public class CoreDisplayMatcher implements DisplayMatcher {

    @Override
    public Group.Flair getRealmDisplay(User user) {

        Group.Flair flair = new Group.Flair() {
            @Override
            public String getRealm() {
                return "default";
            }

            @Override
            public String getColor() {
                return "GRAY";
            }

            @Override
            public String getSymbol() {
                return "";
            }
        };

        Optional<Group> flairGroup = user.getGroups().stream()
                .map(UserDoc.UserGroup::getGroup)
                .filter(group -> group.getFlairs().stream().anyMatch(
                        f -> f.getRealm().equalsIgnoreCase(
                                Configuration.getString("server.realm", "default")
                        ))
                ).min(Comparator.comparingInt(Group::getPriority));

        if (!flairGroup.isPresent()) return flair;

        Optional<Group.Flair> finalFlair = flairGroup.get().getFlairs().stream()
                .filter(f -> f.getRealm().equalsIgnoreCase(
                        Configuration.getString("server.realm", "default")
                )).findAny();

        return finalFlair.orElse(flair);

    }

}
