package net.astrocube.api.bukkit.translation;

public enum LanguageType {

	ES("es", "32bd4521983309e0ad76c1ee29874287957ec3d96f8d889324da8c887e485ea8")

	;

	private final String abbreviation;
	private final String skullUrl;

	LanguageType(String abbreviation, String skullUrl) {
		this.abbreviation = abbreviation;
		this.skullUrl = skullUrl;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getSkullUrl() {
		return skullUrl;
	}
}