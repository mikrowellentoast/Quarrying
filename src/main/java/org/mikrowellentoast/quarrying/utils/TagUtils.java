package org.mikrowellentoast.quarrying.utils;

import org.bukkit.Material;
import org.bukkit.Tag;

import java.util.EnumSet;
import java.util.Set;

public class TagUtils {

    @SafeVarargs
    private static boolean anyTag(Material m, Tag<Material>... tags) {
        for (Tag<Material> tag : tags) {
            if (tag.isTagged(m)) return true;
        }
        return false;
    }

    private static boolean anyMaterial(Material m, Set<Material> set) {
        return set.contains(m);
    }


    private static final Set<Material> SHOVEL_BLOCKS = EnumSet.of(
            Material.GRAVEL,
            Material.CLAY,
            Material.SOUL_SOIL,
            Material.SOUL_SAND,
            Material.MUD,
            Material.MYCELIUM,
            Material.GRASS_BLOCK,
            Material.PODZOL,
            Material.COARSE_DIRT,
            Material.ROOTED_DIRT
    );


    public static boolean isMiningStone(Material m) {
        return anyTag(m,
                Tag.BASE_STONE_OVERWORLD,
                Tag.TERRACOTTA,
                Tag.BASE_STONE_NETHER,
                Tag.NYLIUM
        ) || m == Material.SANDSTONE
                || m == Material.RED_SANDSTONE
                || m == Material.END_STONE
                || m == Material.MAGMA_BLOCK
                || m == Material.COBBLESTONE
                || m == Material.COBBLED_DEEPSLATE
                || m == Material.CALCITE;
    }

    public static boolean isOre(Material m) {
        return anyTag(m,
                Tag.COAL_ORES,
                Tag.IRON_ORES,
                Tag.COPPER_ORES,
                Tag.GOLD_ORES,
                Tag.REDSTONE_ORES,
                Tag.LAPIS_ORES,
                Tag.DIAMOND_ORES,
                Tag.EMERALD_ORES
        ) || m == Material.NETHER_QUARTZ_ORE;
    }

    public static boolean isShovelBlock(Material m) {
        return Tag.DIRT.isTagged(m)
                || Tag.SAND.isTagged(m)
                || anyMaterial(m, SHOVEL_BLOCKS);
    }
}