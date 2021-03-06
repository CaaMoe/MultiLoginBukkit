package moe.caa.multilogin.bukkit.support.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import moe.caa.multilogin.bukkit.main.MultiLoginBukkit;
import moe.caa.multilogin.core.data.User;
import moe.caa.multilogin.core.util.ValueUtil;
import org.bukkit.entity.Player;

import java.util.Locale;

public class MultiLoginPlaceholder extends PlaceholderExpansion {
    private final MultiLoginBukkit PLUGIN;

    public MultiLoginPlaceholder(MultiLoginBukkit plugin) {
        PLUGIN = plugin;
    }

    @Override
    public String getIdentifier() {
        return PLUGIN.getName().toLowerCase(Locale.ROOT);
    }

    @Override
    public String getAuthor() {
        return String.join(", ", PLUGIN.getDescription().getAuthors());
    }

    @Override
    public String getVersion() {
        return PLUGIN.getPluginVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        String ret = "";
        if (player == null || !ValueUtil.notIsEmpty(params)) return ret;
        try {
            User entry = PLUGIN.getCacheUserData(player.getUniqueId());
            if (entry == null) return "";
            if (params.equalsIgnoreCase("currentname")) {
                ret = entry.getCurrentName();
            } else if (params.equalsIgnoreCase("onlineuuid")) {
                ret = entry.getOnlineUuid().toString();
            } else if (params.equalsIgnoreCase("redirecteduuid")) {
                ret = entry.getRedirectUuid().toString();
            } else if (params.equalsIgnoreCase("whitelist")) {
                ret = String.valueOf(entry.isWhitelist());
            } else if (params.equalsIgnoreCase("yggdrasilname")) {
                ret = entry.getService().getName();
            } else if (params.equalsIgnoreCase("yggdrasilpath")) {
                ret = entry.getService().getPath();
            }
        } catch (Exception ignored) {
        }
        return ret;
    }
}
