package net.marsim.chaosmod.item;

import net.marsim.chaosmod.ChaosMod;
import net.marsim.chaosmod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChaosMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CHAOS_TAB = CREATIVE_MODE_TABS.register("chaos_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.UNSTABLE_PARTICLE.get()))
                    .title(Component.translatable("creativetab.chaos_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        // particles
                        output.accept(ModItems.UNSTABLE_PARTICLE.get());
                        output.accept(ModItems.STABLE_PARTICLE.get());

                        // bars
                        output.accept(ModItems.UNSTABLE_BAR.get());
                        output.accept(ModItems.STABLE_BAR.get());
                        output.accept(ModItems.DUALITY_BAR.get());

                        // fuel
                        output.accept(ModItems.SHADOW_INFUSED_COAL.get());

                        // blocks
                        output.accept(ModBlocks.UNSTABLE_BLOCK.get());
                        output.accept(ModBlocks.UNSTABLE_PARTICLE_ORE.get());
                        output.accept(ModBlocks.VOID_REFINER.get());
                        output.accept(ModBlocks.CHAOTIC_STATION.get());
                        output.accept(ModBlocks.STAR_GENERATOR.get());

                        output.accept(ModBlocks.STABLE_PARTICLE_ORE.get());

                        //dusts
                        output.accept(ModItems.VOID_DUST.get());

                        // ingots
                        output.accept(ModItems.VOID_BAR.get());

                        // cores
                        output.accept(ModItems.FUSION_CORE.get());

                        // tools
                        output.accept(ModItems.DUALITY_SWORD.get());
                        output.accept(ModItems.DUALITY_PICKAXE.get());
                        output.accept(ModItems.DUALITY_AXE.get());

                        // armor
                        output.accept(ModItems.DUALITY_HELMET.get());
                        output.accept(ModItems.DUALITY_CHESTPLATE.get());
                        output.accept(ModItems.DUALITY_LEGGINGS.get());
                        output.accept(ModItems.DUALITY_BOOTS.get());
                    })
                    .build());
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
