package net.astrocube.commons.bukkit.punishment;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.punishment.event.PunishmentIssueEvent;
import net.astrocube.api.core.cloud.InstanceNameProvider;
import net.astrocube.api.core.concurrent.AsyncResponse;
import net.astrocube.api.core.message.Channel;
import net.astrocube.api.core.message.Messenger;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.service.create.CreateService;
import net.astrocube.api.core.service.paginate.PaginateResult;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class CorePunishmentHandler implements PunishmentHandler {

	private @Inject CreateService<Punishment, PunishmentDoc.Partial> createService;
	private @Inject InstanceNameProvider instanceNameProvider;
	private final Channel<Punishment> punishmentChannel;

	@Inject
	public CorePunishmentHandler(Messenger jedisMessenger) {
		punishmentChannel = jedisMessenger.getChannel(Punishment.class);
	}

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
		BiConsumer<Punishment, Exception> callback
	) {

		System.out.println("Expiration " + expiration);
		LocalDateTime localDateTime = PunishmentHandler.generateFromExpiration(expiration);



		PunishmentDoc.Partial partial = new PunishmentDoc.Creation() {

			@Override
			public @Nullable
			LocalDateTime getExpiration() {
				return expiration == 0 ? null : localDateTime;
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
			public String getServer() {
				return instanceNameProvider.getName();
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

		try {
			Punishment punishment = createService.createSync(partial);
			punishmentChannel.sendMessage(punishment, new HashMap<>());
			Bukkit.getPluginManager().callEvent(new PunishmentIssueEvent(punishment));
			callback.accept(punishment, null);
		} catch (Exception e) {
			callback.accept(null, e);
		}

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