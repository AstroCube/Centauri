package net.astrocube.api.core.punishment;

import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.query.QueryResult;
import net.astrocube.api.core.virtual.punishment.Punishment;

import java.util.Set;

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

    AsyncResponse<QueryResult<Punishment>> getPunishments(Punishment.Type type, String playerId);

    AsyncResponse<Punishment> updatePunishment(Punishment punishment);

    Punishment updatePunishmentSync(Punishment punishment);

    Set<Punishment> getPunishmentsSync(Punishment.Type type, String playerId);

}