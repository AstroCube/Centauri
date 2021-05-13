package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import org.bukkit.plugin.Plugin;

import java.util.Map;

public class TestCommand implements CommandClass {

	private @Inject Plugin plugin;

	@Command(names = {"teststaff"})
	public boolean onCommand() {

		try {


			@Getter
			@AllArgsConstructor
			class DemoRecord {
				private final String name;
				private final PunishmentDoc.Identity.Type type;
			}


			for (Object key : plugin.getConfig().getList("admin.punishments.reasons")) {
				Map<String, Object> linkedKey = (Map<String, Object>) key;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}


		return true;
	}

}
