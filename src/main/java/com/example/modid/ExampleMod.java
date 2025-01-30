package com.example.modid;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashSet;
import java.util.Set;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class ExampleMod {

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Hello From {}!", Tags.MOD_NAME);
    }


    @Mod.EventBusSubscriber
    public static class RegistryBlocker {

        @SubscribeEvent
        public static void onRegisterItems(RegistryEvent.Register<Item> event) {
            for (ResourceLocation blocked : ModConfig.getBlockedItems()) {
                if (event.getRegistry().containsKey(blocked)) {
                    LOGGER.info("[ItemBlocker] Replacing item: {}", blocked);

                    Item dummy = new Item() {
                        @Override
                        public ItemStack getContainerItem(ItemStack itemStack) {
                            return ItemStack.EMPTY; // Prevent crafting results
                        }

                        @Override
                        public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
                            if (entity instanceof EntityPlayer) {
                                EntityPlayer player = (EntityPlayer) entity;
                                if (isSelected || player.getHeldItemOffhand() == stack) {
                                    stack.shrink(1); // Delete the item when held
                                }
                            }
                        }
                    }.setRegistryName(blocked).setTranslationKey("blocked_item");

                    // Replace with a dummy item that does nothing
                    event.getRegistry().register(dummy);
                }
            }
        }
    }
}
