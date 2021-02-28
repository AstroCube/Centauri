package net.astrocube.commons.bukkit.nametag;

import net.astrocube.api.bukkit.nametag.Nametag;
import net.astrocube.api.bukkit.nametag.types.AbstractRenderedNametag;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.HashSet;
import java.util.Set;

public final class RenderUtil {

    private RenderUtil() {
        throw new UnsupportedOperationException("Can not instantiate utility");
    }

    public static Set<Nametag.Rendered.Entity> getEntities(Nametag nametag) {

        Location location = nametag.getRecipient().getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();

        EntitySlime slime = new EntitySlime(world);
        slime.setPosition(
                location.getX(),
                location.getY(),
                location.getZ()
        );
        slime.setInvisible(true);
        slime.setSize(-4);

        EntityArmorStand armorstand = new EntityArmorStand(world);
        armorstand.setPosition(
                location.getX(),
                location.getY(),
                location.getZ()
        );
        armorstand.setInvisible(true);
        armorstand.setCustomName(nametag.getTag());
        armorstand.setCustomNameVisible(!nametag.getTag().isEmpty());

        Set<Nametag.Rendered.Entity> spawnedEntities = new HashSet<>();
        spawnedEntities.add(new AbstractRenderedNametag.SimpleEntity(
                "slime",
                slime.getId(),
                slime
        ));
        spawnedEntities.add(new AbstractRenderedNametag.SimpleEntity(
                "stand",
                armorstand.getId(),
                armorstand
        ));

        return spawnedEntities;
    }
}