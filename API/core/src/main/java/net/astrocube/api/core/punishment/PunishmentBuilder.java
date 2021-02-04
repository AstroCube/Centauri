package net.astrocube.api.core.punishment;

import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;

public interface PunishmentBuilder {

    String getReason();

    PunishmentBuilder setReason(String reason);

    long getDuration();

    PunishmentBuilder setDuration(long duration);

    User getIssuer();

    User getTarget();

    PunishmentDoc.Identity.Type getType();

    void setType(PunishmentDoc.Identity.Type type);

    void build(PunishmentHandler punishmentHandler, Callback<Punishment> punishmentCallback);

}
