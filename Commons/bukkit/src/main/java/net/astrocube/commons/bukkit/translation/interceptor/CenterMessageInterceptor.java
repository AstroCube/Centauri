package net.astrocube.commons.bukkit.translation.interceptor;

import me.yushust.message.core.intercept.InterceptContext;
import me.yushust.message.core.intercept.MessageInterceptor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CenterMessageInterceptor implements MessageInterceptor<Player> {

    private static final String CENTER_KEYWORD = "%%center%%";

    private String interceptOneLine(String text) {
        if (!text.startsWith(CENTER_KEYWORD)) {
            return text;
        }
        text = text.substring(CENTER_KEYWORD.length());
        return ChatCenter.getCenteredMessage(text);
    }

    @Override
    public @NotNull String replace(InterceptContext<Player> interceptContext, String text) {
        String[] args = text.split("\n");

        for (int i = 0; i < args.length; i++) {
            args[i] = interceptOneLine(args[i]);
        }

        return String.join("\n", args);
    }
}