package org.juan.teleporthome.Utilitys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.juan.teleporthome.TeleportHome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockSpawn {
    private final Map<Enchantment, Integer> enchantmentToLevelMap = new HashMap<>();
    public ItemStack onArrow() {

            ItemStack arrowHome = new ItemStack(Material.valueOf(TeleportHome.getTeleportHome().getConfig().getString("TeleportItem.material")));
            String name_item = ChatColor.translateAlternateColorCodes('&',
                    TeleportHome.getTeleportHome().getConfig().getString("TeleportItem.item_name"));

            ItemMeta arrowHomeItemMeta = arrowHome.getItemMeta();
            arrowHomeItemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            ArrayList<String> lore = (ArrayList<String>) TeleportHome.getTeleportHome().getConfig().getStringList("TeleportItem.lore");
            lore.replaceAll(s -> ChatColor.translateAlternateColorCodes('&', s));
            arrowHomeItemMeta.setLore(lore);
            arrowHomeItemMeta.setDisplayName(name_item);
            arrowHomeItemMeta.getPersistentDataContainer().set(NamespacedKey.fromString("teleportitem"), PersistentDataType.STRING, name_item);

            ConfigurationSection enchantmentSection = TeleportHome.getTeleportHome().getConfig().getConfigurationSection("TeleportItem.Enchantment");
            if (enchantmentSection != null){
                for (String enchantmentKey : enchantmentSection.getKeys(false)){
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentKey.toLowerCase()));

                if (enchantment != null){
                    int level = enchantmentSection.getInt(enchantmentKey);
                    enchantmentToLevelMap.put(enchantment, level);
                } else {
                    Bukkit.getLogger().severe("You have to point level of enchantment");
                }
            }
        }
        if (!enchantmentToLevelMap.isEmpty()){
            for (Map.Entry<Enchantment, Integer> enchantEntry : enchantmentToLevelMap.entrySet()){
                arrowHomeItemMeta.addEnchant(enchantEntry.getKey(), enchantEntry.getValue(), true);
            }
        }
        arrowHome.setItemMeta(arrowHomeItemMeta);
        return arrowHome;
    }
}
