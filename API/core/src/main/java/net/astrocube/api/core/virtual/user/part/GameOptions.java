package net.astrocube.api.core.virtual.user.part;

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

    }

    interface Forum {

        boolean isReceivingSubscriptionAlerts();

        boolean isReceivingQuoteAlerts();

    }

}
