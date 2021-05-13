package net.astrocube.api.core.punishment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.BiConsumer;

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
		BiConsumer<Punishment, Exception> callback
	);

	AsyncResponse<Punishment> getPunishmentById(String id);

	AsyncResponse<List<Punishment>> getPunishments(PunishmentDoc.Identity.Type type, String playerId);

	AsyncResponse<Punishment> getLastPunishment(PunishmentDoc.Identity.Type type, String playerId);

	AsyncResponse<Void> updatePunishment(Punishment punishment);

	static LocalDateTime generateFromExpiration(long expiration) {

		if (expiration == -1) {
			return null;
		}

		Calendar timeout = Calendar.getInstance();
		timeout.setTimeInMillis(
			new Date().getTime() + expiration
		);

		return LocalDateTime.now()
			.plus(expiration, ChronoUnit.MILLIS);
	}

	static ObjectNode findByName(ObjectMapper mapper, String name) {
		ObjectNode node = mapper.createObjectNode();
		node.put("username", name);
		return node;
	}

}