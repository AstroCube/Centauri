package net.astrocube.commons.core.punishment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.inject.Inject;

import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.service.paginate.PaginateService;
import net.astrocube.api.core.service.query.QueryResult;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.commons.core.utils.Callbacks;

import org.joda.time.DateTime;

import java.util.Set;
import java.util.UUID;

public class CorePunishmentHandler implements PunishmentHandler {

    private @Inject CreateService<Punishment, PunishmentDoc.Partial> createService;
    private @Inject PaginateService<Punishment> paginateService;
    private @Inject QueryService<Punishment> queryService;
    private @Inject FindService<Punishment> findService;
    private @Inject UpdateService<Punishment, Punishment> updateService;
    private @Inject ObjectMapper objectMapper;

    @Override
    public AsyncResponse<PaginateResult<Punishment>> paginate(String userId, int page, int perPage) {

        ObjectNode filter = objectMapper.createObjectNode();

        filter.putArray("$or")
                .add(objectMapper.createObjectNode().put("punished", userId));

        return paginateService.paginate("?page=" + page + "&perPage=" + perPage, filter);
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
        Punishment punishment = new Punishment() {
            @Override
            public boolean isAppealed() {
                return false;
            }

            @Override
            public boolean isActive() {
                return true;
            }

            @Override
            public DateTime getExpiration() {
                return new DateTime(expiration);
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
        return findService.find(id);
    }

    @Override
    public AsyncResponse<QueryResult<Punishment>> getPunishments(Punishment.Type type, String playerId) {
        try {
            return queryService
                    .query(objectMapper
                            .createObjectNode()
                            .put("punished", playerId)
                            .put("type", type.name())
                    );
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while getting punishments to " + playerId);
        }
    }

    @Override
    public AsyncResponse<Punishment> updatePunishment(Punishment punishment) {
        return updateService.update(punishment);
    }

    @Override
    public Punishment updatePunishmentSync(Punishment punishment) {
        try {
            return updateService.updateSync(punishment);
        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while updating punishment " + punishment.getId());
        }
    }

    @Override
    public Set<Punishment> getPunishmentsSync(Punishment.Type type, String playerId) {
        try {
            return queryService
                    .querySync(objectMapper
                            .createObjectNode()
                            .put("punished", playerId)
                            .put("type", type.name())
                    )
                    .getFoundModels();

        } catch (Exception e) {
            throw new RuntimeException("An error has occurred while getting punishments to " + playerId);
        }
    }


}