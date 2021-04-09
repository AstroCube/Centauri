package net.astrocube.lobby.selector.npc;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import me.yushust.message.util.StringList;
import net.astrocube.api.bukkit.lobby.selector.npc.LobbyNPCActionHandler;
import net.astrocube.api.bukkit.lobby.selector.npc.LobbyNPCSelector;
import net.astrocube.api.bukkit.lobby.selector.npc.SelectorRegistry;
import net.astrocube.api.core.cloud.CloudModeConnectedProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import net.astrocube.puppets.entity.ClickAction;
import net.astrocube.puppets.entity.PuppetRegistry;
import net.astrocube.puppets.location.CoreLocation;
import net.astrocube.puppets.player.PlayerPuppetEntity;
import net.astrocube.puppets.player.PlayerPuppetEntityBuilder;
import net.astrocube.puppets.player.skin.CorePuppetSkin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Singleton
public class CoreSelectorRegistry implements SelectorRegistry {

    private @Inject Plugin plugin;
    private @Inject MessageHandler messageHandler;
    private @Inject PuppetRegistry puppetRegistry;
    private @Inject CloudModeConnectedProvider cloudModeConnectedProvider;
    private @Inject FindService<GameMode> findService;
    private @Inject LobbyNPCActionHandler lobbyNPCActionHandler;

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

            PlayerPuppetEntity playerPuppetEntity = PlayerPuppetEntityBuilder.create(
                    new CoreLocation(
                            selector.getX(),
                            selector.getY(),
                            selector.getZ(),
                            selector.getYaw(),
                            selector.getPitch(),
                            Bukkit.getWorlds().get(0).getName()
                    ),
                    plugin,
                    puppetRegistry
            )
                    .setClickType(ClickAction.Type.RIGHT)
                    .setSkin(new CorePuppetSkin(selector.getValue(), selector.getSignature()))
                    .setAction((p) -> lobbyNPCActionHandler.execute(p.getPlayer(), selector.getMode(), selector.getSubMode()))
                    .build();

            registries.add(new SelectorCompound(playerPuppetEntity, selector));

        }

    }

    @Override
    public void spawnSelectors(Player player) {

        registries.forEach(registry ->
                findService.find(registry.getLobbyNPCSelector().getMode()).callback(response -> {

                    response.ifSuccessful(gameMode -> {

                        String title;

                        String players = "0";

                        if (registry.getLobbyNPCSelector().getSubMode().isEmpty()) {

                            title = messageHandler.get(player, "selectors." +
                                    registry.getLobbyNPCSelector().getMode());

                            players = cloudModeConnectedProvider.getGlobalOnline(gameMode) + "";

                        } else {

                            title = messageHandler.get(player, "selectors." +
                                    registry.getLobbyNPCSelector().getSubMode());

                            if (gameMode.getSubTypes() != null) {

                                for (SubGameMode subType : gameMode.getSubTypes()) {
                                    if (subType.getId().equalsIgnoreCase(
                                            registry.getLobbyNPCSelector().getSubMode())
                                    ) {
                                        players = cloudModeConnectedProvider.getGroupOnline(subType.getGroup()) + "";
                                        break;
                                    }
                                }

                            }

                        }

                        StringList message = messageHandler.replacingMany(
                                player, "selectors.title",
                                "%players%", players,
                                "%title%", title
                        );

                        registry.getNPC().register(player);
                        registry.getNPC().show(player);
                        registry.getNPC().setHolograms(player, message);

                    });
        }));

    }

    @Override
    public void despawnSelectors(Player player) {
        registries.forEach(registry -> registry.getNPC().unregister(player));
    }

    private static class SelectorCompound {

        private final PlayerPuppetEntity NPC;
        private final LobbyNPCSelector lobbyNPCSelector;

        public SelectorCompound(PlayerPuppetEntity NPC, LobbyNPCSelector lobbyNPCSelector) {
            this.NPC = NPC;
            this.lobbyNPCSelector = lobbyNPCSelector;
        }

        public PlayerPuppetEntity getNPC() {
            return NPC;
        }

        public LobbyNPCSelector getLobbyNPCSelector() {
            return lobbyNPCSelector;
        }

    }

}
