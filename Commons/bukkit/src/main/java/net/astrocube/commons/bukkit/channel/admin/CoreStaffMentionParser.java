package net.astrocube.commons.bukkit.channel.admin;

import net.astrocube.api.bukkit.channel.admin.StaffMentionParser;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreStaffMentionParser implements StaffMentionParser {

	@Override
	public Set<String> parseMentions(String rawMessage) {
		Pattern mentionedPattern = Pattern.compile("@(\\S+)");
		Matcher matchedMentions = mentionedPattern.matcher(rawMessage);
		Set<String> mentions = new HashSet<>();
		while (matchedMentions.find()) {
			mentions.add(matchedMentions.group(1));
		}

		return mentions;
	}
}