package org.juan.teleporthome.Utilitys;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.juan.teleporthome.TeleportHome;
import org.juan.teleporthome.enums.EcoMessages;
import org.juan.teleporthome.enums.RenameComands;

public class EconomyManager {

    public static EcoMessages moneyTaking(Player p, RenameComands rc){
        if (!TeleportHome.USE_ECO){
            return  EcoMessages.ECO_DISABLED;
        }
        EconomyResponse response = TeleportHome.econ.withdrawPlayer(p, TeleportHome.getTeleportHome().getConfig().getInt("economy.costs.rename"));
        TeleportHome.getTeleportHome().getLogger().info("Value from config has been: " + TeleportHome.getTeleportHome().getConfig().getInt("economy.costs.rename"));
        if (response.transactionSuccess()){
            p.sendMessage(EconomyManager.formatMsg(ChatColor.translateAlternateColorCodes('&',
                    TeleportHome.getTeleportHome().getConfig().getString("economyM.transaction_success")),response));
            return EcoMessages.SUCCESS;
        }
        p.sendMessage(EconomyManager.formatMsg(ChatColor.translateAlternateColorCodes('&',
                TeleportHome.getTeleportHome().getConfig().getString("economyM.transaction_error")), response));
        return EcoMessages.TRANSACTION_ERROR;
    }
    public static String formatMsg(String msg, EconomyResponse r) {
        msg = msg.replace("{cost}", String.valueOf(r.amount));
        if (!r.transactionSuccess()) {
            msg = msg.replace("{error}", r.errorMessage);
        }
        return msg;
    }
}
