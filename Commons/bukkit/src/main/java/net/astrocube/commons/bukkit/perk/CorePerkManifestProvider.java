package net.astrocube.commons.bukkit.perk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.perk.AbstractPerk;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.perk.StorablePerk;
import net.astrocube.api.core.virtual.perk.StorablePerkDoc;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CorePerkManifestProvider implements PerkManifestProvider {

    private @Inject CreateService<StorablePerk, StorablePerkDoc.Partial> createService;
    private @Inject QueryService<StorablePerk> queryService;
    private @Inject UpdateService<StorablePerk, StorablePerkDoc.Partial> updateService;
    private @Inject ObjectMapper mapper;

    @Override
    public <T extends AbstractPerk> void createRegistry(
            String playerId, String gameMode, @Nullable String subMode,
            String type, T perk) throws Exception {

        createService.createSync(new StorablePerkDoc.Creation() {
            @Override
            public String getGameMode() {
                return gameMode;
            }

            @Nullable
            @Override
            public String getSubGameMode() { return subMode; }

            @Override
            public String getResponsible() {
                return playerId;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public Object getStored() {
                return perk;
            }

            @Override
            public void setStored(Object updatable) {}
        });
    }

    @Override
    public <T extends AbstractPerk> Set<StorablePerk> query(ObjectNode query, Class<T> genericClass) throws Exception {
        return queryService.querySync(query).getFoundModels()
                .stream()
                .peek(perk -> perk.setStored(transformPerk(perk, genericClass)))
                .collect(Collectors.toSet());
    }

    @Override
    public <T extends AbstractPerk> T transformPerk(StorablePerk perk, Class<T> genericClass) {
        return mapper.convertValue(perk.getStored(), genericClass);
    }

    @Override
    public void update(String playerId, StorablePerk manifest) throws Exception {
        updateService.update(manifest);
    }
}
