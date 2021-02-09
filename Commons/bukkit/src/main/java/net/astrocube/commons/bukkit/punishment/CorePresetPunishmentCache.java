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

    @Override
    public void generateCache() {

        for (Object key : plugin.getConfig().getList("admin.punishments.reasons")) {

            Map<String, Object> linkedKey = (Map<String, Object>) key;

            try {

                PunishmentDoc.Identity.Type type = PunishmentDoc.Identity.Type.valueOf((String) linkedKey.get("type"));

                punishments.add(new PresetPunishment() {
                    @Override
                    public String getId() {
                        return (String) linkedKey.get("name");
                    }

                    @Override
                    public PunishmentDoc.Identity.Type getType() {
                        return type;
                    }

                    @Override
                    public long getExpiration() {
                        return type == PunishmentDoc.Identity.Type.BAN &&
                                ((String) linkedKey.get("expiration")).equalsIgnoreCase("-1") ?
                            TimeParser.parseStringDuration((String) linkedKey.get("expiration")) : -1;
                    }
                });

            } catch (IllegalArgumentException ignore) {}

        }


        cached = true;
    }

}
