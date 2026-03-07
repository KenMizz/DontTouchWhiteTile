package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HelpCommand implements SubCommand {

    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        if ( !commandSender.hasPermission("dttt.command.help") ) return true;
        String[] helpMessage = {
                "<white>别踩白块帮助</white>",
                "/dttt <yellow>create</yellow> - 设置别踩白块区域",
                "/dttt <yellow>remove</yellow> - 移除别踩白块区域",
                "/dttt <green>del</green> <gold>[NAME]</gold> - 删除一个别踩白块区域"
        };
        commandSender.sendMessage(miniMessage.deserialize(String.join("\n", helpMessage)));
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
