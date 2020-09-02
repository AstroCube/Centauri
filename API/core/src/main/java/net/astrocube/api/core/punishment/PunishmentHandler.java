package net.astrocube.api.core.punishment;

import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

import java.util.List;

public interface PunishmentHandler {

    AsyncResponse<PaginateResult<Punishment>> paginate(String userId, int page, int perPage);

    void createPunishment(
            String issuer,
            String punished,
            String reason,
            Punishment.Type type,
            long expiration,
            boolean automatic,
            boolean silent,
            Callback<Punishment> callback
    );

    AsyncResponse<Punishment> getPunishmentById(String id);

    AsyncResponse<List<Punishment>> getPunishments(PunishmentDoc.Identity.Type type, String playerId);

    AsyncResponse<Punishment> getLastPunishment(PunishmentDoc.Identity.Type  type, String playerId);

    AsyncResponse<Void> updatePunishment(Punishment punishment);

}