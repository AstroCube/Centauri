package net.astrocube.commons.bukkit.punishment.lookup;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.punishment.lookup.PunishmentIconGenerator;
import net.astrocube.api.bukkit.user.display.DisplayMatcher;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.punishment.Punishment;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.commons.core.utils.PrettyTimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

@Singleton
public class CorePunishmentIconGenerator implements PunishmentIconGenerator {

    private @Inject MessageHandler messageHandler;
    private @Inject DisplayMatcher displayMatcher;
    private @Inject FindService<User> findService;
    private @Inject Plugin plugin;

    @Override
    public ItemStack generateFromPunishment(Punishment punishment, Player player, User user) {

        short id = 5;

        switch (punishment.getType()) {
            case KICK: {
                id = 4;
                break;
            }
            case BAN: {
                id = 14;
                break;
            }
            default: {
                break;
            }
        }

        ItemStack stack = new ItemStack(Material.STAINED_CLAY, 1, id);
        ItemMeta meta = stack.getItemMeta();
        String title = messageHandler.get(player, "punish-menu.type." + punishment.getType().toString().toLowerCase());

        String issuer = "Error";
        try {
            User issuerRecord = findService.findSync(punishment.getIssuer());
            issuer = displayMatcher.getDisplay(player, issuerRecord).getColor() + user.getDisplay();
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Could not retrieve user data", e);
        }

        String expiration = punishment.getExpiration() == null ?
                messageHandler.get(player, "punishment-expiration.never") :
                PrettyTimeUtils.getHumanDate(punishment.getExpiration().toDate(), user.getLanguage());

        meta.setDisplayName(
                messageHandler.replacing(
                        player, "punish-menu.lookup.title",
                        "%%type%%", title
                )
        );

        meta.setLore(
                messageHandler.replacingMany(
                        player, "punish-menu.lookup.lore",
                        "%%issuer%%", issuer,
                        "%%reason%%", punishment.getReason(),
                        "%%expires%%", expiration,
                        "%%date%%", PrettyTimeUtils.getHumanDate(punishment.getCreatedAt().toDate(), user.getLanguage())
                )
        );

        stack.setItemMeta(meta);

        return stack;
    }

}
