package org.mikrowellentoast.quarrying;

import io.papermc.paper.datapack.Datapack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mikrowellentoast.quarrying.listeners.BlockBreakListener;
import org.mikrowellentoast.quarrying.listeners.SwapHandListener;

public final class Quarrying extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new SwapHandListener(), this);
    }

    @Override
    public void onLoad() {
        Datapack pack = this.getServer().getDatapackManager().getPack(getPluginMeta().getName() + "/provided");
        if (pack != null) {
            if (pack.isEnabled()) {
                this.getLogger().info("Datapack loaded successfully.");
            } else {
                this.getLogger().warning("Datapack failed to load.");
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
