package net.astrocube.api.core.virtual.user.part;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface GameOptions {

    AdminChat getAdminChatSettings();

    General getGeneralSettings();

    Forum getForumSettings();

    interface AdminChat {

        boolean isActive();

        boolean isReadingLogs();

        boolean isReadingPunishments();

    }

    interface General {

        boolean isReceivingGifts();

        boolean isReceivingFriendRequests();

        boolean isReceivingParties();

        boolean isHidingStatus();

        boolean isHidingPlayers();

        HideType getHideType();

        enum HideType {
            @JsonProperty("Alone")
            ALONE,
            @JsonProperty("Friendless")
            FRIENDLESS,
            @JsonProperty("Default")
            DEFAULT
        }

    }

    interface Forum {

        boolean isReceivingSubscriptionAlerts();

        boolean isReceivingQuoteAlerts();

    }

}
