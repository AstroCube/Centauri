package net.astrocube.commons.bukkit.user;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.inject.Inject;

import net.astrocube.api.bukkit.user.UserMatcher;
import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.service.query.QueryService;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.Callbacks;

public class CoreUserMatcher implements UserMatcher {

    private @Inject QueryService<User> userQueryService;
    private @Inject ObjectMapper objectMapper;

    @Override
    public void findUserByName(String name, Callback<User> callback) {
        userQueryService
                .query(objectMapper.createObjectNode()
                        .put("username", name)
                )
                .callback(Callbacks.applyCommonErrorHandler("user-matcher", object -> {
                            User user = object
                                    .getFoundModels()
                                    .iterator()
                                    .next();

                            callback.call(user);
                        })
                );
    }

    @Override
    public User findUserByName(String name) {
        try {
            return userQueryService
                    .querySync(objectMapper.createObjectNode()
                            .put("username", name)
                    )
                    .getFoundModels()
                    .iterator()
                    .next();
        } catch (Exception e) {
            return null;
        }
    }

}