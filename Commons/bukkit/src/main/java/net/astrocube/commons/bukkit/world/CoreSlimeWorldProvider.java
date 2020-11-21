package net.astrocube.commons.bukkit.world;

import net.astrocube.api.bukkit.world.SlimeWorldProvider;
import net.astrocube.slime.api.SlimeWorldManager;
import net.astrocube.slime.api.exceptions.*;
import net.astrocube.slime.api.world.SlimeWorld;
import net.astrocube.slime.api.world.properties.SlimePropertyMap;
import net.astrocube.slime.core.CoreSlimeWorldManager;

import java.io.File;
import java.io.IOException;

public class CoreSlimeWorldProvider implements SlimeWorldProvider {

    private final SlimeWorldManager slimeWorldManager;

    public CoreSlimeWorldProvider(String folder) {
        this.slimeWorldManager = new CoreSlimeWorldManager(folder);
    }

    @Override
    public SlimeWorld importWorld(File file, String name, boolean editable)
            throws WorldTooBigException, InvalidWorldException, IOException,
            WorldAlreadyExistsException, WorldLoadedException, NewerFormatException,
            CorruptedWorldException, WorldInUseException, UnknownWorldException {

        slimeWorldManager.importWorld(file, name);
        SlimeWorld world = slimeWorldManager.loadWorld(name, editable, new SlimePropertyMap());
        slimeWorldManager.generateWorld(world);

        return world;
    }

    @Override
    public SlimeWorld loadWorld(String name, boolean editable) throws NewerFormatException, CorruptedWorldException, WorldInUseException, UnknownWorldException, IOException {

        SlimeWorld world = slimeWorldManager.loadWorld(name, editable, new SlimePropertyMap());
        slimeWorldManager.generateWorld(world);

        return world;
    }
}
