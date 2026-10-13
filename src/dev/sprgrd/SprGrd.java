/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerRegisterChannelEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.java.JavaPlugin
 *  org.bukkit.plugin.messaging.Messenger
 *  org.bukkit.plugin.messaging.PluginMessageListener
 */
package dev.sprgrd;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class SprGrd
extends JavaPlugin
implements Listener,
PluginMessageListener {
    static final String CH = "sprswp:hs";
    static final String PRM = "spearswapguard.allow";
    boolean alw;

    public void onEnable() {
        this.saveDefaultConfig();
        this.alw = this.getConfig().getBoolean("allow-spear-swap", true);
        Messenger messenger = this.getServer().getMessenger();
        messenger.registerIncomingPluginChannel((Plugin)this, CH, (PluginMessageListener)this);
        messenger.registerOutgoingPluginChannel((Plugin)this, CH);
        this.getServer().getPluginManager().registerEvents((Listener)this, (Plugin)this);
        this.getLogger().info(this.alw ? "spear swap is on for ppl w spearswapguard.allow" : "spear swap is off");
    }

    public void onDisable() {
        Messenger messenger = this.getServer().getMessenger();
        messenger.unregisterIncomingPluginChannel((Plugin)this);
        messenger.unregisterOutgoingPluginChannel((Plugin)this);
    }

    @EventHandler
    public void onRg(PlayerRegisterChannelEvent playerRegisterChannelEvent) {
        if (CH.equals(playerRegisterChannelEvent.getChannel())) {
            this.lt(playerRegisterChannelEvent.getPlayer());
        }
    }

    public void onPluginMessageReceived(String string, Player player, byte[] byArray) {
        if (CH.equals(string)) {
            this.lt(player);
        }
    }

    void lt(Player player) {
        if (!(this.alw && player.isOnline() && player.hasPermission(PRM))) {
            return;
        }
        player.sendPluginMessage((Plugin)this, CH, new byte[]{1});
    }
}

