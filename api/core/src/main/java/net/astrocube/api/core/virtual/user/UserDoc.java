package net.astrocube.api.core.virtual.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.astrocube.api.core.model.Model;
import net.astrocube.api.core.model.PartialModel;
import net.astrocube.api.core.virtual.group.Group;
import net.astrocube.api.core.virtual.user.part.GameOptions;
import net.astrocube.api.core.virtual.user.part.PublicInformation;

import javax.annotation.Nullable;
import java.util.Set;

public interface UserDoc {

	interface Partial extends PartialModel {
	}


	interface Login extends Partial {

		String getUsername();

		String getPassword();

	}


	interface UserGroup {

		Group getGroup();

		String getJoinedAt();

		String getRoleComment();

	}


	interface Identity {

		String getDisplay();

		String getMail();

		Set<UserGroup> getGroups();

		@Nullable
		String getSkin();

		boolean isVerified();

		Session getSession();

		int getExperience();

		Set<Address> getAddresses();

		String getLanguage();

		void setLanguage(String language);

		PublicInformation getPublicInfo();

		GameOptions getSettings();

	}


	interface Session {

		String getLastSeen();

		boolean isOnline();

		String getLastGame();

		String getLastLobby();

		String getLastReplyId();

		void setLastReplyId(String lastReplyId);

		@JsonProperty("authorize")
		Authorization getAuthorizeMethod();

		@JsonProperty("authorize")
		void setAuthorizeMethod(Authorization authorization);

		enum Authorization {
			@JsonProperty("Password")
			PASSWORD,
			@JsonProperty("Premium")
			PREMIUM
		}

	}

	interface Address {

		String getCountry();

		String getNumber();

		boolean isPrimary();

	}


	interface Complete extends Model.Stamped, Login, Identity {
	}

}