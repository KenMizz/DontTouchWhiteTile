package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements SubCommand {

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        if ( plugin.pointA == null || plugin.pointB == null ) return false;
        if ( args.length < 2 ) return false;
        String argName = args[1];
        switch ( argName ) {
            default -> {
                return false;
            }

            case "shuffle" -> {
                plugin.resetWall();
                plugin.shufflePattern();
            }

            case "play" -> {
                plugin.shufflePattern();
                plugin.playingPlayer = (Player) commandSender;
                commandSender.sendMessage("Let the game begin!");
            }

            case "stop" -> {
                plugin.playingPlayer = null;
                commandSender.sendMessage("Game stopped");
            }
        }
        return true;
    }
}
