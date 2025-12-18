package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import kenmizz.commands.DTTTCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetCommand implements SubCommand {

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        return false;
    }
}
