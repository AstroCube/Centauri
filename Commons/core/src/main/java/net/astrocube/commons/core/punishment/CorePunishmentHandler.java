package net.astrocube.commons.core.punishment;

import com.google.inject.Inject;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.utils.Callbacks;
import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

public class CorePunishmentHandler implements PunishmentHandler {

    private @Inject CreateService<Punishment, PunishmentDoc.Partial> createService;

    @Override
    public AsyncResponse<PaginateResult<Punishment>> paginate(String userId, int page, int perPage) {
        return null;
    }

    @Override
    public void createPunishment(
            String issuer,
            String punished,
            String reason,
            Punishment.Type type,
            long expiration,
            boolean automatic,
            boolean silent,
            Callback<Punishment> callback
    ) {
        PunishmentDoc.Partial punishment = new PunishmentDoc.Creation() {

            @Override
            public DateTime getExpiration() {
                return PunishmentHandler.generateFromExpiration(expiration);
            }

            @Override
            public DateTime getCreatedAt() {
                return new DateTime();
            }

            @Override
            public DateTime getUpdatedAt() {
                return new DateTime();
            }

            @Override
            public String getId() {
                return UUID.randomUUID().toString();
            }

            @Override
            public boolean isAutomatic() {
                return automatic;
            }

            @Override
            public boolean isSilent() {
                return silent;
            }

            @Override
            public String getReason() {
                return reason;
            }

            @Override
            public String getIssuer() {
                return issuer;
            }

            @Override
            public String getPunished() {
                return punished;
            }

            @Override
            public Type getType() {
                return type;
            }
        };

        createService.create(punishment).callback(
                Callbacks.applyCommonErrorHandler("Punishment creation", callback)
        );
    }

    @Override
    public AsyncResponse<Punishment> getPunishmentById(String id) {
        return null;
    }

    @Override
    public AsyncResponse<List<Punishment>> getPunishments(PunishmentDoc.Identity.Type type, String playerId) {
        return null;
    }

    @Override
    public AsyncResponse<Punishment> getLastPunishment(PunishmentDoc.Identity.Type type, String playerId) {
        return null;
    }

    @Override
    public AsyncResponse<Void> updatePunishment(Punishment punishment) {
        return null;
    }

}