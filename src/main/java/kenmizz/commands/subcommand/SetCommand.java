package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import kenmizz.commands.DTTTCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetCommand implements SubCommand {

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        if ( !(commandSender instanceof Player)) {
            commandSender.sendMessage("You need to a player to do this");
            return true;
        }
        if ( !commandSender.hasPermission("command.dttt.set")) {
            commandSender.sendMessage("Insufficient permission");
            return false;
        }
        plugin.playerDraftingList.add((Player) commandSender);
        commandSender.sendMessage("Start setting point A");
        return true;
    }
}
