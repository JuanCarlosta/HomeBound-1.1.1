package org.juan.teleporthome.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.juan.teleporthome.TeleportHome;
import org.juan.teleporthome.Utilitys.BlockSpawn;
import org.juan.teleporthome.Utilitys.WorldChecker;

import static org.juan.teleporthome.Commands.HomeStaffCommand.renameUtils;

public class GiveBlockCommand implements CommandExecutor {

    BlockSpawn blockSpawn;

    public GiveBlockCommand() {
        blockSpawn = new BlockSpawn();
    }
    String permission_massage = ChatColor.translateAlternateColorCodes('&',
            TeleportHome.getTeleportHome().getConfig().getString("permission-massage"));
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        int cd = TeleportHome.getTeleportHome().getConfig().getInt("cooldown_g-home");

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (WorldChecker.onCheckWorld(p)) {
                if (p.hasPermission("SimpleTeleportHome.give-item")) {
                    if (strings.length == 0) {
                        if (!TeleportHome.getTeleportHome().cooldown.containsKey(p.getUniqueId())) {
                            TeleportHome.getTeleportHome().cooldown.put(p.getUniqueId(), System.currentTimeMillis() + cd * 3_600_000);
                            ItemStack block = blockSpawn.onArrow();
                            p.getInventory().addItem(block);
                        } else {
                            long timeElapsed = TeleportHome.getTeleportHome().cooldown.get(p.getUniqueId()) - System.currentTimeMillis();
                            if (timeElapsed <= 0) {
                                TeleportHome.getTeleportHome().cooldown.put(p.getUniqueId(), System.currentTimeMillis() + cd * 3_600_000);
                                ItemStack block = blockSpawn.onArrow();
                                p.getInventory().addItem(block);
                            } else {
                                p.sendMessage("Wait for " + (timeElapsed / 3_600_000) + " hours " + (timeElapsed / 60_000) + " min");
                            }
                        }
                    }
                } else {
                    p.sendMessage(permission_massage);
                }
            }
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.inccorect_world")));
            return true;

        }
        return true;
    }
}
