package org.juan.teleporthome.Utilitys;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.juan.teleporthome.TeleportHome;
import org.juan.teleporthome.enums.EcoMessages;
import org.juan.teleporthome.enums.RenameComands;

import static org.juan.teleporthome.Commands.HomeStaffCommand.renameUtils;

public class RenameUtils {

    public static BlockSpawn blockSpawn = new BlockSpawn();
    public static void renameCope(Player p, String[] args, RenameComands rc){
        if (rc == RenameComands.RENAME){
            ItemStack renameItem = RenameUtils.getInHand(p);
            if (CharLimits.checkCharLimit(args, p)){
                TeleportHome.getTeleportHome().getLogger().info("Passed Character Limit Check");
                if (renameItem.getItemMeta() != null){
                    StringBuilder builder = new StringBuilder(" ");
                    String completeArgs = "";
                    String[] stringArray = args;
                    int n = args.length;
                    int n2 = 0;
                    while (n2 < n) {
                        String item = stringArray[n2];
                        builder.append(String.valueOf(item) + " ");
                        ++n2;
                    }
                    EcoMessages ecoMessages = EconomyManager.moneyTaking(p, RenameComands.RENAME);
                    if (ecoMessages == EcoMessages.TRANSACTION_ERROR) {
                        return;
                    }
                    completeArgs = builder.toString().trim();
                    String oldName = renameItem.getItemMeta().getDisplayName();
                    if (TeleportHome.USE_NEW_GET_HAND){
                        p.getInventory().setItemInMainHand(RenameUtils.renameItemStack(p, completeArgs, renameItem));
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.success"))
                                .replace("{previous_name}", oldName).replace("{new_name}", completeArgs));
                        TeleportHome.getTeleportHome().getLogger().info(TeleportHome.getTeleportHome().getConfig().getString("rename.log").replace("{player}", p.getName()).replace("{name}", completeArgs).replace("{previous_name}",
                                oldName).replace("{new_name}", completeArgs));
                        return;
                    }
                    p.setItemInHand(RenameUtils.renameItemStack(p, completeArgs, renameItem));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.success").replace("{previous_name}", oldName).replace("{new_name}", completeArgs)));
                    TeleportHome.getTeleportHome().getLogger().info(TeleportHome.getTeleportHome().getConfig().getString("rename.log").replace("{player}", p.getName()).replace("{name}", completeArgs).replace("{previous_name}", oldName).replace("{new_name}", completeArgs));
                    return;
                } else if (renameItem.getItemMeta() == null){
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.incorect_item")));
                    return;
                }

            }
        }
    }
    public static ItemStack renameItemStack(Player player, String completeArgs, ItemStack torename){
        if (TeleportHome.getTeleportHome().getConfig().getBoolean("replace_underscores")){
            completeArgs = String.valueOf(completeArgs) + "";
            TeleportHome.getTeleportHome().getLogger().info("Added trailing space to rename arguments");
        }
        if (TeleportHome.getTeleportHome().getConfig().getBoolean("add_leading_space_to_rename")){
            completeArgs = " " + completeArgs;
            TeleportHome.getTeleportHome().getLogger().info("Added Leading space to rename arguments");
        }
        TeleportHome.getTeleportHome().getLogger().info("The complete args result is: " + completeArgs);
        ItemMeta itemMeta = torename.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', completeArgs));
        torename.setItemMeta(itemMeta);
        return torename;
    }
    public static ItemStack getInHand(Player player) {
        ItemStack itemStack = new ItemStack(blockSpawn.onArrow());
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (TeleportHome.USE_NEW_GET_HAND) {

            if (player.getInventory().getItemInMainHand().hasItemMeta() &&
                    itemMeta.getPersistentDataContainer().has(NamespacedKey.fromString("teleportitem"), PersistentDataType.STRING));
        }
        try {
            itemStack = player.getItemInHand();
        }
        catch (Exception e) {
            e.printStackTrace();
            TeleportHome.getTeleportHome().getLogger().info("&cProblem while getting the ItemStack inHand. Failed at player.getItemInHand()");
            TeleportHome.getTeleportHome().getLogger().info("&cProblem while getting the ItemStack inHand. Failed at player.getItemInHand()");
        }
        return itemStack;
    }
}
