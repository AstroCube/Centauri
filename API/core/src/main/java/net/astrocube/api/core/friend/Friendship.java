package net.astrocube.api.core.friend;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Friendship {

    @Nonnull String getSender();

    @Nonnull String getReceiver();

    @Nullable String getIssuer();

    boolean isAlerted();

    boolean setAlerted(boolean alerted);

}
