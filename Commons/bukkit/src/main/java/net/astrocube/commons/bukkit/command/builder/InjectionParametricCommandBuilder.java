package net.astrocube.commons.bukkit.command.builder;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.fixeddev.ebcm.Command;
import me.fixeddev.ebcm.CommandAction;
import me.fixeddev.ebcm.CommandContext;
import me.fixeddev.ebcm.CommandData;
import me.fixeddev.ebcm.ImmutableCommand;
import me.fixeddev.ebcm.exception.CommandException;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.ParametricCommandBuilder;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.ConsumedArgs;
import me.fixeddev.ebcm.parametric.annotation.Flag;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.ModifierAnnotation;
import me.fixeddev.ebcm.parametric.annotation.Named;
import me.fixeddev.ebcm.parametric.annotation.Optional;
import me.fixeddev.ebcm.parametric.annotation.ParentArg;
import me.fixeddev.ebcm.parametric.annotation.Required;
import me.fixeddev.ebcm.parametric.annotation.SubCommandClasses;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import me.fixeddev.ebcm.part.ArgumentPart;
import me.fixeddev.ebcm.part.CommandPart;
import me.fixeddev.ebcm.part.FlagPart;
import me.fixeddev.ebcm.part.InjectedValuePart;
import me.fixeddev.ebcm.part.SubCommandPart;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectionParametricCommandBuilder implements ParametricCommandBuilder {

    private @Inject Injector injector;

    @Override
    public Command fromMethod(CommandClass commandClass, Method method) {
        ACommand commandAnnotation = method.getAnnotation(ACommand.class);

        if (commandAnnotation == null) {
            throw new IllegalArgumentException("The provided method isn't annotated with an ACommand annotation");
        }

        if (!Modifier.isPublic(method.getModifiers())) {
            throw new IllegalArgumentException("The provided method doesn't has public visibility!");
        }

        if (method.getReturnType() != boolean.class && method.getReturnType() != Boolean.class) {
            throw new IllegalArgumentException("The provided method doesn't return a boolean value!");
        }

        String[] names = commandAnnotation.names();

        ImmutableCommand.Builder commandBuilder = ImmutableCommand.builder(
                //
                CommandData.builder(names[0])
                        .setDescription(commandAnnotation.desc())
                        .setAliases(Arrays.asList(Arrays.copyOfRange(names, 1, names.length)))
                //
        ).setPermission(commandAnnotation.permission())
                .setPermissionMessage(commandAnnotation.permissionMessage());

        for (Parameter parameter : method.getParameters()) {
            if(parameter.getType() == CommandContext.class){
                continue;
            }

            CommandPart part = fromParameter(parameter);

            if (part == null) {
                continue;
            }

            commandBuilder.addPart(part);
        }

        commandBuilder.setAction(actionOfMethod(commandClass, method));

        Usage usage = method.getAnnotation(Usage.class);
        if (usage != null && !usage.usage()[0].equals("_!!_NOT_OVERRIDE_!!_")) {
            commandBuilder.setUsage(String.join("\n", usage.usage()));
        }


        return commandBuilder.build();
    }

    @Override
    public List<Command> fromClass(CommandClass commandClass) {
        Class<?> clazz = commandClass.getClass();

        ACommand commandAnnotation = clazz.getAnnotation(ACommand.class);

        Map<String, Command> commands = new HashMap<>();
        List<Command> commandList = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers()) || !Modifier.isPublic(method.getModifiers())) {
                continue;
            }

            if (method.getReturnType() != boolean.class && method.getReturnType() != Boolean.class) {
                continue;
            }

            if (!method.isAnnotationPresent(ACommand.class)) {
                continue;
            }

            Command command = fromMethod(commandClass, method);

            commands.put(command.getData().getName(), command);
            commandList.add(command);
        }

        if (commandAnnotation != null) {
            Command command = commands.get("");

            String[] names = commandAnnotation.names();

            CommandData.Builder dataBuilder = CommandData.builder(commandAnnotation.names()[0])
                    .setAliases(Arrays.asList(Arrays.copyOfRange(names, 1, names.length)))
                    .setDescription(commandAnnotation.desc());

            ImmutableCommand.Builder builder = ImmutableCommand.builder(dataBuilder);

            builder.setPermission(commandAnnotation.permission())
                    .setPermissionMessage(commandAnnotation.permissionMessage());

            if (command != null) {
                commands.remove("");
                commandList.remove(command);

                builder.setUsage(command.getUsage())
                        .setAction(command.getAction())
                        .setCommandParts(command.getParts());

                if (!command.getData().getAliases().isEmpty()) {
                    Command newCommand = cloneCommand(command).build();

                    commands.put(newCommand.getData().getName(), newCommand);
                    commandList.add(newCommand);
                }
            }

            SubCommandPart.Builder subCommandBuilder = SubCommandPart.builder("subcommand");

            if (clazz.isAnnotationPresent(Required.class)) {
                subCommandBuilder.setRequired(true);
            }

            SubCommandClasses classesAnnotation = clazz.getAnnotation(SubCommandClasses.class);

            if (classesAnnotation != null) {
                for (Class<? extends CommandClass> subCommandClass : classesAnnotation.value()) {
                    if (subCommandClass.equals(clazz)) {
                        continue;
                    }

                    try {
                        List<Command> subCommands = fromClass(createSubCommandInstance(subCommandClass, clazz, commandClass));
                        commandList.addAll(subCommands);
                    } catch (RuntimeException e) {
                        continue;
                    }
                }
            }

            Usage usage = clazz.getAnnotation(Usage.class);
            if (usage != null) {
                if (!usage.usage()[0].equals("_!!_NOT_OVERRIDE_!!_")) {
                    builder.setUsage(String.join("\n", usage.usage()));
                }
            }

            builder.addPart(subCommandBuilder
                    .setCommands(commandList)
                    .build());

            return Collections.singletonList(builder.build());
        }

        return commandList;
    }

    private CommandData.Builder cloneCommandData(CommandData commandData) {
        CommandData.Builder dataBuilder = CommandData.builder(commandData.getName());

        boolean aliasSet = false;

        if (commandData.getName().equals("") && !commandData.getAliases().isEmpty()) {
            dataBuilder = CommandData.builder(commandData.getAliases().get(0));

            if (commandData.getAliases().size() > 1) {
                dataBuilder.setAliases(new ArrayList<>(commandData.getAliases().subList(1, commandData.getAliases().size())));
            }

            aliasSet = true;
        }

        if (!aliasSet) {
            dataBuilder.setAliases(commandData.getAliases());
        }

        dataBuilder.setDescription(commandData.getDescription());

        return dataBuilder;
    }

    private ImmutableCommand.Builder cloneCommand(Command command) {
        ImmutableCommand.Builder builder = ImmutableCommand.builder(cloneCommandData(command.getData()));

        builder.setUsage(command.getUsage())
                .setAction(command.getAction())
                .setCommandParts(command.getParts())
                .setPermission(command.getPermission())
                .setPermissionMessage(command.getPermissionMessage());

        return builder;
    }

    private CommandClass createSubCommandInstance(Class<? extends CommandClass> clazz, Class<?> upperCommandClass, CommandClass upperCommand) {
        return injector.getInstance(clazz);
    }

    private CommandPart fromParameter(Parameter parameter) {
        if (parameter.isAnnotationPresent(ParentArg.class)) {
            return null;
        }

        Class<?> type = parameter.getType();
        String name = getName(parameter);
        int consumedArgs = getConsumedArgs(parameter);
        java.util.Optional<String[]> defaultValues = getDefault(parameter);

        Flag flag = parameter.getAnnotation(Flag.class);
        Injected injected = parameter.getAnnotation(Injected.class);

        List<String> modifiers = new ArrayList<>();

        for (Annotation annotation : parameter.getAnnotations()) {
            Class<? extends Annotation> annotationType = annotation.annotationType();

            ModifierAnnotation modifierAnnotation = annotationType.getAnnotation(ModifierAnnotation.class);

            // This is not a modifier annotation, ignore it
            if (modifierAnnotation == null) {
                continue;
            }

            modifiers.add(modifierAnnotation.value());
        }

        if (injected != null && flag != null) {
            throw new IllegalArgumentException("The provided parameter has a Flag annotation and a Injected annotation, it should have only one of the two!");
        }

        if (flag != null) {
            if (type != boolean.class && type != Boolean.class) {
                throw new IllegalArgumentException("The provided parameter has a Flag annotation but it doesn't a boolean!");
            }

            return FlagPart.builder(name, flag.value())
                    .setAllModifiers(modifiers)
                    .build();
        }

        if (injected != null) {
            return InjectedValuePart.builder(name, type)
                    .setRequired(injected.value())
                    .setAllModifiers(modifiers)
                    .build();
        }

        return ArgumentPart.builder(name, type)
                .setConsumedArguments(consumedArgs)
                .setRequired(!defaultValues.isPresent())
                .setDefaultValues(Arrays.asList(defaultValues.orElse(new String[0])))
                .setAllModifiers(modifiers)
                .build();
    }

    private CommandAction actionOfMethod(CommandClass commandClass, Method method) {
        class ParametricCommandAction implements CommandAction {

            final List<ValueGetter> commandParts = new ArrayList<>();

            @Override
            public boolean execute(CommandContext parameters) throws CommandException {
                if (commandParts.isEmpty()) {
                    computeParts(parameters);
                }

                List<Object> params = new ArrayList<>();

                for (ValueGetter getter : commandParts) {
                    params.add(getter.get(commandClass, parameters));
                }

                boolean accessible = method.isAccessible();

                try {
                    method.setAccessible(true);

                    boolean result = (boolean) method.invoke(commandClass, params.toArray());
                    method.setAccessible(accessible);

                    return result;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new CommandException("An exception occurred while executing the command", e);
                }
            }

            private void computeParts(CommandContext parameters) {
                for (Parameter parameter : method.getParameters()) {
                    if (parameter.getType() == CommandContext.class) {
                        commandParts.add((commandClass1, commandContext) -> commandContext);

                        continue;
                    }
                    String name = getName(parameter);
                    int indexOf = 0;

                    ParentArg parentArg = parameter.getAnnotation(ParentArg.class);
                    if (parentArg != null) {
                        indexOf = parentArg.value();
                    }

                    int finalIndexOf = indexOf;

                    CommandPart part = parameters.getParts(name).get(finalIndexOf);

                    if (part instanceof FlagPart || part instanceof ArgumentPart || part instanceof InjectedValuePart) {
                        commandParts.add(new ParameterValueGetter(part));
                    }
                }

            }

        }

        return new ParametricCommandAction();
    }

    private static class ParameterValueGetter implements ValueGetter{

        private final CommandPart part;

        public ParameterValueGetter(CommandPart part) {
            this.part = part;
        }

        @Override
        public Object get(CommandClass commandClass, CommandContext commandContext) throws CommandException {
            if (commandContext.hasValue(part)) {
                return commandContext.getRawValue(part);
            } else {
                if (part.isRequired()) {
                    throw new CommandException("The value for the required part" + part.getName() + " is missing!");
                }

                return null;
            }
        }
    }

    private interface ValueGetter {
        Object get(CommandClass commandClass, CommandContext commandContext) throws CommandException;
    }

    private String getName(Parameter parameter) {
        Named named = parameter.getAnnotation(Named.class);

        return named != null ? named.value() : parameter.getName();
    }

    private int getConsumedArgs(Parameter parameter) {
        ConsumedArgs consumedArgs = parameter.getAnnotation(ConsumedArgs.class);

        return consumedArgs != null ? consumedArgs.value() : 1;
    }

    private java.util.Optional<String[]> getDefault(Parameter parameter) {
        Optional optionalA = parameter.getAnnotation(Optional.class);

        return optionalA == null ? java.util.Optional.empty() : java.util.Optional.of(optionalA.value());
    }
}