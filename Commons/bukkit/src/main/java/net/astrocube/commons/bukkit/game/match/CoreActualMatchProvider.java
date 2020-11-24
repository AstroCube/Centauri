package net.astrocube.commons.bukkit.game.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.game.match.ActualMatchProvider;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.service.query.QueryService;

import java.util.Comparator;
import java.util.Optional;

@Singleton
public class CoreActualMatchProvider implements ActualMatchProvider {

    private @Inject QueryService<Match> findService;
    private @Inject ObjectMapper objectMapper;

    @Override
    public Optional<Match> provide(String id) throws Exception {

        //TODO: Do this in a better way may maybe
        ObjectNode query = (ObjectNode) objectMapper.readTree("{\n" +
                "\"$or\": [" +
                "{\"spectators\": " + id + "}," +
                "{\"pending\":" +
                "{\"responsible\": " + id + "}" +
                "},\n" +
                "{\"pending\": {\"involved\": " + id + "}}" +
                "],\n" +
                "\"status\": {\"$nin\": [\"Finished\", \"Invalidated\"]}" +
                "}");

        ;

        return findService.querySync(query)
                .getFoundModels()
                .stream().max(Comparator.comparing(Model.Stamped::getCreatedAt));
    }

}
