package net.astrocube.commons.bukkit.command.punishment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.translation.mode.AlertModes;
import net.astrocube.api.bukkit.util.TimeParser;
import net.astrocube.api.core.punishment.PunishmentHandler;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import net.astrocube.api.core.virtual.user.User;
import org.bukkit.entity.Player;

import java.util.Optional;

public class PunishmentCommandHelper {

	private @Inject PunishmentHandler punishmentHandler;
	private @Inject MessageHandler messageHandler;
	private @Inject QueryService<User> queryService;
	private @Inject ObjectMapper mapper;

	public void processPunishment(Player sender, PunishmentDoc.Identity.Type type, String punished, String compound, boolean silent) {
		queryService.query(PunishmentHandler.findByName(mapper, punished)).callback(response -> {

			if (!response.isSuccessful()) {
				messageHandler.sendIn(sender, AlertModes.ERROR, "punish.error");
			}

			response.ifSuccessful(punish -> {

				Optional<User> user = punish.getFoundModels().stream().findAny();

				if (!user.isPresent()) {
					messageHandler.sendIn(sender, AlertModes.ERROR, "commands.unknown-player");
				}

				user.ifPresent(target -> {

					String reason = compound;
					long expiration = -1;

					if (type == PunishmentDoc.Identity.Type.BAN) {

						String[] splitDuration = compound.split(" ");

						if (splitDuration.length != 0) {

							expiration = TimeParser.parseStringDuration(splitDuration[0]);

							if (expiration != -1) {

								StringBuilder builder = new StringBuilder();

								for (int i = 1; i < splitDuration.length; i++) {
									builder.append(splitDuration[i]);
								}

								reason = builder.toString();

							}

						}

					}

					punishmentHandler.createPunishment(
						sender.getDatabaseIdentifier(),
						target.getId(),
						reason,
						type,
						expiration,
						false,
						silent,
						(punishment, exception) -> {
							if (exception != null) {
								messageHandler.sendIn(sender, AlertModes.ERROR, "punish.error");
							}
						}
					);

				});

			});

		});
	}

}
