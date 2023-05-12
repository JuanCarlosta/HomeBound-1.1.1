package org.juan.teleporthome.Commands;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.juan.teleporthome.TeleportHome;
import org.juan.teleporthome.Utilitys.BlockSpawn;
import org.juan.teleporthome.Utilitys.CharLimits;
import org.juan.teleporthome.Utilitys.RenameUtils;
import org.juan.teleporthome.Utilitys.WorldChecker;
import org.juan.teleporthome.enums.RenameComands;

import static org.juan.teleporthome.Commands.HomeStaffCommand.renameUtils;

public class Rename implements CommandExecutor {
    private static final RenameComands RENAME = RenameComands.RENAME;
    String permission_massage = ChatColor.translateAlternateColorCodes('&',
            TeleportHome.getTeleportHome().getConfig().getString("permission-massage"));

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            ItemStack handItem = p.getInventory().getItemInMainHand();
            ItemMeta im = handItem.getItemMeta();
            if (WorldChecker.onCheckWorld(p)) {
                if (CharLimits.checkCharLimit(args, p)) {
                    if (p.hasPermission("SimpleTeleportHome.rename")) {
                        if (im.getPersistentDataContainer().has(NamespacedKey.fromString("teleportitem"), PersistentDataType.STRING)) {
                            RenameUtils.renameCope(p, args, RENAME);
                            return true;
                        } else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.incorect_item")));
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.no_arguments")));
                            return true;
                        }
                    }
                    p.sendMessage(permission_massage);
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.name_too_long")));
                return true;
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.inccorect_world")));
            return true;

        }
        return false;
    }
}
