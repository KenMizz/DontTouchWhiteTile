package kenmizz.commands;

import kenmizz.DontTouchWhiteTile;
import kenmizz.commands.subcommand.HelpCommand;
import kenmizz.commands.subcommand.SetCommand;
import kenmizz.commands.subcommand.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTTTCommand implements CommandExecutor, TabCompleter {

    private DontTouchWhiteTile plugin;

    private Map<String, SubCommand> subCommandMap = new HashMap<>();

    public DTTTCommand(DontTouchWhiteTile plugin ) {
        this.plugin = plugin;
        subCommandMap.put("set", new SetCommand());
        subCommandMap.put("help", new HelpCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if ( strings.length < 1 ) return false;
        String subCommandName = strings[0];
        if ( !subCommandMap.containsKey(subCommandName)) return false;

        SubCommand subCommand = subCommandMap.get(subCommandName);
        return subCommand.execute(plugin, commandSender, strings);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
