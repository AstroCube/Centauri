package net.astrocube.commons.bukkit.command.punishment;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Flag;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.astrocube.api.core.virtual.punishment.PunishmentDoc;
import org.bukkit.entity.Player;

public class PunishmentCommand implements CommandClass {

    private @Inject PunishmentCommandHelper punishmentCommandHelper;


    @Command(names = {"ban", "tb", "tempban", "permaban", "pb"}, permission = "commons.staff.punish.ban")
    public boolean onBanCommand(
            @Sender Player sender,
            String punished,
            @Text @OptArg("") String compound,
            @Flag("s") boolean silent
    ) {
        punishmentCommandHelper.processPunishment(
                sender,
                PunishmentDoc.Identity.Type.BAN,
                punished,
                compound,
                silent
        );
        return true;
    }

    @Command(names = {"kick", "expulse", "k"}, permission = "commons.staff.punish.kick")
    public boolean onKickCommand(
            @Sender Player sender,
            String punished,
            @Text @OptArg("") String compound,
            @Flag("s") boolean silent
    ) {
        punishmentCommandHelper.processPunishment(
                sender,
                PunishmentDoc.Identity.Type.KICK,
                punished,
                compound,
                silent
        );
        return true;
    }

    @Command(names = {"warn", "warning"}, permission = "commons.staff.punish.warn")
    public boolean onWarnCommand(
            @Sender Player sender,
            String punished,
            @Text @OptArg("") String compound,
            @Flag("s") boolean silent
    ) {
        punishmentCommandHelper.processPunishment(
                sender,
                PunishmentDoc.Identity.Type.WARN,
                punished,
                compound,
                silent
        );
        return true;
    }


}
