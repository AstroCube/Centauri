package net.astrocube.lobby.selector.npc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.lobby.selector.npc.LobbyNPCSelector;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.Skin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public class CoreSelectorRegistry implements SelectorRegistry {

    private @Inject Plugin plugin;
    private @Inject MessageHandler messageHandler;
    private @Inject NPCLib npcLib;

    private final Set<SelectorCompound> registries = new HashSet<>();

    @Override
    public void generateRegistry() {

        for (Object section : plugin.getConfig().getList("selector")) {


            Map<String, Object> linkedKey = (Map<String, Object>) section;

            LobbyNPCSelector selector = new LobbyNPCSelector() {
                @Override
                public String getMode() {
                    return (String) linkedKey.get("mode");
                }

                @Override
                public String getSubMode() {
                    return (String) linkedKey.get("subMode");
                }

                @Override
                public String getValue() {
                    return (String) linkedKey.get("value");
                }

                @Override
                public String getSignature() {
                    return (String) linkedKey.get("signature");
                }

                @Override
                public double getX() {
                    return (double) linkedKey.get("x");
                }

                @Override
                public double getY() {
                    return (double) linkedKey.get("y");
                }

                @Override
                public double getZ() {
                    return (double) linkedKey.get("z");
                }

                @Override
                public int getYaw() {
                    return (int) linkedKey.get("yaw");
                }

                @Override
                public int getPitch() {
                    return (int) linkedKey.get("pitch");
                }
            };

            NPC npc = npcLib.createNPC();
            npc.setLocation(
                    new Location(
                            Bukkit.getWorlds().get(0),
                            selector.getX(),
                            selector.getY(),
                            selector.getZ(),
                            selector.getYaw(),
                            selector.getPitch()
                    )
            );
            npc.setSkin(new Skin(selector.getValue(), selector.getSignature()));

            registries.add(new SelectorCompound(npc, selector));

        }

    }

    @Override
    public void spawnSelectors(Player player) {

        registries.forEach(registry -> {

            StringList message = messageHandler.replacingMany(
                    player, "selectors.replacing",
                    "%%players%%", "0",
                    "%%title%%", messageHandler.get(player, "selectors." + registry.getLobbyNPCSelector().getMode())
            );


            registry.getNPC().show(player);
            registry.getNPC().setPlayerLines(message, player);

        });

    }

    @Override
    public void despawnSelectors(Player player) {
        registries.forEach(registry -> registry.getNPC().hide(player));
    }

    private static class SelectorCompound {

        private final NPC NPC;
        private final LobbyNPCSelector lobbyNPCSelector;

        public SelectorCompound(NPC NPC, LobbyNPCSelector lobbyNPCSelector) {
            this.NPC = NPC;
            this.lobbyNPCSelector = lobbyNPCSelector;
        }

        public NPC getNPC() {
            return NPC;
        }

        public LobbyNPCSelector getLobbyNPCSelector() {
            return lobbyNPCSelector;
        }

    }

}
