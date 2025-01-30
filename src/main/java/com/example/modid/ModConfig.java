package com.example.modid;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Config(modid = Tags.MOD_ID)
public class ModConfig {

    @Config.Comment("Items to block")
    @Config.RequiresMcRestart
    public static String[] blockedItems = new String[]{};

    /**
     * Converts the blockedItems config into a Set of ResourceLocations.
     */
    public static Set<ResourceLocation> getBlockedItems() {
        return Arrays.stream(blockedItems)
                .map(ResourceLocation::new)
                .collect(Collectors.toCollection(HashSet::new));
    }
}
