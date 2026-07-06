package org.mikrowellentoast.quarrying.listeners;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.mikrowellentoast.quarrying.utils.BlockUtils;
import org.mikrowellentoast.quarrying.utils.ItemUtils;
import org.mikrowellentoast.quarrying.utils.QuarryingUtils;
import org.mikrowellentoast.quarrying.utils.TagUtils;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakListener implements Listener {

    private final Set<Block> processingBlocks = new HashSet<>();

    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {

        if (processingBlocks.contains(event.getBlock())) {
            return;
        }

        Player player = event.getPlayer();


        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE) {
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR || (!ItemUtils.isPickaxe(itemInHand.getType()) && !ItemUtils.isShovel(itemInHand.getType()))) {
            return;
        }

       boolean usingShovel = ItemUtils.isShovel(itemInHand.getType());

        NamespacedKey key = new NamespacedKey("quarrying", "quarrying");
        Enchantment enchant = Registry.ENCHANTMENT.get(key);

        if (enchant == null) {
            Bukkit.getConsoleSender().sendMessage("Error: Quarrying enchantment not found.");
            return;
        }

        if(!itemInHand.containsEnchantment(enchant)) {
            return;
        }

        Block block = event.getBlock();
        Material blockType = block.getType();

        if (usingShovel) {
            if (!TagUtils.isShovelBlock(blockType)) {
                return;
            }
        } else {
            if (!TagUtils.isMiningStone(blockType) && !TagUtils.isOre(blockType)) {
                return;
            }
        }

        int maxLevel = itemInHand.getEnchantmentLevel(enchant);
        int level = QuarryingUtils.getActiveLevel(itemInHand, maxLevel);

        if (level <= 0) {
            return;
        }


        BlockFace face = player.getTargetBlockFace(5);
        if (face != null) {
            face = face.getOppositeFace();
        }

        boolean strict = QuarryingUtils.isStrictBlockTypeMatching(itemInHand);

        int successfulbreaks = 0;

        for (Block target : BlockUtils.findBlocks(block, face ,level, strict)) {
            if (target.getType().isAir() || target.isLiquid() || target.getType().getHardness() < 0) {
                continue;
            }

            if (usingShovel) {
                if(!TagUtils.isShovelBlock(target.getType())) {
                    continue;
                }
            } else {
                if (!TagUtils.isMiningStone(target.getType()) && !TagUtils.isOre(target.getType())) {
                    continue;
                }
            }

            processingBlocks.add(target);

            try {

                BlockBreakEvent fakeEvent = new BlockBreakEvent(target, player);
                Bukkit.getPluginManager().callEvent(fakeEvent);

                if (!fakeEvent.isCancelled()) {
                    target.breakNaturally(itemInHand, true, true);
                    successfulbreaks++;
                }
            } finally {
                processingBlocks.remove(target);
            }
        }

        for (int i = 0; i < successfulbreaks; i++) {
            player.damageItemStack(itemInHand, 1);
        }
    }
}
