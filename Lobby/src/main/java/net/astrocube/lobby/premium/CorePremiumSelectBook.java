package net.astrocube.lobby.premium;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.lobby.premium.PremiumSelectBook;
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
                                .add(BookUtil.TextBuilder.of(messageHandler.get(player, "premium.enable.button"))
                                        .onHover(BookUtil.HoverAction.showText(messageHandler.get(player, "premium.enable.hover")))
                                        .onClick(BookUtil.ClickAction.runCommand("/premium confirm"))
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
                                .add(BookUtil.TextBuilder.of(messageHandler.get(player, "premium.disable.button"))
                                        .onHover(BookUtil.HoverAction.showText(messageHandler.get(player, "premium.disable.hover")))
                                        .onClick(BookUtil.ClickAction.runCommand("/premium confirm"))
                                        .build())
                                .build()
                        )
                        .build()
        );

    }

}
