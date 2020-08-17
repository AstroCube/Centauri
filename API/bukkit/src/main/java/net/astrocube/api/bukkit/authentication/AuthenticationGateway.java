package net.astrocube.api.bukkit.authentication;

public interface AuthenticationGateway {

    void startProcessing();

    void generateSession();

    String getName();

}
