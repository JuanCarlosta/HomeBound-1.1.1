package org.juan.teleporthome.Commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import org.juan.teleporthome.Utilitys.RenameUtils;
import org.juan.teleporthome.Utilitys.WorldChecker;

public class HomeStaffCommand implements CommandExecutor {

    BlockSpawn blockSpawn;

    public HomeStaffCommand(){
        blockSpawn = new BlockSpawn();
    }
    public static RenameUtils renameUtils = new RenameUtils();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        String return_massage = ChatColor.translateAlternateColorCodes('&',
                TeleportHome.getTeleportHome().getConfig().getString("return-massage"));
        String override_massage = ChatColor.translateAlternateColorCodes('&',
                TeleportHome.getTeleportHome().getConfig().getString("overriding-massage"));
        String reload_massage = ChatColor.translateAlternateColorCodes('&',
                TeleportHome.getTeleportHome().getConfig().getString("reload-massage"));
        String teleport_text = ChatColor.translateAlternateColorCodes('&',
                TeleportHome.getTeleportHome().getConfig().getString("teleporting-massage"));
        String permission_massage = ChatColor.translateAlternateColorCodes('&',
                TeleportHome.getTeleportHome().getConfig().getString("permission-massage"));


        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (strings.length == 0) {
                for (String str : TeleportHome.getTeleportHome().getConfig().getStringList("massage")) {
                    String massage = ChatColor.translateAlternateColorCodes('&', str);
                    p.sendMessage(massage);
                }

            }
            if (TeleportHome.getTeleportHome().getConfig().getBoolean("enable")) {
                if (WorldChecker.onCheckWorld(p)) {
                    if (strings[0].equalsIgnoreCase("set")) {
                        if (p.hasPermission("SimpleTeleportHome.use")) {
                            ItemStack handItem = p.getInventory().getItemInMainHand();
                            ItemMeta im = handItem.getItemMeta();
                            if (im == null) {
                                for (String str : TeleportHome.getTeleportHome().getConfig().getStringList("massage-error-item")){
                                    String massage = ChatColor.translateAlternateColorCodes('&', str);
                                    p.sendMessage(massage);
                                }
                                return true;
                            }
                            if (im.getPersistentDataContainer().has(NamespacedKey.fromString("teleportitem"), PersistentDataType.STRING)) {
                                if (p.hasPermission("SimpleTeleportHome.set")) {
                                    if (TeleportHome.getTeleportHome().getConfig().isConfigurationSection("saved_location." + p.getName())) {
                                        p.sendMessage(override_massage + Math.round(TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + p.getName() + ".x")) + " " + Math.round(TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + p.getName() + ".y")) + " " + Math.round(TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + p.getName() + ".z")));
                                        this.onSaveLocation(p);
                                    } else {
                                        this.onSaveLocation(p);
                                    }
                                } else {
                                    p.sendMessage(permission_massage);
                                }
                            } else {
                                for (String str : TeleportHome.getTeleportHome().getConfig().getStringList("massage-error-item")){
                                    String massage = ChatColor.translateAlternateColorCodes('&', str);
                                    p.sendMessage(massage);
                                }
                            }
                        } else {
                            p.sendMessage(permission_massage);
                        }
                    }

                    if (strings[0].equalsIgnoreCase("return")) {
                        ItemStack handItem = p.getInventory().getItemInMainHand();
                        ItemMeta im = handItem.getItemMeta();
                        if (im == null) {
                            for (String str : TeleportHome.getTeleportHome().getConfig().getStringList("massage-error-item")){
                                String massage = ChatColor.translateAlternateColorCodes('&', str);
                                p.sendMessage(massage);
                            }
                            return true;
                        }
                        if (im.getPersistentDataContainer().has(NamespacedKey.fromString("teleportitem"), PersistentDataType.STRING)) {
                            if (p.hasPermission("SimpleTeleportHome.return")) {
                                if (TeleportHome.getTeleportHome().getConfig().isConfigurationSection("saved_location." + p.getName())) {
                                    Location return_location = new Location(p.getWorld(), TeleportHome.getTeleportHome().getConfig()
                                            .getDouble("saved_location." + p.getName() + ".x"), TeleportHome.getTeleportHome().getConfig()
                                            .getDouble("saved_location." + p.getName() + ".y"), TeleportHome.getTeleportHome().getConfig()
                                            .getDouble("saved_location." + p.getName() + ".z"));
                                    p.teleport(return_location);
                                    p.sendMessage(return_massage);
                                    TeleportHome.getTeleportHome().getConfig().set("saved_location." + p.getName(), null);
                                    TeleportHome.getTeleportHome().saveConfig();
                                } else {
                                    p.sendMessage(ChatColor.RED + "You have to set your home at first");
                                    p.sendMessage(ChatColor.RED + "For example /set-home set");
                                }
                            } else {
                                p.sendMessage(permission_massage);
                            }
                        } else {
                            for (String str : TeleportHome.getTeleportHome().getConfig().getStringList("massage-error-item")){
                                String massage = ChatColor.translateAlternateColorCodes('&', str);
                                p.sendMessage(massage);
                            }
                        }
                    }
                    if (strings[0].equalsIgnoreCase("reload")) {
                        if (p.hasPermission("SimpleTeleportHome.reload")) {
                            TeleportHome.getTeleportHome().reloadConfig();
                            p.sendMessage(reload_massage);
                        } else {
                            p.sendMessage(permission_massage);
                        }
                    }
                    if (strings.length == 1) {
                        if (p.hasPermission("SimpleTeleportHome.admin.teleport")) {
                            Player t = Bukkit.getPlayer(strings[0]);
                            teleport_text = PlaceholderAPI.setPlaceholders(t, teleport_text);
                            if (t != null) {
                                if (TeleportHome.getTeleportHome().getConfig().isConfigurationSection("saved_location." + t.getName())) {
                                    p.sendMessage(teleport_text + Math.round(TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + t.getName() + ".x")) + " " +
                                            Math.round(TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + t.getName() + ".y")) + " " +
                                            Math.round(TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + t.getName() + ".z")));
                                    Location return_location1 = new Location(t.getWorld(), TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + t.getName() + ".x"),
                                            TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + t.getName() + ".y"),
                                            TeleportHome.getTeleportHome().getConfig().getDouble("saved_location." + t.getName() + ".z"));
                                    p.teleport(return_location1);
                                } else {
                                    String massage = ChatColor.translateAlternateColorCodes('&',TeleportHome.getTeleportHome().getConfig().getString("massage-no-set"));
                                    massage = PlaceholderAPI.setPlaceholders(p, massage);
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', massage));
                                }
                            }
                        } else {
                            p.sendMessage(permission_massage);
                        }
                    }
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("rename.inccorect_world")));
                return true;
            }
        }
        return true;
    }
    public void onSaveLocation(Player p){
        String set_massage = ChatColor.translateAlternateColorCodes('&', TeleportHome.getTeleportHome().getConfig().getString("set-massage"));
        Location location = p.getLocation();
        TeleportHome.getTeleportHome().getConfig().getConfigurationSection("saved_location." + p.getName());
        TeleportHome.getTeleportHome().getConfig().set("saved_location." + p.getName() + ".x", location.getX());
        TeleportHome.getTeleportHome().getConfig().set("saved_location." + p.getName() + ".y", location.getY());
        TeleportHome.getTeleportHome().getConfig().set("saved_location." + p.getName() + ".z", location.getZ());
        TeleportHome.getTeleportHome().saveConfig();
        p.sendMessage(set_massage + Math.round(TeleportHome.getTeleportHome().getConfig()
                .getDouble("saved_location." + p.getName() + ".x")) + " " + Math.round(TeleportHome.getTeleportHome().getConfig()
                .getDouble("saved_location." + p.getName() + ".y")) + " " + Math.round(TeleportHome.getTeleportHome().getConfig()
                .getDouble("saved_location." + p.getName() + ".z")));
    }
}
