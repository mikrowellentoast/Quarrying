package org.mikrowellentoast.quarrying.utils;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class QuarryingUtils {

    private static final NamespacedKey LEVEL_KEY =
            new NamespacedKey("quarrying", "active_level");

    private static final NamespacedKey STRICT_KEY =
            new NamespacedKey("quarrying", "break");

    public static int getActiveLevel(ItemStack item, int maxLevel) {
        if (item == null || !item.hasItemMeta()) return 1;

        ItemMeta meta = item.getItemMeta();
        Integer stored = meta.getPersistentDataContainer()
                .get(LEVEL_KEY, PersistentDataType.INTEGER);

        if (stored == null) return 1;


        return Math.min(stored, maxLevel);
    }


    public static void setActiveLevel(ItemStack item, int level) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer()
                .set(LEVEL_KEY, PersistentDataType.INTEGER, level);
        item.setItemMeta(meta);

        updateLore(item);
    }

    public static int cycleLevel(int current, int maxLevel) {
        int next = current + 1;
        if (next > maxLevel) next = 0;
        return next;
    }

    public static void toggleStrictBlockTypeMatching(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();

        int stored = meta.getPersistentDataContainer()
                .getOrDefault(STRICT_KEY, PersistentDataType.INTEGER, 0);

        int newValue = (stored == 0) ? 1 : 0;

        meta.getPersistentDataContainer()
                .set(STRICT_KEY, PersistentDataType.INTEGER, newValue);

        item.setItemMeta(meta);
        updateLore(item);
    }

    public static boolean isStrictBlockTypeMatching(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        Integer stored = meta.getPersistentDataContainer()
                .get(STRICT_KEY, PersistentDataType.INTEGER);

        return stored != null && stored == 1;
    }

    public static void updateLore(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();

        int level = QuarryingUtils.getActiveLevel(item, 3); // or maxLevel dynamically if you store it
        boolean strict = meta.getPersistentDataContainer()
                .getOrDefault(STRICT_KEY, PersistentDataType.INTEGER, 0) == 1;

        List<String> lore = meta.hasLore()
                ? new ArrayList<>(meta.getLore())
                : new ArrayList<>();


        lore.removeIf(line -> {
            String stripped = ChatColor.stripColor(line);
            return stripped.startsWith("Filter:") || stripped.startsWith("Radius:") || stripped.equals("--------------------");
        });

        lore.add(ChatColor.GRAY + "--------------------");


        if (level > 0) {
            lore.add(ChatColor.GRAY + "Radius: " + ChatColor.GREEN + "3x3x" + level);
        } else {
            lore.add(ChatColor.GRAY + "Radius: " + ChatColor.RED + "Disabled");
        }


        String strictStatus = strict
                ? ChatColor.GREEN + "Enabled"
                : ChatColor.RED + "Disabled";

        lore.add(ChatColor.GRAY + "Filter: " + strictStatus);

        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}