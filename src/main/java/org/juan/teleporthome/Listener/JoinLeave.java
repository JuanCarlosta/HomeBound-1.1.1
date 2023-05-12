package org.juan.teleporthome.Listener;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.juan.teleporthome.TeleportHome;
import org.juan.teleporthome.Utilitys.BlockSpawn;
import org.juan.teleporthome.Utilitys.WorldChecker;

import static org.juan.teleporthome.Commands.HomeStaffCommand.renameUtils;

public class JoinLeave implements Listener {

    BlockSpawn blockSpawn;

    public JoinLeave() {
        blockSpawn = new BlockSpawn();
    }
    int cd = TeleportHome.getTeleportHome().getConfig().getInt("player-join-cooldown");
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e){
        if (TeleportHome.getTeleportHome().getConfig().getBoolean("player-join")) {
            Player p = e.getPlayer();
            if (WorldChecker.onCheckWorld(p)) {
                for (String str : TeleportHome.getTeleportHome().getConfig().getStringList("massageJ")) {
                    String massage = ChatColor.translateAlternateColorCodes('&', str);
                    massage = PlaceholderAPI.setPlaceholders(p, massage);
                    if (!TeleportHome.getTeleportHome().cooldown.containsKey(p.getUniqueId())) {
                        if (TeleportHome.getTeleportHome().getConfig().getBoolean("cooldown")) {
                            TeleportHome.getTeleportHome().cooldown.put(p.getUniqueId(), System.currentTimeMillis() + cd * 3_600_000);
                        }
                        p.sendMessage(massage);
                        p.getInventory().addItem(blockSpawn.onArrow());
                    } else {
                        long timeElapsed = TeleportHome.getTeleportHome().cooldown.get(p.getUniqueId()) - System.currentTimeMillis();
                        if (timeElapsed <= 0) {
                            TeleportHome.getTeleportHome().cooldown.put(p.getUniqueId(), System.currentTimeMillis() + cd * 3_600_000);
                            p.sendMessage(massage);
                            p.getInventory().addItem(blockSpawn.onArrow());
                        }
                    }
                }
            }
        }
    }
}
