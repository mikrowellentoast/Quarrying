package org.mikrowellentoast.quarrying.listeners;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.mikrowellentoast.quarrying.utils.ItemUtils;
import org.mikrowellentoast.quarrying.utils.QuarryingUtils;

public class SwapHandListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (itemInMainHand == null || itemInMainHand.getType().isAir()) {
            return;
        }

        if (!ItemUtils.isPickaxe(itemInMainHand.getType())
                && !ItemUtils.isShovel(itemInMainHand.getType())) {
            return;
        }

        NamespacedKey key = new NamespacedKey("quarrying", "quarrying");
        Enchantment enchant = Registry.ENCHANTMENT.get(key);

        if (enchant == null || !itemInMainHand.containsEnchantment(enchant)) {
            return;
        }

        event.setCancelled(true);

        if (player.isSneaking()) {
            QuarryingUtils.toggleStrictBlockTypeMatching(itemInMainHand);

            boolean isStrict = QuarryingUtils.isStrictBlockTypeMatching(itemInMainHand);
            String message = ChatColor.GRAY + "Strict block type matching: " + (isStrict ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled");
            player.sendActionBar(net.kyori.adventure.text.Component.text(message));
            return;
        }



        int maxLevel = itemInMainHand.getEnchantmentLevel(enchant);
        int currentLevel = QuarryingUtils.getActiveLevel(itemInMainHand, maxLevel);
        int newLevel = QuarryingUtils.cycleLevel(currentLevel, maxLevel);

        QuarryingUtils.setActiveLevel(itemInMainHand, newLevel);

        player.sendActionBar(
                net.kyori.adventure.text.Component.text("3x3x" + newLevel).color(NamedTextColor.GREEN)
        );
    }
}