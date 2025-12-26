package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements SubCommand {

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        if ( plugin.pointA == null || plugin.pointB == null ) return false;
        plugin.updatePattern();
        return true;
    }
}
