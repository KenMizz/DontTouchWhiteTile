package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CreateCommand implements SubCommand {

    private MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        if ( !commandSender.hasPermission("dttt.command.create") ) {
            commandSender.sendMessage("<red>权限不足</red>");
            return true;
        }

        if ( !(commandSender instanceof Player) ) {
            commandSender.sendMessage(miniMessage.deserialize("<red>你需要以玩家身份执行此操作!</red>"));
            return true;
        }

        commandSender.sendMessage(miniMessage.deserialize("<yellow>正在设置一块新的别踩白块儿区域，请先选择<aqua>保护点A</aqua></yellow>"));
        plugin.getGameConfigManager().createConfigDraft((Player) commandSender);
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        return List.of();
    }
}
