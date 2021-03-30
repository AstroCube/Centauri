package net.astrocube.lobby.inject;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.astrocube.lobby.Lobby;
import net.jitse.npclib.NPCLib;

public class NPCLibProvider implements Provider<NPCLib> {

    private @Inject Lobby plugin;

    @Override
    public NPCLib get() {
        return new NPCLib(plugin);
    }

}
