package org.juan.teleporthome.Utilitys;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Debug;
import org.juan.teleporthome.TeleportHome;

import java.awt.*;
import java.util.List;

public class WorldChecker {
    public static boolean onCheckWorld(Player player){
        if (!TeleportHome.getTeleportHome().getConfig().getBoolean("per_world")){
            TeleportHome.getTeleportHome().getLogger().info("Per world is disabled.");
            return true;
        }
        Location location = player.getLocation();
        List<String> worlds = TeleportHome.getTeleportHome().getConfig().getStringList("enabled_worlds");
        for (String s : worlds){
            if (!location.getWorld().getName().equals(s)) continue;
            TeleportHome.getTeleportHome().getLogger().info("In an enable world");
            return true;
        }
        TeleportHome.getTeleportHome().getLogger().info("In an disable world");
        return false;
    }
}
