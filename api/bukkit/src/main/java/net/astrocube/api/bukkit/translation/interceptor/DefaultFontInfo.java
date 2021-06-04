package net.astrocube.api.bukkit.translation.interceptor;

public enum DefaultFontInfo {

	SIX_SYMBOLS(6, '@'),
	FIVE_SYMBOLS(5, 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'J', 'j', 'K',
		'L', 'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 'S', 's', 'T', 'U', 'u',
		'V', 'v', 'W', 'w', 'X', 'x', 'Y', 'y', 'Z', 'z', 'G', 'g', 'H', 'h', '1', '2', '3', '4',
		'5', '6', '7', '8', '9', '0', '#', '$', '%', '^', '&', '*', '-', '_', '+', '=', '?', '/',
		'\\', '~'),
	FOUR_SYMBOLS(4, 'f', 'k', 't', '(', ')', '{', '}', '<', '>'),
	THREE_SYMBOLS(3, '[', ']', '"', ' ', 'I'),
	TWO_SYMBOLS(2, '`'),
	ONE_SYMBOLS(1, ':', ';', '\'', '|', '.', ',', 'i', 'l', '!'),
	DEFAULT(4);

	private final char[] characters;
	private final int length;

	DefaultFontInfo(int length, char... characters) {
		this.characters = characters;
		this.length = length;
	}

	public char[] getCharacters() {
		return this.characters;
	}

	public int getLength() {
		return this.length;
	}

	public int getBoldLength(char c) {
		if (c == ' ') {
			return this.getLength();
		}
		return this.length + 1;
	}

	public static DefaultFontInfo getDefaultFontInfo(char c) {
		for (DefaultFontInfo fontInfo : DefaultFontInfo.values()) {
			for (char character : fontInfo.getCharacters()) {
				if (character == c) {
					return fontInfo;
				}
			}
		}
		return DefaultFontInfo.DEFAULT;
	}
}