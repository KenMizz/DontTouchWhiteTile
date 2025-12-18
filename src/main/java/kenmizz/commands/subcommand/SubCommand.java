package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import kenmizz.commands.DTTTCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface SubCommand {

    String getName();

    boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args);
}
