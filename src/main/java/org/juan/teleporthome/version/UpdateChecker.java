package org.juan.teleporthome.version;

import net.kyori.adventure.platform.facet.Facet;
import net.md_5.bungee.chat.SelectorComponentSerializer;
import org.bukkit.Bukkit;
import org.juan.teleporthome.TeleportHome;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private final TeleportHome teleportHome;
    private final int resourceId;

    public UpdateChecker(TeleportHome teleportHome, int resourceId) {
        this.teleportHome = teleportHome;
        this.resourceId = resourceId;
    }

    public void getVersion(Consumer<String> consumer){
        Bukkit.getScheduler().runTaskAsynchronously(this.teleportHome, () -> {
            try{
                InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                Scanner scanner = new Scanner(inputStream);
                if (scanner.hasNext()){
                    consumer.accept(scanner.next());
                    scanner.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e){
                teleportHome.getLogger().info("Unable to check for updates: " + e.getMessage());
            }
        });
    }
    public void getUpdate(TeleportHome teleportHome){
        if (TeleportHome.getTeleportHome().getConfig().getBoolean("notify-update.enable")){
            new UpdateChecker(teleportHome, this.resourceId).getVersion(latest -> {
                if (TeleportHome.getTeleportHome().getDescription().getVersion().equalsIgnoreCase((String) latest)){
                    teleportHome.getLogger().info("You are using the latest version");
                } else {
                    teleportHome.getLogger().info("New update: " + latest);
                    teleportHome.getLogger().info("Current version: " + teleportHome.getDescription().getVersion());
                }

            });
        }
    }
}
