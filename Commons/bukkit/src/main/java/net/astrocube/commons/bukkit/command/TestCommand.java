package net.astrocube.commons.bukkit.command;

import com.google.inject.Inject;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import net.astrocube.api.bukkit.user.staff.OnlineStaffProvider;

public class TestCommand implements CommandClass {

    private @Inject OnlineStaffProvider onlineStaffProvider;

    @Command(names = {"teststaff"})
    public boolean onCommand() {

        try {
            System.out.println(onlineStaffProvider.provide().size());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;
    }

}
