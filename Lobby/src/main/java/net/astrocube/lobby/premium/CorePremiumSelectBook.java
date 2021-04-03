package net.astrocube.lobby.premium;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumSelectBook;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.upperlevel.spigot.book.BookUtil;

@Singleton
public class CorePremiumSelectBook implements PremiumSelectBook {

    private @Inject MessageHandler messageHandler;

    @Override
    public void displayEnable(Player player) {

        BookUtil.openPlayer(
                player,
                BookUtil.writtenBook()
                        .pages(new BookUtil.PageBuilder()
                                .add(messageHandler.get(player, "premium.enable.book"))
                                .add(new TextComponent("\n\n"))
                                .add(BookUtil.TextBuilder.of(messageHandler.get(player, "premium.enable.button"))
                                        .onHover(BookUtil.HoverAction.showText(messageHandler.get(player, "premium.enable.hover")))
                                        .onClick(BookUtil.ClickAction.runCommand("/premium confirm"))
                                        .color(ChatColor.DARK_GREEN)
                                        .build())
                                .build()
                        )
                        .build()
        );

    }

    @Override
    public void displayDisable(Player player) {

        BookUtil.openPlayer(
                player,
                BookUtil.writtenBook()
                        .pages(new BookUtil.PageBuilder()
                                .add(messageHandler.get(player, "premium.disable.book"))
                                .add(new TextComponent("\n\n"))
                                .add(BookUtil.TextBuilder.of(messageHandler.get(player, "premium.disable.button"))
                                        .onHover(BookUtil.HoverAction.showText(messageHandler.get(player, "premium.disable.hover")))
                                        .onClick(BookUtil.ClickAction.runCommand("/premium confirm"))
                                        .color(ChatColor.RED)
                                        .build())
                                .build()
                        )
                        .build()
        );

    }

}
