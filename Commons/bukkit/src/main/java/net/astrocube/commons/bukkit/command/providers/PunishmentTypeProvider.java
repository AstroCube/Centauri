package net.astrocube.commons.bukkit.command.providers;

import me.fixeddev.ebcm.NamespaceAccesor;
import me.fixeddev.ebcm.parameter.provider.SingleArgumentProvider;
import me.fixeddev.ebcm.part.CommandPart;

import net.astrocube.api.core.virtual.punishment.PunishmentDoc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PunishmentTypeProvider implements SingleArgumentProvider<PunishmentDoc.Identity.Type> {

    @Override
    public Result<PunishmentDoc.Identity.Type> transform(String input, NamespaceAccesor namespaceAccesor, CommandPart commandPart) {
        if (input == null) {
            return Result.createResult(PunishmentDoc.Identity.Type.WARN);
        }

        return Result.createResult(PunishmentDoc.Identity.Type.valueOf(input.toUpperCase()));
    }

    @Override
    public List<String> getSuggestions(String startsWith) {
        String finalPrefix = startsWith.toUpperCase();

        return Arrays.stream(PunishmentDoc.Identity.Type.values())
                .map(type -> type.name().toUpperCase())
                .filter(name -> finalPrefix.length() == 0 || name.startsWith(finalPrefix))
                .collect(Collectors.toList());
    }

}