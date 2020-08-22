package net.astrocube.lobby.hide.collection;

import net.astrocube.api.bukkit.lobby.hide.HideCompound;

public class DefaultCompound {

    public static HideCompound aloneCompound() {
        return createCompound(true, true, true);
    }

    public static HideCompound friendlessCompound() {
        return createCompound(true, false, false);
    }

    public static HideCompound defaultCompound() {
        return createCompound(false, false, false);
    }

    public static HideCompound createCompound(boolean friends, boolean staff, boolean permission) {
        return new HideCompound() {
            @Override
            public boolean friends() {
                return friends;
            }

            @Override
            public boolean staff() {
                return staff;
            }

            @Override
            public boolean permission() {
                return permission;
            }
        };
    }

}
