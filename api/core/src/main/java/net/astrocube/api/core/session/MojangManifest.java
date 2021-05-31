package net.astrocube.api.core.session;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * This is the manifest created by {@author Electroid} in order
 * to add concurrent use of Mojang API.
 * <p>
 * {@url https://github.com/Electroid/mojang-api}
 */
public interface MojangManifest {

	/**
	 * @return user unique id.
	 */
	@JsonProperty("uuid")
	UUID getUniqueId();

	/**
	 * @return username.
	 */
	String getUsername();

	/**
	 * @return set of username legacy record.
	 */
	@JsonProperty("username_history")
	Set<UserHistory> getUserHistory();

	/**
	 * @return user texture record.
	 */
	@JsonProperty("textures")
	Texture getTexture();

	/**
	 * @return if account is legacy.
	 */
	boolean isLegacy();

	/**
	 * @return if account is in demo mode.
	 */
	boolean isDemo();

	/**
	 * @return date where account was created.
	 */
	Date getCreatedAt();

	interface UserHistory {

		/**
		 * @return username of the record.
		 */
		String getUsername();

		/**
		 * @return where username was changed at.
		 */
		@JsonProperty("changed_at")
		Date getChangedAt();

	}

	interface Texture {

		/**
		 * @return if using slime version of skin.
		 */
		boolean isSlim();

		/**
		 * @return if skin is custom.
		 */
		boolean isCustom();

		/**
		 * @return skin registry.
		 */
		Skin getSkin();

		/**
		 * @return cape registry.
		 */
		Cape getCape();

		/**
		 * @return data of skin.
		 */
		Raw getRaw();

		interface Skin {

			/**
			 * @return minecraft URL stored skin.
			 */
			@JsonProperty("url")
			String getURL();

			/**
			 * @return data of minecraft stored skin.
			 */
			String getData();

		}

		interface Cape {

			/**
			 * @return minecraft URL of stored cape.
			 */
			@JsonProperty("url")
			String getURL();

			/**
			 * @return data of minecraft stored cape.
			 */
			String getData();

		}

		interface Raw {

			/**
			 * @return value of the skin.
			 */
			String getValue();

			/**
			 * @return mojang signature of the skin.
			 */
			String getSignature();

		}

	}

}
