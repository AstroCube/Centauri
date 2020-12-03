package net.astrocube.commons.core.punishment;

import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

public class CorePunishmentBuilder implements PunishmentBuilder {

    private final String issuer;
    private final String punished;
    private final PunishmentDoc.Identity.Type type;

    private String reason;

    private long duration;

    private CorePunishmentBuilder(String issuer, String punished, PunishmentDoc.Identity.Type type) {
        this.issuer = issuer;
        this.punished = punished;
        this.type = type;
    }

    @Override
    public CorePunishmentBuilder setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public CorePunishmentBuilder setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public void build(PunishmentHandler punishmentHandler, Callback<Punishment> punishmentCallback) {
        punishmentHandler.createPunishment(
                issuer,
                punished,
                reason,
                type,
                duration,
                false,
                false,
                punishmentCallback);
    }

    public static PunishmentBuilder newBuilder(String issuer, String punished, PunishmentDoc.Identity.Type type) {
        return new CorePunishmentBuilder(issuer, punished, type);
    }
}