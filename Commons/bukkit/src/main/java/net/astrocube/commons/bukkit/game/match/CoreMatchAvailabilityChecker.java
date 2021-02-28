package net.astrocube.commons.bukkit.game.match;

import com.google.api.client.http.HttpResponseException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.game.match.MatchAvailabilityChecker;
import net.astrocube.api.bukkit.game.match.MatchStateUpdater;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.cloud.CloudInstanceProvider;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.server.Server;

@Singleton
public class CoreMatchAvailabilityChecker implements MatchAvailabilityChecker {

    private @Inject ActualMatchProvider actualMatchProvider;
    private @Inject FindService<Server> findService;
    private @Inject MatchStateUpdater matchStateUpdater;
    private @Inject CloudInstanceProvider cloudInstanceProvider;

    @Override
    public void clearLegitMatches(String id) throws Exception {

        actualMatchProvider.getRegisteredMatches(id)
                .forEach(match -> {
                    try {

                        Server server = findService.findSync(match.getServer());
                        boolean available = cloudInstanceProvider.isAvailable(server.getSlug());

                        if (!available) {
                            matchStateUpdater.updateMatch(match, MatchDoc.Status.INVALIDATED);
                        }

                    } catch (Exception exception) {

                        if (exception instanceof HttpResponseException) {

                            HttpResponseException responseException = (HttpResponseException) exception;

                            System.out.println(responseException.getStatusCode());

                            if (responseException.getStatusCode() == 404) {
                                try {
                                    matchStateUpdater.updateMatch(match, MatchDoc.Status.INVALIDATED);
                                } catch (Exception ignore) {}
                            }

                        }

                    }

                });
    }

}
