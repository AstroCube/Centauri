package net.astrocube.commons.bukkit.punishment;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.punishment.PresetPunishment;
import net.astrocube.api.bukkit.punishment.PresetPunishmentCache;
import net.astrocube.api.bukkit.util.TimeParser;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class CorePresetPunishmentCache implements PresetPunishmentCache {

    private @Inject Plugin plugin;
    private final Set<PresetPunishment> punishments = new HashSet<>();
    private boolean cached = false;

    @Override
    public Set<PresetPunishment> getPunishments() {

        if (!cached) {
            generateCache();
        }

        return punishments;
    }

    @Override
    public Set<PresetPunishment> getPunishments(PunishmentDoc.Identity.Type type) {
        return getPunishments().stream().filter(p -> p.getType() == type).collect(Collectors.toSet());
    }

    private void generateCache() {

        for (Object key : plugin.getConfig().getList("admin.punishments.reasons")) {

            Map<String, Object> linkedKey = (Map<String, Object>) key;

            try {

                punishments.add(new PresetPunishment() {
                    @Override
                    public String getId() {
                        return (String) linkedKey.get("name");
                    }

                    @Override
                    public PunishmentDoc.Identity.Type getType() {
                        return PunishmentDoc.Identity.Type.valueOf((String) linkedKey.get("type"));
                    }

                    @Override
                    public long getExpiration() {
                        return TimeParser.parseStringDuration((String) linkedKey.get("expiration"));
                    }
                });

            } catch (IllegalArgumentException ignore) {}

        }

        cached = true;
    }

}
