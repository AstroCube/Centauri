package net.astrocube.commons.core.punishment;

import net.astrocube.api.core.punishment.PunishmentBuilder;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;

import java.util.function.BiConsumer;

public class CorePunishmentBuilder implements PunishmentBuilder {

    private final User issuer;
    private final User punished;
    private PunishmentDoc.Identity.Type type;

    private String reason;

    private long duration;

    private CorePunishmentBuilder(User issuer, User punished, PunishmentDoc.Identity.Type type) {
        this.issuer = issuer;
        this.punished = punished;
        this.type = type;
        this.duration = -1;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public CorePunishmentBuilder setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public CorePunishmentBuilder setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public User getIssuer() {
        return issuer;
    }

    @Override
    public User getTarget() {
        return punished;
    }

    @Override
    public PunishmentDoc.Identity.Type getType() {
        return type;
    }

    @Override
    public void setType(PunishmentDoc.Identity.Type type) {
        this.type = type;
    }

    @Override
    public void build(PunishmentHandler punishmentHandler, BiConsumer<Punishment, Exception> punishmentCallback) {
        punishmentHandler.createPunishment(
                issuer.getId(),
                punished.getId(),
                reason,
                type,
                duration,
                false,
                false,
                punishmentCallback);
    }

    public static PunishmentBuilder newBuilder(User issuer, User punished, PunishmentDoc.Identity.Type type) {
        return new CorePunishmentBuilder(issuer, punished, type);
    }
}