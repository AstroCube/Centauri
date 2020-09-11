package net.astrocube.api.bukkit.user;

import net.astrocube.api.core.concurrent.Callback;
import net.astrocube.api.core.virtual.user.User;

public interface UserMatcher {

    void findUserByName(String name, Callback<User> callback);

}