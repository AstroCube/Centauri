package net.astrocube.commons.bukkit.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.api.bukkit.server.broadcast.GlobalBroadcaster;
import org.bukkit.entity.Player;

@Command(names = {
	"global",
	"g"
},
	permission = "admin.broadcast"
)
public class GlobalBroadcastCommand implements CommandClass {

	@Inject
	private GlobalBroadcaster globalBroadcaster;

	@Command(names = "")
	public void main(@Sender Player player, @Text String content) throws JsonProcessingException {
		globalBroadcaster.broadcastMessage(content);
	}

}