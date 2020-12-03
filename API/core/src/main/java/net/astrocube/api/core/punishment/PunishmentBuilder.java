package net.astrocube.api.core.punishment;

import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.virtual.punishment.Punishment;

public interface PunishmentBuilder {

    PunishmentBuilder setReason(String reason);

    PunishmentBuilder setDuration(long duration);

    void build(PunishmentHandler punishmentHandler, Callback<Punishment> punishmentCallback);
}
