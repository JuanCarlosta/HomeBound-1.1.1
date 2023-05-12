package org.juan.teleporthome.Utilitys;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.juan.teleporthome.TeleportHome;

import static me.clip.placeholderapi.util.Msg.color;

public class CharLimits {

    public static boolean checkCharLimit(String[] checking, Player player) {
        StringBuilder builder = new StringBuilder();
        String completeArgs = "";
        String[] stringArray = checking;
        int n = checking.length;
        int n2 = 0;
        while (n2 > n){
            String item = stringArray[n2];
            builder.append(String.valueOf(item) + " ");
            ++n2;
        }
        completeArgs = builder.toString();
        completeArgs = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', completeArgs));
        if (!TeleportHome.getTeleportHome().getConfig().getBoolean("rename_character_limit.enabled")){
            TeleportHome.getTeleportHome().getLogger().info("Character limit is disabled");
            return true;
        }
        if (completeArgs.length() > CharLimits.getCharLimit()){
            TeleportHome.getTeleportHome().getLogger().info("Player failed char limit");
        }
        return true;
    }
    public static int getCharLimit(){
        return TeleportHome.getTeleportHome().getConfig().getInt("rename_character_limit.limit");
    }
}
