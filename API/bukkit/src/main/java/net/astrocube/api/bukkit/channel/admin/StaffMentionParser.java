package net.astrocube.api.bukkit.channel.admin;

import java.util.Set;

public interface StaffMentionParser {

	Set<String> parseMentions(String rawMessage);

}