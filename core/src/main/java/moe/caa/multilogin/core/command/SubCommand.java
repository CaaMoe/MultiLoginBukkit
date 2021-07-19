package moe.caa.multilogin.core.command;

import moe.caa.multilogin.core.impl.ISender;
import moe.caa.multilogin.core.language.LanguageKeys;
import moe.caa.multilogin.core.logger.LoggerLevel;
import moe.caa.multilogin.core.logger.MultiLogger;
import moe.caa.multilogin.core.main.MultiCore;
import moe.caa.multilogin.core.util.ValueUtil;

import java.util.*;
import java.util.stream.Collectors;

public abstract class SubCommand {
    protected final String name;
    protected final Permission permission;
    protected final Set<SubCommand> subCommands = new HashSet<>();

    protected SubCommand(String name, Permission permission) {
        this.name = name;
        this.permission = permission;
    }

    protected final void execute0(ISender sender, String[] args) {
        if (permission != null && !permission.hasPermissionAndFeedback(sender)) return;
        if (args.length >= 1) {
            String name = args[0];
            for (SubCommand command : subCommands) {
                if (command.name.equalsIgnoreCase(name)) {
                    String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                    command.execute0(sender, newArgs);
                    return;
                }
            }
        }

        try {
            execute(sender, args);
        } catch (Throwable throwable) {
            sender.sendMessage(LanguageKeys.COMMAND_ERROR.getMessage());
            MultiLogger.log(LoggerLevel.ERROR, throwable);
            MultiLogger.log(LoggerLevel.ERROR, LanguageKeys.COMMAND_ERROR.getMessage());
        }

        MultiCore.plugin.getSchedule().runTaskAsync(() -> {
            try {
                executeAsync(sender, args);
            } catch (Throwable throwable) {
                sender.sendMessage(LanguageKeys.COMMAND_ERROR.getMessage());
                MultiLogger.log(LoggerLevel.ERROR, throwable);
                MultiLogger.log(LoggerLevel.ERROR, LanguageKeys.COMMAND_ERROR.getMessage());
            }
        });
    }

    public final List<String> tabCompile0(ISender sender, String[] args) throws Throwable {
        if (permission != null && !permission.hasPermission(sender)) return Collections.emptyList();
        if (args.length >= 1) {
            String name = args[0];
            String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
            for (SubCommand command : subCommands) {
                if (command.name.equalsIgnoreCase(name)) {
                    return command.tabCompile0(sender, newArgs);
                }
            }
        }
        return tabCompile(sender, args).stream().filter(s -> ValueUtil.startsWithIgnoreCase(s, args[args.length - 1])).collect(Collectors.toList());
    }

    public void execute(ISender sender, String[] args) throws Throwable {
        sender.sendMessage(LanguageKeys.COMMAND_UNKNOWN.getMessage());
    }

    public void executeAsync(ISender sender, String[] args) throws Throwable {

    }

    public List<String> tabCompile(ISender sender, String[] args) throws Throwable {
        if (args.length == 1) {
            return subCommands.stream().filter(subCommand -> subCommand.permission == null || subCommand.permission.hasPermission(sender)).map(subCommand -> subCommand.name).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}