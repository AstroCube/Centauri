package net.astrocube.commons.bukkit.tablist;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.tablist.TablistCompound;
import net.astrocube.api.bukkit.tablist.TablistGenerator;
import org.bukkit.entity.Player;

import java.util.List;

@Singleton
public class CoreTablistGenerator implements TablistGenerator {

    private @Inject MessageHandler<Player> messageHandler;

    @Override
    public TablistCompound generate(Player player) {
        return new TablistCompound() {
            @Override
            public List<String> getHeader() {
                return messageHandler.getMany(player, "tablist.header");
            }

            @Override
            public List<String> getFooter() {
                return messageHandler.getMany(player, "tablist.footer");
            }
        };
    }

}
