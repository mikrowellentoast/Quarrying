package org.mikrowellentoast.quarrying.utils;

import org.bukkit.Material;

public class ItemUtils {

    public static boolean isPickaxe(Material material) {
        return material == Material.WOODEN_PICKAXE ||
               material == Material.STONE_PICKAXE ||
               material == Material.IRON_PICKAXE ||
               material == Material.GOLDEN_PICKAXE ||
               material == Material.DIAMOND_PICKAXE ||
               material == Material.NETHERITE_PICKAXE;
    }

    public static boolean isShovel(Material material) {
        return material == Material.WOODEN_SHOVEL ||
               material == Material.STONE_SHOVEL ||
               material == Material.IRON_SHOVEL ||
               material == Material.GOLDEN_SHOVEL ||
               material == Material.DIAMOND_SHOVEL ||
               material == Material.NETHERITE_SHOVEL;
    }
}
