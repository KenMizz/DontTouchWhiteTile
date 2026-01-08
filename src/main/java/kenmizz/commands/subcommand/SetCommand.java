package kenmizz.commands.subcommand;

import kenmizz.DontTouchWhiteTile;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetCommand implements SubCommand {

    private MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public String getName() {
        return "set";
    }

    @Override
    public boolean execute(DontTouchWhiteTile plugin, @NotNull CommandSender commandSender, @NotNull String @NotNull [] args) {
        if ( !commandSender.hasPermission("command.dttt.set") ) {
            commandSender.sendMessage("<red>权限不足</red>");
            return true;
        }

        if ( !(commandSender instanceof Player) ) {
            commandSender.sendMessage(miniMessage.deserialize("<red>你需要以玩家身份执行此操作!</red>"));
            return true;
        }

        commandSender.sendMessage(miniMessage.deserialize("你正在设置一块新的别踩白块儿区域，请先选择点A"));
        plugin.getGameConfigManager().createConfigDraft((Player) commandSender);
        return true;
    }
}
