package org.juan.teleporthome;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.juan.teleporthome.Commands.GiveBlockCommand;
import org.juan.teleporthome.Commands.HomeStaffCommand;
import org.juan.teleporthome.Commands.Rename;
import org.juan.teleporthome.Listener.JoinLeave;
import org.juan.teleporthome.Utilitys.BlockSpawn;
import org.juan.teleporthome.version.UpdateChecker;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;



public final class TeleportHome extends JavaPlugin {

    public static TeleportHome teleportHome;
    private static final Logger log = Logger.getLogger("Minecraft");
    public static Economy econ = null;
    public HashMap<UUID, Long> cooldown = new HashMap<>();
    public static boolean USE_NEW_GET_HAND = true;
    public static boolean USE_ECO;

    static {
        USE_ECO = false;
        econ = null;
    }
    @Override
    public void onEnable() {
        teleportHome = this;
        // Plugin startup logic
        System.out.println("[SimpleTeleportHome] is starting up..");

        getCommand("g-home").setExecutor(new GiveBlockCommand());

        getServer().getPluginManager().registerEvents(new JoinLeave(), this);

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getCommand("s-home").setExecutor(new HomeStaffCommand());
            getCommand("newname").setExecutor(new Rename());
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        new UpdateChecker(this, 109736).getUpdate(this);

        if (TeleportHome.getTeleportHome().getConfig().getBoolean("economy.use")){
            USE_ECO = true;
            TeleportHome.getTeleportHome().getLogger().info("[HomeBound] Economy is enabled");
        }


    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[SimpleTeleportHome] is shutting down..");
    }
    public static TeleportHome getTeleportHome(){
        return teleportHome;
    }
    public static Economy getEconomy() {
        return econ;
    }
    public static String getConfigMsg(String path){
        if (teleportHome.getConfig().getString(path) == null){
            teleportHome.getLogger().info("[Main#getMsgFromConfig()] A message from messages.yml was NULL. The path to the message is: " + path);
            return "[ERROR] Could not read value from config.yml. Ask a server admin to obtain help [ERROR]";
        }
        return teleportHome.getConfig().getString(path);
    }

}
