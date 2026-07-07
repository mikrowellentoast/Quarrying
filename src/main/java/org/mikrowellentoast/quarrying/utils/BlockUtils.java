package org.mikrowellentoast.quarrying.utils;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.Collection;

public class BlockUtils {

    public static Collection<Block> findBlocks(Block origin, BlockFace face, int level, boolean strict) {
        if (level < 1) level = 1;

        Collection<Block> blocks = new ArrayList<>(9 * level);

        if (face == null) {
            face = BlockFace.UP;
        }

        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {

                for (int l = 0; l < level; l++) {

                    int dx = 0, dy = 0, dz = 0;

                    switch (face) {

                        case UP -> {
                            dx = a;
                            dz = b;
                            dy = l;
                        }

                        case DOWN -> {
                            dx = a;
                            dz = b;
                            dy = -l;
                        }

                        case NORTH -> {
                            dx = a;
                            dy = b;
                            dz = -l;
                        }

                        case SOUTH -> {
                            dx = a;
                            dy = b;
                            dz = l;
                        }

                        case EAST -> {
                            dz = a;
                            dy = b;
                            dx = l;
                        }

                        case WEST -> {
                            dz = a;
                            dy = b;
                            dx = -l;
                        }

                        default -> {
                            continue;
                        }
                    }

                    if (a == 0 && b == 0 && l == 0) continue;

                    Block target = origin.getRelative(dx, dy, dz);
                    if (target.getType() != origin.getType() && strict) {
                        continue;
                    }
                    blocks.add(target);
                }
            }
        }

        return blocks;
    }
}