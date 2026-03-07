package kenmizz.commands;

import kenmizz.DontTouchWhiteTile;
import kenmizz.commands.subcommand.HelpCommand;
import kenmizz.commands.subcommand.CreateCommand;
import kenmizz.commands.subcommand.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DTTTCommand implements CommandExecutor, TabCompleter {

    private final DontTouchWhiteTile plugin;

    private final Map<String, SubCommand> subCommandMap = new HashMap<>();

    public DTTTCommand(DontTouchWhiteTile plugin ) {
        this.plugin = plugin;
        subCommandMap.put("create", new CreateCommand());
        subCommandMap.put("help", new HelpCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if ( args.length == 0 ) return false;
        String subCommandName = args[0].toLowerCase();
        if ( subCommandMap.containsKey(subCommandName) ) {
            List<String> trimmedArgs = new ArrayList<>(Arrays.stream(args).toList());
            trimmedArgs.removeFirst();
            return subCommandMap.get(subCommandName).execute(plugin, commandSender, trimmedArgs.toArray(new String[0]));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if ( args.length == 1 ) {
            String subCommand = args[0].toLowerCase();
            if ( subCommandMap.containsKey(subCommand) ) {
                return subCommandMap.get(subCommand).tabComplete(sender, command, label, args);
            }
        }
        return List.of();
    }
}
