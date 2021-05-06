package net.astrocube.commons.bukkit.tablist;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.tablist.TablistCompound;
import net.astrocube.api.bukkit.tablist.TablistCompoundApplier;
import net.astrocube.api.bukkit.tablist.TablistGenerator;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.entity.Player;

import java.util.List;

@Singleton
public class CoreTablistCompoundApplier implements TablistCompoundApplier {

    private @Inject TablistGenerator tablistGenerator;

    @Override
    public void apply(Player player) {
        TablistCompound compound = tablistGenerator.generate(player);
        player.setPlayerListHeaderFooter(
                generateSpaces(compound.getHeader()),
                generateSpaces(compound.getFooter())
        );
    }

    private BaseComponent[] generateSpaces(List<String> compound) {

        ComponentBuilder componentBuilder = new ComponentBuilder("");

        for (int i = 0; compound.size() > i; i++) {
            if (i != 0) {
                componentBuilder.append("\n");
            }
            componentBuilder.appendLegacy(compound.get(i));
        }
        return componentBuilder.create();
    }

}
