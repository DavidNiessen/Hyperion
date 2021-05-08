package net.skillcode.hyperion.command.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.hyperion.config.configs.MainConfig;
import net.skillcode.hyperion.item.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Singleton
public class HyperionCommand implements CommandExecutor {

    @Inject
    private MainConfig mainConfig;
    @Inject
    private ItemManager itemManager;

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        final Player player = (Player) commandSender;

        if (!player.hasPermission(mainConfig.getString(MainConfig.COMMAND_PERMISSION))) {
            player.sendMessage(mainConfig.getString(MainConfig.NO_PERMISSION_MESSAGE));
            return false;
        }

        itemManager.getHyperion().ifPresent(player.getInventory()::addItem);
        return false;
    }
}
