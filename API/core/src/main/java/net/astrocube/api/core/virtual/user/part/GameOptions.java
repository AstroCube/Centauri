package net.astrocube.api.core.virtual.user.part;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface GameOptions {

	@JsonProperty("adminChat")
	AdminChat getAdminChatSettings();

	@JsonProperty("general")
	General getGeneralSettings();

	@JsonProperty("forum")
	Forum getForumSettings();

	interface AdminChat {

		boolean isActive();

		void setActive(boolean active);

		@JsonProperty("logs")
		boolean isReadingLogs();

		@JsonProperty("logs")
		void setReadingLogs(boolean active);

		@JsonProperty("punishments")
		boolean isReadingPunishments();

		@JsonProperty("punishments")
		void setReadingPunishments(boolean active);

	}

	interface General {

		@JsonProperty("gifts")
		boolean isReceivingGifts();

		@JsonProperty("friends")
		boolean isReceivingFriendRequests();

		@JsonProperty("parties")
		boolean isReceivingParties();

		@JsonProperty("status")
		boolean isHidingStatus();

		@JsonProperty("hiding")
		boolean isHidingPlayers();

		void setHidingPlayers(boolean hidingPlayers);

		HideType getHideType();

		void setHideType(HideType hideType);

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

		@JsonProperty("subscribe")
		boolean isReceivingSubscriptionAlerts();

		@JsonProperty("quoteAlert")
		boolean isReceivingQuoteAlerts();

	}

}
