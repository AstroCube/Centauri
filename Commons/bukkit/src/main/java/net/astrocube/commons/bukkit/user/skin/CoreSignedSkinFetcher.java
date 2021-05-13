package net.astrocube.commons.bukkit.user.skin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.api.bukkit.user.profile.AbstractProperty;
import net.astrocube.api.bukkit.user.profile.CoreProperty;
import net.astrocube.api.bukkit.user.skin.SignedSkinFetcher;
import net.astrocube.api.core.http.HttpClient;
import net.astrocube.api.core.http.RequestOptions;
import net.astrocube.api.core.session.MojangManifest;
import net.astrocube.commons.core.http.CoreRequestCallable;
import net.astrocube.commons.core.http.CoreRequestOptions;
import org.bukkit.plugin.Plugin;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.logging.Level;

@Singleton
public class CoreSignedSkinFetcher implements SignedSkinFetcher {

	private @Inject HttpClient httpClient;
	private @Inject Plugin plugin;
	private @Inject ObjectMapper mapper;

	@Override
	public AbstractProperty fetch(String skin) {

		try {

			MojangManifest response = httpClient.executeRequestSync(
				"https://api.ashcon.app/mojang/v2/user/" + skin,
				new CoreRequestCallable<>(TypeToken.of(MojangManifest.class), mapper),
				new CoreRequestOptions(
					RequestOptions.Type.GET,
					new HashMap<>(),
					"",
					null
				)
			);


			return new CoreProperty(
				"textures",
				response.getTexture().getRaw().getValue(),
				response.getTexture().getRaw().getSignature()
			);

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error parsing texture", e);
		}


		return new AbstractProperty() {
			@Override
			public String getName() {
				return "";
			}

			@Override
			public String getValue() {
				return "";
			}

			@Override
			public String getSignature() {
				return "";
			}

			@Override
			public boolean hasSignature() {
				return false;
			}

			@Override
			public boolean isSignatureValid(PublicKey key) {
				return false;
			}
		};
	}

}
